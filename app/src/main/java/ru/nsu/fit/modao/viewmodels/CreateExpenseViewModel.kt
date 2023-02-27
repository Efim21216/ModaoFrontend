package ru.nsu.fit.modao.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.ParticipantEvent
import ru.nsu.fit.modao.repository.Repository

class CreateExpenseViewModel(private val repository: Repository) : ViewModel() {
    val participants = MutableLiveData<Array<ParticipantEvent>>()
    val message = MutableLiveData<String>()
    val eventId = MutableLiveData<Long>()
    var sponsor: ParticipantEvent? = null
    private val handler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace() }
    fun createExpense(description: String, cost: String, id: Long) {
        var sum: Float
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
        viewModelScope.launch(handler) {
            val expense = Expense(
                name = description, price = sum,
                customPairIdCoefficientList = participantsEvent,
                groupId = id,
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