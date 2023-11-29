package com.jhostinlh.topeliculas.core.platform

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhostinlh.topeliculas.MainActivity
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.functional.DialogCallback
import com.jhostinlh.topeliculas.core.navigation.PopUpDelegator

abstract class BaseFragment: androidx.fragment.app.Fragment() {

    private var popUpDelegator: PopUpDelegator? = null

    open fun onBackPressed() {
        activity?.onBackPressed()
    }
    open fun finishActivity(){
        activity?.finish()
    }
    internal fun showProgress() = with(activity) {
        if (this is MainActivity) this.progressStatus(View.VISIBLE)
    }

    internal fun hideProgress() = with(activity) {
        if (this is MainActivity) this.progressStatus(View.GONE)
    }
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is PopUpDelegator) {
            this.popUpDelegator = activity
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PopUpDelegator) {
            this.popUpDelegator = context
        }
    }

    internal fun showErrorWithRetry(title: String, message: String, positiveText: String,
                                    negativeText: String, callback: DialogCallback
    ) {
        popUpDelegator?.showErrorWithRetry(title, message, positiveText, negativeText, callback)
    }

    internal fun showError(errorCode: Int, errorMessage: String?, dialogCallback: DialogCallback) {
        val genericErrorTitle = getString(R.string.generic_error_title)
        val genericErrorMessage = getString(R.string.generic_error_body)
        showErrorWithRetry(
            genericErrorTitle,
            genericErrorMessage,
            getString(R.string.Retry),
            getString(R.string.Cancel),
            object : DialogCallback {
                override fun onDecline() = dialogCallback.onDecline()
                override fun onAccept() = dialogCallback.onAccept()
            })
    }
}