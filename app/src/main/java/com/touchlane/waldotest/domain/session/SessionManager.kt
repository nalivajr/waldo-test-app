package com.touchlane.waldotest.domain.session

interface SessionManager {

    fun updateSessionToken(token: String)

    fun getSessionToken(): String?
}