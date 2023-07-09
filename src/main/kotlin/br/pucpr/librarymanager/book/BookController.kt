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

    @PostMapping
    fun addNewBook(@Valid @RequestBody req: BookRequest) =
        service.userAddBook(req)
            .toResponse()
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable("id") id: Long) =
        service.getBookById(id)
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()

    @PutMapping("/{id}")
    fun updateBookById( @PathVariable("id") id: Long, @Validated @RequestBody newBook: Book ): ResponseEntity<Void> {
        val updatedBook = service.updateBookById(id, newBook)
        return if (updatedBook != null) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteBookById(@PathVariable("id") id: Long): ResponseEntity<Void> =
        if (service.deleteBookById(id)) ResponseEntity.ok().build()
        else ResponseEntity.notFound().build()

    @PostMapping("/filter")
    fun getBooksByTitle(
        @RequestParam("title",required = true)title:String)
            :List<Book> = bookRepository.findBookByTitle(title)

}