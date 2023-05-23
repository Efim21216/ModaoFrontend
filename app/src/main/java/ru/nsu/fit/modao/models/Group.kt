package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Group(
    var id: Long? = null,
    val typeGroup: Int? = null,
    val description: String? = null,
    val groupName: String? = null,
    val uuid: String? = null,
    val userIdList: Array<Long>? = null,
    val time: String? = null
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Group

        if (id != other.id) return false
        if (typeGroup != other.typeGroup) return false
        if (description != other.description) return false
        if (groupName != other.groupName) return false
        if (uuid != other.uuid) return false
        if (userIdList != null) {
            if (other.userIdList == null) return false
            if (!userIdList.contentEquals(other.userIdList)) return false
        } else if (other.userIdList != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (typeGroup ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (groupName?.hashCode() ?: 0)
        result = 31 * result + (uuid?.hashCode() ?: 0)
        result = 31 * result + (userIdList?.contentHashCode() ?: 0)
        return result
    }
}