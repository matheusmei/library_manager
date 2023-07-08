package br.pucpr.librarymanager.users.requests

import jakarta.validation.constraints.NotBlank
data class AddBookToUserRequest (
    @NotBlank
    var userId: Long?,

    @NotBlank
    var bookId: Long?
)