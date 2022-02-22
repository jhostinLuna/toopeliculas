package com.jhostinlh.topeliculas.core.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jhostinlh.topeliculas.core.exception.Failure

abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    fun handleFailure(failure: Failure) {
            this.failure.value = failure

    }
    fun handleFailureHome(failure: Failure) {
        if (this.failure.value != failure){
            this.failure.value = failure
        }
    }
}