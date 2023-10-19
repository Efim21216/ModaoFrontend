package ru.nsu.fit.modao.models

data class User(
    val login: String? = null,
    val password: String? = null,
    val username: String? = null,
    val phone_number: String? = null,
    val bank: String? = null,
    val idPicture: Int? = null,
    val id: Long? = null,

    val uuid: String? = null,
    val deviceToken: String? = null,
    val packageName: String = "ru.nsu.fit.modao",
    val appVersion: String = "1"
)