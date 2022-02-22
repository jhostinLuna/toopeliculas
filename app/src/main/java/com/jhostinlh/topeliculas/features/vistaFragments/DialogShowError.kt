package com.jhostinlh.topeliculas.features.vistaFragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.jhostinlh.topeliculas.R
import com.jhostinlh.topeliculas.core.functional.DialogCallback

class DialogShowError():DialogFragment() {
    internal var dialogCallback: DialogCallback? = null
    internal var title:String =""
    internal var message:String =""
    internal var positiveText:String =""
    internal var negativeText:String =""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle(title)
            builder.setMessage(message)
                .setPositiveButton(positiveText,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialogCallback?.onAccept()
                    })
                .setNegativeButton(negativeText,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                        dialogCallback?.onDecline()
                        this.dismiss()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}