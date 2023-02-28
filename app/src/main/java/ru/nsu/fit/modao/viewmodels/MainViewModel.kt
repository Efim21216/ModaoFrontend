package ru.nsu.fit.modao.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.nsu.fit.modao.models.Expense
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    val user = MutableLiveData<User>()
    val groupId = MutableLiveData<Long>()
    val expenses = MutableLiveData<Array<Expense>>()
    val usersInGroup = MutableLiveData<Array<User>>()
    val tipMessageData = MutableLiveData<String>()
    val tipMessageStart = MutableLiveData<String>()
    val messageHandler = MutableLiveData<String>()
    private val handler = CoroutineExceptionHandler { _, throwable -> messageHandler.value = throwable.message}
    fun getUser() {
        viewModelScope.launch(handler) {
            val response = repository.getUser()
            if (response.isSuccessful) {
                user.value = response.body()
            } else {
                tipMessageStart.value = "error"
                Log.e("MyError", response.message())
            }
        }
    }
    fun createGroup(group: Group){
        viewModelScope.launch(handler) {
            val response = repository.createGroup(group)
            if (response.isSuccessful){
                groupId.value = response.body()
            }
            else {
                Log.e("MyError", response.message())
            }
        }
    }
    fun getGroupExpenses(id: Long){
        viewModelScope.launch(handler) {
            val response = repository.getGroupExpenses(id)
            if (response.isSuccessful){
                expenses.value = response.body()
            }
            else {
                Log.e("MyError", response.message())
            }
        }
    }
    fun getUsersInGroup(groupId: Long){
        viewModelScope.launch(handler) {
            val response = repository.getUsersInGroup(groupId)
            if (response.isSuccessful){
                usersInGroup.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }
    fun confirmEvent(eventId: Long){
        viewModelScope.launch(handler) {
            val response = repository.confirmEvent(eventId)
            if (response.isSuccessful){
                tipMessageData.value = "OK"
            } else {
                tipMessageData.value = "Fail"
            }
        }
    }
    fun addUserToGroup(orgId: Long, groupId: Long, userId: Long){
        viewModelScope.launch(handler) {
            val response = repository.addUserToGroup(orgId, groupId, userId)
            if (response.isSuccessful){
                getUsersInGroup(groupId)
            }
        }
    }
}