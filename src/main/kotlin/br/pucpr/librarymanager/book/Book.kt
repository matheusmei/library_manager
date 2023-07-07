package br.pucpr.librarymanager.book

import br.pucpr.librarymanager.book.book_response.BookResponse
import jakarta.persistence.*
import br.pucpr.librarymanager.users.User
import com.fasterxml.jackson.annotation.JsonBackReference


@Entity
data class Book(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var title : String = "",

    @Column(nullable = false)
    var authors:String = "",

    @JsonBackReference
    @ManyToOne
    var user: User? = null

) {
    fun toResponse() = id?.let { BookResponse(it, title, authors) }
}
