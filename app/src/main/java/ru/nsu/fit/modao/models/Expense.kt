package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expense (
    val currency: Currency? = null,
    val id: Long? = null,
    val type: Int = 0,
    val description: String = "Description",
    val name: String? = null,
    val groupId: Long? = null,
    val price: Float? = null,
    val time: String? = null,
    val status: Int? = null,
    val userPayingId: Long? = null,
    val usernameCreator: String? = null,
    val usernamePaying: String? = null,
    val customPairIdCoefficientList: Array<ParticipantEvent>? = null,
    val customPairIdCoefficientPaying: ParticipantEvent? = null,
    val expenseDtoList: Array<ParticipantEvent>? = null
): ExpenseListItem(), Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Expense

        if (currency != other.currency) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (groupId != other.groupId) return false
        if (price != other.price) return false
        if (userPayingId != other.userPayingId) return false
        if (usernamePaying != other.usernamePaying) return false
        if (customPairIdCoefficientList != null) {
            if (other.customPairIdCoefficientList == null) return false
            if (!customPairIdCoefficientList.contentEquals(other.customPairIdCoefficientList)) return false
        } else if (other.customPairIdCoefficientList != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = currency?.hashCode() ?: 0
        result = 31 * result + (id?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (groupId?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        result = 31 * result + (userPayingId?.hashCode() ?: 0)
        result = 31 * result + (usernamePaying?.hashCode() ?: 0)
        result = 31 * result + (customPairIdCoefficientList?.contentHashCode() ?: 0)
        return result
    }
}