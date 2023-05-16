package ru.nsu.fit.modao.models

data class Authorization(
    val id: Long,
    val accessToken: String,
    val refreshToken: String
)