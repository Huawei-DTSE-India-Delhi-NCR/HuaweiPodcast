package com.huawei.podcast.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ProgressBar
import com.huawei.podcast.R

object ProgressDialog {
    // progress bar handling
    fun showProgress(activity: Activity): Dialog {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(
                ColorDrawable(0))
        dialog.setContentView(R.layout.dialog_progress)
        val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.indeterminateDrawable.setColorFilter(activity.resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
        dialog.setCancelable(false)
        dialog.show()
        return dialog
    }
}