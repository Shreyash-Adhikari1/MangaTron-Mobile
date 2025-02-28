package com.example.mangatronmobile.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.mangatronmobile.R

class LoadingUtils (val activity : Activity){
    lateinit var alertDialog: AlertDialog

    fun show(){
        val builder= AlertDialog.Builder(activity)

        val designView= activity.layoutInflater.inflate(
            R.layout.loading,
            null
        )
        builder.setView(designView)


        builder.setCancelable(false)//stops user from cancelling loading

        alertDialog=builder.create()
        alertDialog.show()
    }
    fun dismiss(){
    alertDialog.dismiss()
    }
}