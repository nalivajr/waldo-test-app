package com.touchlane.waldotest.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touchlane.waldotest.domain.model.SessionState
import com.touchlane.waldotest.domain.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max

class SplashViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    val sessionStateData: MutableLiveData<SessionState> = MutableLiveData()

    fun checkSession() {
        viewModelScope.launch(Dispatchers.IO) {
            val splashStated = System.currentTimeMillis()
            val state = sessionManager.getSessionToken()
                ?.let { SessionState.ACTIVE }
                ?: SessionState.NO_SESSION
            val wait = SPLASH_MILLIS - (System.currentTimeMillis() - splashStated)
            delay(max(0, wait))
            sessionStateData.postValue(state)
        }
    }

    companion object {
        private const val SPLASH_MILLIS = 1000L
    }
}