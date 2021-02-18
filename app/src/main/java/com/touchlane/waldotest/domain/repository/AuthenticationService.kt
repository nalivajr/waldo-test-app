package com.touchlane.waldotest.domain.repository

interface AuthenticationService {

    fun authenticate(login: String, password: String): Boolean
}