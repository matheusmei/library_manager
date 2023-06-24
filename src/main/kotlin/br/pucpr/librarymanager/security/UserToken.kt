package br.pucpr.librarymanager.security

data class UserToken(
    val id: Long,
    val name: String,
    val roles: Set<String>
) {
    constructor() : this(0, "", setOf())
}
