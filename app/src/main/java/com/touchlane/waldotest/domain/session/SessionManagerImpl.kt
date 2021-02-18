package com.touchlane.waldotest.domain.session

class SessionManagerImpl : SessionManager {

    private var token: String? = null

    override fun updateSessionToken(token: String) {
        this.token = token
    }

    override fun getSessionToken(): String? = token
}