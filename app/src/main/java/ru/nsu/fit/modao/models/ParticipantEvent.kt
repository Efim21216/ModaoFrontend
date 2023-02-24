package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParticipantEvent(
    val username: String? = null,
    val id: Long? = null,
    var selected: Boolean = false,
    var coefficient: Float? = null,
    var assumedCoefficient: String? = null
) : Parcelable
