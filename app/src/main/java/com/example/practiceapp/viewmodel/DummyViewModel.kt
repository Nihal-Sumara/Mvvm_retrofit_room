package com.example.practiceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practiceapp.local.AuthorDatabase
import com.example.practiceapp.model.DataClass
import com.example.practiceapp.repository.DummyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DummyViewModel(private val repository: DummyRepository) : ViewModel() {
    private var list = MutableLiveData<ArrayList<DataClass.Result>>()
    val showData: LiveData<ArrayList<DataClass.Result>>
        get() = list
    private var setProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = setProgress

    fun getDataFromApi() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                showProgressbar()
                repository.getData().let {
                    if (it.isSuccessful) {
                        hideProgressbar()
                        list.postValue(it.body()?.results)
                    }
                }
            }
        }
    }

    private fun showProgressbar() {
        setProgress.postValue(true)
    }

    fun hideProgressbar() {
        setProgress.postValue(false)
    }
}