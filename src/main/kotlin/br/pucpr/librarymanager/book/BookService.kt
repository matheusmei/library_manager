package br.pucpr.librarymanager.book

import br.pucpr.librarymanager.book.book_requests.BookRequest
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

}