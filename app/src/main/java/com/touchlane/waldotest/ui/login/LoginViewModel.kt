package com.touchlane.waldotest.ui.login

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touchlane.waldotest.domain.errorhandling.ErrorDispatcher
import com.touchlane.waldotest.domain.exception.EmptyPasswordException
import com.touchlane.waldotest.domain.exception.EmptyUsernameException
import com.touchlane.waldotest.domain.exception.InvalidUsernameException
import com.touchlane.waldotest.domain.repository.AuthenticationService
import com.touchlane.waldotest.domain.validation.Validator
import com.touchlane.waldotest.ui.model.UiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticationService: AuthenticationService,
    private val errorDispatcher: ErrorDispatcher,
    private val validator: Validator
) : ViewModel() {

    val progressData: MutableLiveData<Boolean> = MutableLiveData()
    val loginResultData: MutableLiveData<UiResult<Boolean>> = MutableLiveData()
    val loginInputError: MutableLiveData<String> = MutableLiveData()
    val passwordInputError: MutableLiveData<String> = MutableLiveData()

    fun login(username: String, password: String) {
        if (!validate(username, password)) {
            return
        }
        progressData.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                authenticationService.authenticate(username, password)
            }.onSuccess {
                loginResultData.postValue(UiResult.success(true))
                progressData.postValue(false)
            }.onFailure {
                val message = errorDispatcher.dispatchError(it)
                loginResultData.postValue(UiResult.error(message, it))
                progressData.postValue(false)
            }
        }
    }

    private fun validate(username: String, password: String): Boolean {
        return validateUserName(username) and validatePassword(password)
    }

    private fun validateUserName(username: String): Boolean {
        return validator.validate(
            rule = { ensureUsernameValid(username) },
            onValid = { resetLoginError() },
            onInvalid = { msg, _ -> loginInputError.postValue(msg) }
        )
    }


    private fun validatePassword(password: String): Boolean {
        return validator.validate(
            rule = {
                if (password.isBlank()) {
                    throw EmptyPasswordException()
                } else {
                    true
                }
            },
            onValid = { resetPasswordError() },
            onInvalid = { msg, _ -> passwordInputError.postValue(msg) }
        )
    }

    private fun ensureUsernameValid(username: String): Boolean {
        when {
            username.isBlank() -> throw EmptyUsernameException()
            !username.matches(Patterns.EMAIL_ADDRESS.toRegex()) -> throw InvalidUsernameException()
        }
        return true
    }

    fun resetLoginError() {
        loginInputError.postValue(null)
    }

    fun resetPasswordError() {
        passwordInputError.postValue(null)
    }
}