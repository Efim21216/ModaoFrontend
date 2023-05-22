package ru.nsu.fit.modao.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.nsu.fit.modao.models.Authorization
import ru.nsu.fit.modao.models.Registration
import ru.nsu.fit.modao.models.User
import ru.nsu.fit.modao.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: MainRepository): ViewModel() {
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
                val gson = Gson()
                val errorResponse = gson.fromJson(response.errorBody()?.string()!!,
                    Registration::class.java)
                when (errorResponse.message!!) {
                    "Пароль должен содержать " +
                            "как минимум одну букву в нижнем регистре" -> {
                        message.value = "The password must contain at least " +
                                "one letter in lowercase"
                    }
                    "Пароль должен содержать " +
                            "как минимум одну букву в верхнем регистре" -> {
                        message.value = "The password must contain at least " +
                                "one uppercase letter"
                            }
                    "Пароль слишком короткий\n" -> {
                        message.value = "The password must contain at least 6 characters"
                    }
                    "Логин должен быть от 3 до 20 символов\n" -> {
                        message.value = "Login must be from 3 to 20 characters "
                    }
                    else -> message.value = "User with this login already exists"
                }

            }
        }
    }
}