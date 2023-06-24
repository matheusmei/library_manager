package br.pucpr.librarymanager.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface BookRepository : JpaRepository<Book, Long> {
    @Query("SELECT b from Book b where b.title = :title")
    fun findBookByTitle(@Param("title")title:String):MutableList<Book>
}