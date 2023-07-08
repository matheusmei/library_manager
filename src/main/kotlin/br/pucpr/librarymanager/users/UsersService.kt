package br.pucpr.librarymanager.users

import br.pucpr.librarymanager.book.Book
import br.pucpr.librarymanager.book.BookController
import br.pucpr.librarymanager.book.BookRepository
import br.pucpr.librarymanager.book.book_requests.BookRequest
import br.pucpr.librarymanager.exception.BadRequestException
import br.pucpr.librarymanager.security.Jwt
import br.pucpr.librarymanager.users.requests.AddBookToUserRequest
import br.pucpr.librarymanager.users.requests.LoginRequest
import br.pucpr.librarymanager.users.requests.UserRequest
import br.pucpr.librarymanager.users.responses.LoginResponse
import br.pucpr.librarymanager.users.responses.UserResponse
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsersService(
    val repository: UsersRepository,
    val rolesRepository: RolesRepository,
    val booksRepository: BookRepository,
    val jwt: Jwt
) {
    fun save(req: UserRequest): User {
        val user = User(
            email = req.email!!,
            password = req.password!!,
            name = req.name!!,
        )
        val userRole = rolesRepository.findByName("USER")
            ?: throw IllegalStateException("Role 'USER' not found!")

        user.roles.add(userRole)
        return repository.save(user)
    }

    fun getById(id: Long) = repository.findByIdOrNull(id)

    fun findAll(role: String?): List<User> =
        if (role == null) repository.findAll(Sort.by("name"))
        else repository.findAllByRole(role)

    fun login(credentials: LoginRequest): LoginResponse? {
        val user = repository.findByEmail(credentials.email!!) ?: return null
        if (user.password != credentials.password) return null
        log.info("User logged in. id={} name={}", user.id, user.name)
        return LoginResponse(
            token = jwt.createToken(user),
            user.toResponse()
        )
    }

    fun addBookToUser(addBookToUserRequest: AddBookToUserRequest): UserResponse? {
        val user = repository.findByIdOrNull(addBookToUserRequest.userId) ?: return null
        val book = booksRepository.findByIdOrNull(addBookToUserRequest.bookId) ?: return null
        user.books.add(book)
        repository.save(user)
        return user.toResponse()
    }

    fun delete(id: Long): Boolean {
        val user = repository.findByIdOrNull(id) ?: return false
        if (user.roles.any { it.name == "ADMIN" }) {
            val count = repository.findAllByRole("ADMIN").size
            if (count == 1)  throw BadRequestException("Cannot delete the last system admin!")
        }
        log.warn("User deleted. id={} name={}", user.id, user.name)
        repository.delete(user)
        return true
    }

    fun getBooksForUser(id: Long): MutableSet<Book> {
        val user = repository.findByIdOrNull(id) ?: return mutableSetOf()
        return user.books
    }

    companion object {
        val log = LoggerFactory.getLogger(UsersService::class.java)
    }
}