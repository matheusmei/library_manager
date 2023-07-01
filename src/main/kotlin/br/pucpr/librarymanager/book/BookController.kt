package br.pucpr.librarymanager.book

import br.pucpr.librarymanager.book.book_requests.BookRequest
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController (
    val service: BookService,
    @Autowired private val bookRepository : BookRepository
){
    @GetMapping()
    fun listBooks() = service.getAllBooks()

    fun addNewBook(@Valid @RequestBody req: BookRequest) =
        service.userAddBook(req).toResponse().let { ResponseEntit}

    @PostMapping()
    fun createNewBook(@Validated @RequestBody book: Book) : Book
            = bookRepository.save(book)

    @GetMapping("/{id}")
    fun getBookById(@PathVariable(value = "id")bookId:Long)
            : ResponseEntity<Book> {
        return bookRepository.findById(bookId).map {
                book -> ResponseEntity.ok(book)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PutMapping("/{id}")
    fun updateBookById(
        @PathVariable(value = "id")bookId: Long,
        @Validated @RequestBody newBook: Book
    ): ResponseEntity<Book> {
        return bookRepository.findById(bookId).map {
                existingBook ->
            val updatedBook = existingBook.copy(
                title = newBook.title,authors = newBook.authors)
            ResponseEntity.ok().body(bookRepository.save(updatedBook))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteBookById(
        @PathVariable(value = "id")bookId: Long
    ): ResponseEntity<Void> {
        return bookRepository.findById(bookId).map {
                book ->
            bookRepository.delete(book)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
    @PostMapping("/filter")
    fun getBooksByTitle(
        @RequestParam("title",required = true)title:String)
            :List<Book> = bookRepository.findBookByTitle(title)

}