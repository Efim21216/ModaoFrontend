package ru.nsu.fit.modao.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.nsu.fit.modao.models.*
import ru.nsu.fit.modao.repository.MainRepository
import ru.nsu.fit.modao.utils.Constants.Companion.FAIL
import ru.nsu.fit.modao.utils.Constants.Companion.SUCCESS
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
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
    val tokens = MutableLiveData<Tokens>()
    val totalPages = MutableLiveData<Int>()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        messageHandler.value = throwable.message

        Log.e(
            "MyTag", "${throwable.message}\n" +
                    "${throwable.cause}\n${throwable.localizedMessage}\n" +
                    "${throwable.suppressed}\n${throwable.suppressedExceptions}\n" +
                    "${throwable.stackTrace}"
        )
    }

    fun getUser() {
        viewModelScope.launch(handler) {
            val response = repository.getUser()
            if (response.isSuccessful) {
                user.value = response.body()
            } else {
                tipMessage.value = "error"
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun getAccessToken(refreshToken: Tokens) {
        viewModelScope.launch(handler) {
            val response = repository.getAccessToken(refreshToken)
            if (response.isSuccessful) {
                tokens.value?.accessToken = response.body()?.accessToken
            } else {
                tipMessage.value = "error"
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun deleteGroup(groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.deleteGroup(groupId)
            if (response.isSuccessful) {
                tipMessage.value = "Deleted"
            } else {
                Log.d("MyTag", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun archiveGroup(groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.archiveGroup(groupId)
            if (response.isSuccessful) {
                tipMessage.value = "Archived"
            } else {
                Log.d("MyTag", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun getRefreshToken(refreshToken: Tokens) {
        viewModelScope.launch(handler) {
            val response = repository.getRefreshToken(refreshToken)
            if (response.isSuccessful) {
                tokens.value = Tokens(
                    refreshToken = response.body()?.refreshToken,
                    accessToken = response.body()?.accessToken
                )
            } else {
                tipMessage.value = "error"
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun exit() {
        viewModelScope.launch(handler) {
            val response = repository.exit()
            if (response.isSuccessful) {
                tipMessage.value = SUCCESS
            } else {
                tipMessage.value = FAIL
            }
        }
    }

    fun getUserGroups(type: Int) {
        viewModelScope.launch(handler) {
            val response = repository.getUserGroups(type)
            if (response.isSuccessful) {
                userGroups.value = response.body()
            } else {
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun createGroup(group: Group) {
        viewModelScope.launch(handler) {
            val response = repository.createGroup(group)
            if (response.isSuccessful) {
                groupId.value = response.body()
            } else {
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun getGroupExpenses(
        id: Long, mode: Int, filter: Int,
        minTime: Long, maxTime: Long,
        offset: Int, limit: Int
    ) {
        viewModelScope.launch(handler) {
            val response = repository.getGroupExpenses(
                id, mode, filter,
                minTime, maxTime, offset, limit
            )
            if (response.isSuccessful) {
                expenses.value = response.body()?.content!!
                totalPages.value = response.body()?.totalPages!!
            } else {
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun deleteEvent(groupId: Long, eventId: Long, name: String) {
        viewModelScope.launch(handler) {
            val response = repository.deleteEvent(groupId, eventId, name)
            if (response.isSuccessful) {
                tipMessage.value = SUCCESS
            } else {
                tipMessage.value = FAIL
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun makeGroupActive(groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.makeGroupActive(groupId)
            if (response.isSuccessful) {
                tipMessage.value = SUCCESS
            } else {
                tipMessage.value = FAIL
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun deleteUser(groupId: Long, userId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.deleteUser(groupId, userId)
            if (response.isSuccessful) {
                tipMessage.value = SUCCESS
                getUsersInGroup(groupId)
            } else {
                tipMessage.value = FAIL
                Log.e("MyError", "Error ${response.errorBody()?.string()}")
            }
        }
    }

    fun getUsersInGroup(groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.getUsersInGroup(groupId)
            if (response.isSuccessful) {
                usersInGroup.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }

    fun confirmEvent(groupId: Long, eventId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.confirmEvent(groupId, eventId)
            if (!response.isSuccessful) {
                Log.d("MyTag", response.message())
            } else {
                getGroupUnconfirmedExpenses(groupId)
            }
        }
    }

    fun notConfirmEvent(groupId: Long, eventId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.notConfirmEvent(groupId, eventId)
            if (!response.isSuccessful) {
                Log.d("MyTag", response.message())
            } else {
                getGroupUnconfirmedExpenses(groupId)
            }
        }
    }

    fun addUserToGroup(groupId: Long, userId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.addUserToGroup(groupId, userId)
            if (!response.isSuccessful) {
                Log.e("My errors", "Error add friend")
            }
        }
    }

    fun getUserDebtInGroup(userId: Long, groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.getUserDebtInGroup(userId, groupId)
            if (response.isSuccessful) {
                userExpenses.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }

    fun getListOrganizers(groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.getListOrganizers(groupId)
            if (response.isSuccessful) {
                organizers.value = response.body()
            } else {
                organizers.value = arrayOf()
            }
        }
    }

    fun getEventInfo(eventId: Long, groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.getEventInfo(eventId, groupId)
            if (response.isSuccessful) {
                infoEvent.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }

    fun getGroupUnconfirmedExpenses(groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.getGroupUnconfirmedExpenses(groupId)
            if (response.isSuccessful) {
                unconfirmedExpenses.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }

    fun getGroupInfo(groupId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.getGroupInfo(groupId)
            if (response.isSuccessful) {
                groupInfo.value = response.body()
            } else {
                Log.e("MyTag", response.message())
            }
        }
    }

    fun addFriend(userUuid: String) {
        viewModelScope.launch(handler) {
            val response = repository.addFriend(userUuid)
            if (response.isSuccessful) {
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Incorrect data"
            }
        }
    }

    fun getInvitationsFriend() {
        viewModelScope.launch(handler) {
            val response = repository.getInvitationsFriend()
            if (response.isSuccessful) {
                invitationUser.value = response.body()
            } else {
                Log.d("MyTag", "Fail")
            }
        }
    }

    fun getInvitationsGroup() {
        viewModelScope.launch(handler) {
            val response = repository.getInvitationsGroup()
            if (response.isSuccessful) {
                invitationUser.value = response.body()
            } else {
                Log.d("MyTag", "Fail")
            }
        }
    }

    fun acceptInvitationFriend(invitationId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.acceptInvitationFriend(invitationId)
            if (response.isSuccessful) {
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }

    fun denyInvitationFriend(invitationId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.denyInvitationFriend(invitationId)
            if (response.isSuccessful) {
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }

    fun acceptInvitationGroup(invitationId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.acceptInvitationGroup(invitationId)
            if (response.isSuccessful) {
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }

    fun denyInvitationGroup(invitationId: Long) {
        viewModelScope.launch(handler) {
            val response = repository.denyInvitationGroup(invitationId)
            if (response.isSuccessful) {
                tipMessage.value = "Success"
            } else {
                tipMessage.value = "Fail"
            }
        }
    }

    fun getListFriends() {
        viewModelScope.launch(handler) {
            val response = repository.getListFriends()
            if (response.isSuccessful) {
                listFriends.value = response.body()
            } else {
                Log.d("MyTag", "Fail")
            }
        }
    }

    fun addToGroup(groupUUID: String) {
        viewModelScope.launch(handler) {
            val response = repository.addToGroup(groupUUID)
            if (response.isSuccessful) {
                tipMessage.value = "Success"
                getUserGroups(0)
            } else {
                tipMessage.value = "Incorrect uuid"
            }
        }
    }
}