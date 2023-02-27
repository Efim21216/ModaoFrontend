package ru.nsu.fit.modao.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.nsu.fit.modao.repository.Repository

@Suppress("UNCHECKED_CAST")
class RepositoryViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(CreateExpenseViewModel::class.java)){
            return CreateExpenseViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("ViewModel not found.")
    }
}