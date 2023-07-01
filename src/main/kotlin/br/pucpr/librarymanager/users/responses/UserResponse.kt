package br.pucpr.librarymanager.users.responses

import br.pucpr.librarymanager.book.Book

data class UserResponse(
    val id: Long,
    val name: String,
    val email: String,
    val books: MutableSet<Book>
)