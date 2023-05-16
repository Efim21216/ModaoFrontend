package ru.nsu.fit.modao.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    val participants = MutableLiveData<Array<ParticipantEvent>>()
    var users: Array<ParticipantEvent> = arrayOf()
    val message = MutableLiveData<String>()
    val eventId = MutableLiveData<Long>()
    var sponsor: ParticipantEvent? = null
    var payFor = false
    private val handler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    fun createExpense(description: String, cost: String, id: Long, type: Int) {
        participants.value?.forEach { Log.d("MyTag", "${it.username!!} select ${it.selected} sponsor ${it.isSponsor}") }
        if (participants.value == null) {
            message.value = "Server problems..."
            return
        }
        if (description == "") {
            message.value = "Enter the description"
            return
        }
        participants.value?.forEach { participantEvent -> participantEvent.coefficient = 1f }
        try {
            participants.value?.forEach { participantEvent ->
                if (participantEvent.assumedCoefficient != null) {
                    participantEvent.coefficient = participantEvent.assumedCoefficient?.toFloat()
                } else {
                    participantEvent.coefficient = 1f
                }
            }
        } catch (e: NumberFormatException) {
            message.value = "Enter coefficient"
            return
        }
        val sum: Float
        try {
            sum = cost.toFloat()
        } catch (e: NumberFormatException) {
            message.value = "Enter the cost"
            return
        }
        val sponsorList = participants.value?.filter { it.isSponsor }
        if (sponsorList?.size != 1) {
            message.value = "Select the sponsor"
            return
        }
        val sponsor = sponsorList[0]
        val participantsEvent: Array<ParticipantEvent>? = participants.value
            ?.filter { it.selected && !it.isSponsor }?.toTypedArray()
        if (participantsEvent!!.isEmpty()) {
            message.value = "Select participants"
            return
        }

        if (type == 1 || payFor) {
            sponsor.coefficient = 0f
        }
        viewModelScope.launch(handler) {
            val expense = Expense(
                name = description, price = sum,
                customPairIdCoefficientList = participantsEvent,
                groupId = id, type = type,
                customPairIdCoefficientPaying = sponsor
            )
            val response = repository.createExpense(expense)
            if (response.isSuccessful){
                eventId.value = response.body()
            } else {
                Log.e("MyError", response.message())
            }
        }
    }
}