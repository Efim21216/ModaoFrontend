package ru.nsu.fit.modao.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreationExpense(
    val group: Group,
    val cost: Float,
    val description: String
    ) : Parcelable
