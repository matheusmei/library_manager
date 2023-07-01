package br.pucpr.librarymanager.book

import br.pucpr.librarymanager.book.book_response.BookResponse
import jakarta.persistence.*
import br.pucpr.librarymanager.users.User


@Entity
class Book(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var title : String = "",

    @Column(nullable = false)
    var authors:String = "",

    @ManyToOne
    val users: User? = null

) {
    fun toResponse() = id?.let { BookResponse(it, title, authors) }
}
