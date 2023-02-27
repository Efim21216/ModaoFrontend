package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val id: Long? = null,
    val typeGroup: Int? = null,
    val description: String? = null,
    val groupName: String? = null,
    val name: String? = null
) : Parcelable