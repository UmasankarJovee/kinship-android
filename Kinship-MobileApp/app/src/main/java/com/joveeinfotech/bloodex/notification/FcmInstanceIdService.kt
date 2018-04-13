package com.joveeinfotech.bloodex.notification

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.model.SendTokenResult
import com.joveeinfotech.bloodex.utils.Others.DLog
import java.util.HashMap

/**
 * Created by prandex-and-05 on 20/2/18.
 */
class FcmInstanceIdService : FirebaseInstanceIdService(), APIListener {

    var networkCall : APICall? = null

    override fun onTokenRefresh() {

        var recent_token = FirebaseInstanceId.getInstance().token
        var sp : SharedPreferences = applicationContext.getSharedPreferences("FCM_PREF", Context.MODE_PRIVATE)
        var spe : SharedPreferences.Editor = sp.edit()
        spe.putString("FCM_TOKEN",recent_token)
        spe.commit()
        sendTokenToServer(recent_token!!)
    }

    private fun sendTokenToServer(recent_token : String) {
        networkCall = APICall(this)
        val queryParams = HashMap<String, String>()
        queryParams.put("id", recent_token)
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, SendTokenResult::class.java, this, 1, "Sending...",false)
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Get Districts
                DLog("API CALL : ", "inside Main activity and onSuccess when")
                val result = response as SendTokenResult
                if(result.status){
                    //Toast.makeText(applicationContext,"Stored", Toast.LENGTH_LONG).show()
                    DLog("FcmInstanceIdService","send token")
                }else{
                    //Toast.makeText(applicationContext,"Not Stored", Toast.LENGTH_LONG).show()
                    DLog("FcmInstanceIdService","error on send token")
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {

    }
    override fun onNetworkFailure(from: Int) {

    }
}