package br.pucpr.librarymanager.users

import br.pucpr.librarymanager.users.responses.UserResponse
import jakarta.persistence.*
import br.pucpr.librarymanager.book.Book
import br.pucpr.librarymanager.book.BookService
import br.pucpr.librarymanager.book.book_response.BookResponse
import jakarta.validation.constraints.Email


@Entity
@Table(name = "TblUser")
class User(
    @Id @GeneratedValue
    var id: Long? = null,

    @Email
    @Column(unique = true, nullable = false)
    var email: String,

    @Column(length = 50)
    var password: String,

    @Column(nullable = false)
    var name: String = "",

    @OneToMany // 1 user -> varios livros
    @JoinTable(
        name = "OnTbBook",
        joinColumns = [JoinColumn(name = "idUser")],
        inverseJoinColumns = [JoinColumn(name = "idBook")]
    )
    val books: MutableSet<Book> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "UserRole",
        joinColumns = [JoinColumn(name = "idUser")],
        inverseJoinColumns = [JoinColumn(name = "idRole")]
    )
    val roles: MutableSet<Role> = mutableSetOf()
) {
    fun toResponse() = UserResponse(id!!, name, email, books)
}