package com.touchlane.waldotest.domain.repository.impl

import com.touchlane.waldotest.api.WaldoAPI
import com.touchlane.waldotest.domain.repository.AuthenticationService
import com.touchlane.waldotest.domain.session.SessionManager

class AuthenticationServiceImpl(
    private val waldoAPI: WaldoAPI,
    private val sessionManager: SessionManager
) : AuthenticationService {

    override fun authenticate(login: String, password: String): Boolean {
        val result = waldoAPI.login(login, password)
        if (result.isSuccess) {
            sessionManager.updateSessionToken(result.requireData())
        }
        return result.isSuccess
    }
}