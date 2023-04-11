package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreationExpenseViaBottom(
    val second: ParticipantEvent? = null,
    val isEvent: Boolean,
    val cost: String? = null,
    val description: String? = null
): Parcelable
