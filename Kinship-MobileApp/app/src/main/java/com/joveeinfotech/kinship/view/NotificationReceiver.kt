package com.joveeinfotech.kinship.view

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.model.ReplyBloodRequestResult
import org.jetbrains.anko.design.snackbar
import java.util.HashMap
import android.support.v4.content.ContextCompat.startActivity
import com.joveeinfotech.kinship.model.NotComeForRequestResult
import com.joveeinfotech.kinship.utils.DisplayDialog

class NotificationReceiver : BroadcastReceiver(), APIListener {

    var networkCall : APICall? = null
    var context : Context? = null

    override fun onReceive(context: Context?, intent: Intent?) {

        networkCall = APICall(context!!)
        this.context=context

        val action = intent?.action
        if ("YES_ACTION" == action) {
            Toast.makeText(context, "YES CALLED", Toast.LENGTH_SHORT).show()
            //DisplayDialog(context).onCreateDialog(0)
            getPhoneNumberOfRequestor()
        } else if ("NO_ACTION" == action) {
            Toast.makeText(context, "STOP CALLED", Toast.LENGTH_SHORT).show()
            notCome()
        }
    }

    fun getPhoneNumberOfRequestor() {
        val queryParams = HashMap<String, String>()
        queryParams.put("reply","yes")
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/replyRequest",queryParams, ReplyBloodRequestResult::class.java,this, 1, "Authenticating...")
    }

    fun notCome() {
        val queryParams = HashMap<String, String>()
        queryParams.put("reply","no")
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/replyRequest",queryParams, NotComeForRequestResult::class.java,this, 2, "Authenticating...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val result = response as ReplyBloodRequestResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    var phoneNumber = result.phoneNumber
                    if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber))
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context?.startActivity(intent)
                    } else{
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber))
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context?.startActivity(intent)
                    }
                    /*val callIntent = Intent(Intent.ACTION_CALL)
                    callIntent.data = Uri.parse("tel:" + getPhoneNumber())
                    startActivity(context!!,callIntent,null)*/
                } else {
                    snackbar((context as Activity).findViewById(android.R.id.content), "Press again 'Yes'")
                }
            }
            2 -> { // User Login
                val result = response as NotComeForRequestResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {

                } else {
                    snackbar((context as Activity).findViewById(android.R.id.content), "Press again 'No'")
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onNetworkFailure(from: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}