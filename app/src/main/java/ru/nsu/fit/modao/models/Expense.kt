package ru.nsu.fit.modao.models

data class Expense (
    val info: String,
    val shortInfo: String,
    val currency: Currency,
    val expense: Double
)