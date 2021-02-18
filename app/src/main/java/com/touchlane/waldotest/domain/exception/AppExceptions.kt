package com.touchlane.waldotest.domain.exception

import java.lang.RuntimeException

class ApiException(val messages: List<String>) : RuntimeException()

class ValidationException : RuntimeException()

class EmptyUsernameException : RuntimeException()
class InvalidUsernameException : RuntimeException()

class EmptyPasswordException : RuntimeException()

