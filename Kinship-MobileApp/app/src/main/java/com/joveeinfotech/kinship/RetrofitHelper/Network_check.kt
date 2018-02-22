package com.joveeinfotech.kinship

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

/**
 * Created by prandex-and-05 on 6/2/18.
 */
class Network_check {

    companion object {
        var apicall : APICall? =null
        fun isNetworkAvailable(context: Context): Boolean {
            var networkavailable = false


            try {
                val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager.activeNetworkInfo

                if (connectivityManager != null && activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting) {
                    networkavailable = true
                } else {
                    networkavailable = false
                    apicall?.showNetworkError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return networkavailable
        }
    }
}