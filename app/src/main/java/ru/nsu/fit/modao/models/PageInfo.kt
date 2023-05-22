package ru.nsu.fit.modao.models

data class PageInfo(
    val sort: SortInfo? = null,
    val offset: Int? = null,
    val pageNumber: Int? = null,
    val pageSize: Int? = null,
    val paged: Boolean? = null,
    val unpaged: Boolean? = null
)