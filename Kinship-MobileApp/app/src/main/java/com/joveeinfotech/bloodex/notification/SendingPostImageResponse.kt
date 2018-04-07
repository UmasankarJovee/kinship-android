package com.joveeinfotech.bloodex.notification

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.model.SendingRequestResponseResult
import com.joveeinfotech.bloodex.utils.Others
import java.util.HashMap

class SendingPostImageResponse : Service(), APIListener {

    var context: Context? = this

    var networkCall : APICall? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //mApiInterface= RetrofitClient.getClient()
        networkCall = APICall(this)
        var i =intent?.extras
        var response = i?.getString("permission")

        val queryParams = HashMap<String, String>()
        queryParams.put("permission",response!!)
        Log.e("MAIN ACTIVITY : ", "inside onStartCommand ${response}")
        networkCall?.APIRequest("api/v1/permissionOfPostImage", queryParams, SendingRequestResponseResult::class.java, this, 1, "Sending Location...",false)

        //intent?.putExtra("key1","value1")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Send Additional Details
                Log.e("API CALL : ", "inside Sending Request Response and onSuccess when")
                val sendingRequestResponseResult = response as SendingRequestResponseResult
                if(sendingRequestResponseResult.status){
                    Others.DLog("SendingRequestResponse :", "Sending Response")
                    //Toast.makeText(context, "${sendingRequestResponseResult.status}", Toast.LENGTH_SHORT).show()
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

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

}