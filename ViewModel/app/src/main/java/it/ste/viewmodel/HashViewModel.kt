package it.ste.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class HashViewModel: ViewModel() {
    private val liveData = MutableLiveData<String>()

    fun setObserver(owner: LifecycleOwner, observer: Observer<String>) {
        liveData.observe(owner, observer)
    }

    fun clearText(clear: String) {
        liveData.value = HashModel(clear).toString()
    }
}