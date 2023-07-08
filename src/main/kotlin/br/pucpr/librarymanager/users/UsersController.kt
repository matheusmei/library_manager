package br.pucpr.librarymanager.users

import br.pucpr.librarymanager.book.BookService
import br.pucpr.librarymanager.users.requests.AddBookToUserRequest
import br.pucpr.librarymanager.users.requests.LoginRequest
import br.pucpr.librarymanager.users.requests.UserRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(
    val service: UsersService,
    val bookService: BookService) {
    @Operation(
        summary = "Lista todos os usuários",
        parameters = [
            Parameter(
                name = "role",
                description = "Papel a ser usado no filtro (opcional)",
                example = "USER"
            )]
    )
    @GetMapping
    fun listUsers(@RequestParam("role") role: String?) =
        service.findAll(role)
            .map { it.toResponse() }

    @Transactional
    @PostMapping
    fun createUser(@Valid @RequestBody req: UserRequest) =
        service.save(req)
            .toResponse()
            .let { ResponseEntity.status(CREATED).body(it) }

    @GetMapping("/me")
    @PreAuthorize("permitAll()")
    @SecurityRequirement(name = "AuthServer")
    fun getSelf(auth: Authentication) = getUser(auth.credentials as Long)

    @GetMapping("/{id}")
    fun getUser(@PathVariable("id") id: Long) =
        service.getById(id)
            ?.let { ResponseEntity.ok(it.toResponse()) }
            ?: ResponseEntity.notFound().build()

    @PostMapping("/login")
    fun login(@Valid @RequestBody credentials: LoginRequest) =
        service.login(credentials)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "AuthServer")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Void> =
        if (service.delete(id)) ResponseEntity.ok().build()
        else ResponseEntity.notFound().build()

    @PutMapping("/books")
    @PreAuthorize("permitAll()")
    @SecurityRequirement(name = "AuthServer")
    fun addBookForUser(@Valid @RequestBody addBookRequest: AddBookToUserRequest) =
        service.addBookToUser(addBookRequest)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    @GetMapping("/{id}/books")
    fun getBooksFor(@PathVariable("id") id: Long) =
        service.getBooksForUser(id)
            ?.let { ResponseEntity.ok(it.forEach { it.toResponse() }) }
}