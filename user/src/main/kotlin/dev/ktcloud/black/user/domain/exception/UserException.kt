package dev.ktcloud.black.user.domain.exception

sealed class UserException(message: String) : RuntimeException(message) {
    class UserNotFoundException : UserException("User not found")
    class DuplicateEmailException : UserException("Email already in use")
}
