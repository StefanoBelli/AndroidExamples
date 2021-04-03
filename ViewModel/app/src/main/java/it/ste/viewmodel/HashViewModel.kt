package it.ste.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HashViewModel: ViewModel() {
    private val hashesLiveData = MutableLiveData<HashModel>()

    val hashes
        get() = hashesLiveData

    fun doHash(clearText: String) {
        hashesLiveData.value = HashModel(clearText)
    }
}