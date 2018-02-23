package com.joveeinfotech.kinship.notification

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.model.SendTokenResult
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
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, SendTokenResult::class.java, this, 1, "Sending...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Get Districts
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val result = response as SendTokenResult
                if(result.status){
                    //Toast.makeText(applicationContext,"Stored", Toast.LENGTH_LONG).show()
                    Log.e("FcmInstanceIdService","send token")
                }else{
                    //Toast.makeText(applicationContext,"Not Stored", Toast.LENGTH_LONG).show()
                    Log.e("FcmInstanceIdService","error on send token")
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {

    }
    override fun onNetworkFailure(from: Int) {

    }
}