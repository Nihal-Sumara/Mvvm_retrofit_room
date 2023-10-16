package com.example.practiceapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practiceapp.repository.DummyRepository

/*create factory method to initialize parameterize viewModel*/
class DummyVMFactory(private val repository: DummyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DummyViewModel(repository) as T
    }
}