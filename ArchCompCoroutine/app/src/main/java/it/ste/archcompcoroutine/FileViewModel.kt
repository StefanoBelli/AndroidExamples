package it.ste.archcompcoroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FileViewModel : ViewModel() {
    val data = MutableLiveData<Response>()

    fun fetch(url : String) {
        viewModelScope.launch {
            data.value = Repository.fetch(url)
        }
    }
}