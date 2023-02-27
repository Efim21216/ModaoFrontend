package ru.nsu.fit.modao.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nsu.fit.modao.models.Authorization
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.Repository
import java.net.SocketTimeoutException

class LoginViewModel(private val repository: Repository): ViewModel() {
    val token = MutableLiveData<Authorization>()
    val userId = MutableLiveData<Long>()
    val message = MutableLiveData<String>()
    private val handler = CoroutineExceptionHandler { _, _ ->  message.value = "Server problems"}
    fun login(login: String, password: String){
        if (login == "" || password == ""){
            message.value = "Enter the data"
            return
        }
        val user = User(login = login, password = password)
        viewModelScope.launch(handler) {
            val response = repository.login(user)
            if (response.isSuccessful){
                token.value = response.body()
                return@launch
            }
            message.value = "Incorrect login or password"
        }
    }
    fun createUser(login: String, password: String, name: String){
        if (login == "" || password == "" || name == ""){
            message.value = "Enter the data"
            return
        }
        val user = User(login = login, password = password, username = name)
        viewModelScope.launch(handler) {
            val response = repository.createUser(user)
            if (response.isSuccessful){
                userId.value = response.body()
            } else {
                message.value = "User with this login already exists"
            }
        }
    }
}