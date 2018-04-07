package com.joveeinfotech.bloodex.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.joveeinfotech.bloodex.R

/**
 * Created by prandex-and-05 on 15/2/18.
 */
class CustomToast {

    fun alertToast(mContext : Context, message : String)
    {
        val context = mContext
        val inflater = (mContext as Activity).layoutInflater
        val customToastroot = inflater.inflate(R.layout.custom_toast, null)

        val text = customToastroot.findViewById<TextView>(R.id.custom_toast_textView) as TextView
        val image = customToastroot.findViewById<ImageView>(R.id.custom_toast_imageView) as ImageView
        image.setImageResource(R.mipmap.warning_icon)
        text.text = " $message "

        val customtoast = Toast(context)
        customtoast.view = customToastroot
        customtoast.setGravity(Gravity.BOTTOM, 0, 80)
        customtoast.duration = Toast.LENGTH_LONG
        customtoast.show()
    }

    fun normalToast(mContext : Context, message : String)
    {
        val context = mContext
        val inflater = (mContext as Activity).layoutInflater
        val customToastroot = inflater.inflate(R.layout.custom_toast, null)

        val text = customToastroot.findViewById<TextView>(R.id.custom_toast_textView) as TextView
        val image = customToastroot.findViewById<ImageView>(R.id.custom_toast_imageView) as ImageView
        image.visibility=View.GONE
        text.text = " $message "

        val customtoast = Toast(context)
        customtoast.view = customToastroot
        customtoast.setGravity(Gravity.BOTTOM, 0, 80)
        customtoast.duration = Toast.LENGTH_SHORT
        customtoast.show()
    }
}