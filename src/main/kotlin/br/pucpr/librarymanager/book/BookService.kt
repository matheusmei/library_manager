package br.pucpr.librarymanager.book

import br.pucpr.librarymanager.book.book_requests.BookRequest
import br.pucpr.librarymanager.users.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class BookService(
    val repository: BookRepository
    )
{
    fun getAllBooks():List<Book> = repository.findAll()

    fun userAddBook(req: BookRequest): Book {
        val userBook = Book(
            title = req.title!!,
            authors = req.authors!!,
        )
        return repository.save(userBook)
    }

    fun getBookById(id: Long): Book? = repository.findByIdOrNull(id)

    fun deleteBookById(id: Long): Boolean {
        val book = repository.findByIdOrNull(id) ?: return false
        repository.delete(book)
        return true
    }

    fun updateBookById(id: Long, newBook: Book): Book? {
        val book = repository.findByIdOrNull(id)
        return if (book != null) {
            val updateBook = book.copy(
                title = newBook.title,
                authors = newBook.authors)
                 repository.save(updateBook)
        } else {
            null
        }
    }

    fun setUserForBook(id: Long, user: User): Boolean {
        val book = repository.findByIdOrNull(id)?: return false
        book.user = user
        repository.save(book)
        return true
    }
}