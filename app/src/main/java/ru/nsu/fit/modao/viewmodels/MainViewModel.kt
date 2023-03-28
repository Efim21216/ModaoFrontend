package ru.nsu.fit.modao.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.nsu.fit.modao.models.*
import ru.nsu.fit.modao.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    val tipMessage = MutableLiveData<String>()
    val messageHandler = MutableLiveData<String>()

    val user = MutableLiveData<User>()
    val groupId = MutableLiveData<Long>()
    val expenses = MutableLiveData<Array<Expense>>()
    val usersInGroup = MutableLiveData<Array<User>>()
    val userExpenses = MutableLiveData<Array<UserDebt>>()
    val organizers = MutableLiveData<Array<User>>()
    val infoEvent = MutableLiveData<Expense>()
    val unconfirmedExpenses = MutableLiveData<Array<Expense>>()
    val userGroups = MutableLiveData<Array<Group>>()
    val groupInfo = MutableLiveData<Group>()
    val invitationUser = MutableLiveData<Array<Notification>>()
    val listFriends = MutableLiveData<Array<User>>()
    val countAdded = MutableLiveData<Int>(0)

    private val handler = CoroutineExceptionHandler { _, throwable -> messageHandler.value = throwable.message}
    fun getUser() {
        viewModelScope.launch(handler) {
            val response = repository.getUser()
            if (response.isSuccessful) {
                user.value = response.body()
            } else {
                tipMessage.value = "error"
                Log.e("MyError", response.message())
            }
        }
    }
    fun getUserGroups(){
        viewModelScope.launch(handler) {
            val response = repository.getUserGroups()
            if (response.isSuccessful){
                userGroups.value = response.body()
            } else {
                Log.e("MyTag", response.message())
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
    fun getGroupExpenses(id: Long, mode: Int, filter: Int){
        viewModelScope.launch(handler) {
            val response = repository.getGroupExpenses(id, mode, filter)
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
    fun confirmEvent(groupId: Long, eventId: Long){
        viewModelScope.launch(handler) {
            val response = repository.confirmEvent(groupId, eventId)
            if (!response.isSuccessful) {
                Log.d("MyTag", response.message())
            } else {
                getGroupUnconfirmedExpenses(groupId)
            }
        }
    }
    fun notConfirmEvent(eventId: Long, groupId: Long){
        viewModelScope.launch(handler) {
            val response = repository.notConfirmEvent(eventId)
            if (!response.isSuccessful) {
                Log.d("MyTag", response.message())
            } else {
                getGroupUnconfirmedExpenses(groupId)
            }
        }
    }
    fun addUserToGroup(groupId: Long, userId: Long){
        viewModelScope.launch(handler) {
            val response = repository.addUserToGroup(groupId, userId)
            if (response.isSuccessful){
                countAdded.value?.inc()
            } else {
                Log.e("My errors", "Error add friend")
            }
        }
    }
    fun getUserDebtInGroup(userId: Long, groupId: Long){
        viewModelScope.launch(handler) {
            val response = repository.getUserDebtInGroup(userId, groupId)
            if (response.isSuccessful){
                userExpenses.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }
    fun getListOrganizers(groupId: Long){
        viewModelScope.launch(handler) {
            val response = repository.getListOrganizers(groupId)
            if (response.isSuccessful){
                organizers.value = response.body()
            } else {
                organizers.value = arrayOf()
            }
        }
    }

    fun getEventInfo(eventId: Long){
        viewModelScope.launch(handler) {
            val response = repository.getEventInfo(eventId)
            if (response.isSuccessful){
                infoEvent.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }
    fun getGroupUnconfirmedExpenses(groupId: Long){
        viewModelScope.launch(handler) {
            val response = repository.getGroupUnconfirmedExpenses(groupId)
            if (response.isSuccessful){
                unconfirmedExpenses.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }
    fun getGroupInfo(groupId: Long){
        viewModelScope.launch(handler) {
            val response = repository.getGroupInfo(groupId)
            if (response.isSuccessful){
                groupInfo.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }
    fun addFriend(userUuid: String){
        viewModelScope.launch(handler) {
            val response = repository.addFriend(userUuid)
            if (response.isSuccessful){
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Incorrect data"
            }
        }
    }
    fun getInvitationsFriend() {
        viewModelScope.launch(handler) {
            val response = repository.getInvitationsFriend()
            if (response.isSuccessful){
                invitationUser.value = response.body()
            } else {
                Log.d("MyTag", "Fail")
            }
        }
    }
    fun getInvitationsGroup() {
        viewModelScope.launch(handler) {
            val response = repository.getInvitationsGroup()
            if (response.isSuccessful){
                invitationUser.value = response.body()
            } else {
                Log.d("MyTag", "Fail")
            }
        }
    }
    fun acceptInvitationFriend(invitationId: Long){
        viewModelScope.launch(handler) {
            val response = repository.acceptInvitationFriend(invitationId)
            if (response.isSuccessful){
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }
    fun denyInvitationFriend(invitationId: Long){
        viewModelScope.launch(handler) {
            val response = repository.denyInvitationFriend(invitationId)
            if (response.isSuccessful){
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }
    fun acceptInvitationGroup(invitationId: Long){
        viewModelScope.launch(handler) {
            val response = repository.acceptInvitationGroup(invitationId)
            if (response.isSuccessful){
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }
    fun denyInvitationGroup(invitationId: Long){
        viewModelScope.launch(handler) {
            val response = repository.denyInvitationGroup(invitationId)
            if (response.isSuccessful){
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }
    fun getListFriends(){
        viewModelScope.launch(handler) {
            val response = repository.getListFriends()
            if (response.isSuccessful){
                listFriends.value = response.body()
            } else {
                Log.d("MyTag", "Fail")
            }
        }
    }
    fun addToGroup(groupUUID: String){
        viewModelScope.launch(handler) {
            val response = repository.addToGroup(groupUUID)
            if (response.isSuccessful){
                tipMessage.value = "Success"
                getUserGroups()
            } else {
                tipMessage.value = "Incorrect uuid"
            }
        }
    }
}