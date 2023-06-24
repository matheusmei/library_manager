package br.pucpr.librarymanager.users.responses

data class LoginResponse(
    val token: String,
    val user: UserResponse
)
