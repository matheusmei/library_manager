package br.pucpr.librarymanager.book.book_requests


import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
data class BookRequest(
    @field:NotBlank
    @field:Size(max = 50)
    val title: String?,

    @field:NotBlank
    @field:Size(max = 50)
    val authors: String?,
)
