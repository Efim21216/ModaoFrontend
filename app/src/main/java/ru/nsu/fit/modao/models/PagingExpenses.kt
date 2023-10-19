package ru.nsu.fit.modao.models

data class PagingExpenses(
    val content: Array<Expense>? = null,
    val pageable: PageInfo? = null,
    val totalPages: Int? = null,
    val totalElements: Int? = null,
    val last: Boolean? = null,
    val number: Int? = null,
    val sort: SortInfo? = null,
    val size: Int? = null,
    val numberOfElements: Int? = null,
    val first: Boolean? = null,
    val empty: Boolean? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PagingExpenses

        if (content != null) {
            if (other.content == null) return false
            if (!content.contentEquals(other.content)) return false
        } else if (other.content != null) return false

        return true
    }

    override fun hashCode(): Int {
        return content?.contentHashCode() ?: 0
    }
}