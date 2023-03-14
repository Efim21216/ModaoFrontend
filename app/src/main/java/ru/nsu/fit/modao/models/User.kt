package ru.nsu.fit.modao.models

data class User(
    val login: String? = null,
    val password: String? = null,
    val username: String? = null,
    val phone_number: String? = null,
    val bank: String? = null,
    val idPicture: Int? = null,
    val id: Long? = null,
    val coefficient: Float? = null,
    val groupCustomPairIdNameList: ArrayList<Group>? = null
)