package com.jhostinlh.topeliculas.core.navigation

import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.features.vistaFragments.DialogShowError

interface PopUpDelegator {
    fun showErrorWithRetry(title: String, message: String, positiveText: String,
                           negativeText: String, callback: DialogCallback
    )
}