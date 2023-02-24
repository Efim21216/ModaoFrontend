package ru.nsu.fit.modao.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.fit.modao.models.Group
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.Repository
import java.net.SocketTimeoutException

class MainViewModel(private val repository: Repository) : ViewModel() {
    val user = MutableLiveData<User>()
    val groupId = MutableLiveData<Long>()
    private val handler = CoroutineExceptionHandler { _, throwable -> throwable.printStackTrace()}
    fun getUser(id: Long) {
        viewModelScope.launch(handler) {
            val response = repository.getUser(id)
            if (response.isSuccessful) {
                user.value = response.body()
            } else {
                Log.e("MyError", response.message())
            }
        }
    }
    fun createGroup(id: Long, group: Group){
        viewModelScope.launch(handler) {
            val response = repository.createGroup(id, group)
            if (response.isSuccessful){
                groupId.value = response.body()
            }
            else {
                Log.e("MyError", response.message())
            }
        }
    }

}