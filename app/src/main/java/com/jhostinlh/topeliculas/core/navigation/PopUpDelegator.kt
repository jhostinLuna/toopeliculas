package com.jhostinlh.topeliculas.core.navigation

import com.jhostinlh.topeliculas.core.functional.DialogCallback


interface PopUpDelegator {

    fun showErrorWithRetry(title: String, message: String, positiveText: String,
                           negativeText: String, callback: DialogCallback
    )
    fun progressStatus(viewStatus: Int)
}