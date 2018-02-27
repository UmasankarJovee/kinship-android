package com.joveeinfotech.kinship.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.view.NotificationReceiver

/**
 * Created by prandex-and-05 on 27/2/18.
 */
class DisplayDialog(var mContext : Context) {

     fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            0 -> return AlertDialog.Builder(mContext)
                    .setIcon(R.mipmap.home_logo)
                    .setTitle("Are you sure want to give your Blood")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                                NotificationReceiver().getPhoneNumberOfRequestor()
                            }
                    )
                    .setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                                NotificationReceiver().notCome()
                            }
                    ).create()
        }
        return null
    }


}