package br.pucpr.librarymanager.book
import jakarta.persistence.*


@Entity
@Table(name = "TbBook")
data class Book(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var title : String = "",

    @Column(nullable = false)
    var authors:String = "",


)
