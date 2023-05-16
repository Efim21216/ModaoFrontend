package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParticipantEvent(
    val username: String? = null,
    val id: Long? = null,
    val userId: Long? = null,
    var selected: Boolean = false,
    var isSponsor: Boolean = false,
    var coefficient: Float? = null,
    var transferAmount: String? = null,
    var assumedCoefficient: String? = null
) : Parcelable
