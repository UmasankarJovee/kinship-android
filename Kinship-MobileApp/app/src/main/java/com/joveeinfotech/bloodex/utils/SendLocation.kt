package com.joveeinfotech.bloodex.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.model.LocationResult
import com.joveeinfotech.bloodex.utils.Others.DLog
import java.util.HashMap

class SendLocation : Service(), APIListener{

    var context: Context? = this
    var latitude : String? = null
    var longitude : String? = null

    var networkCall : APICall? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        networkCall = APICall(context!!)
        latitude = getStringPreference(context!!,"latitude","")
        longitude = getStringPreference(context!!,"longitude","")

        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(this, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("latitude", latitude!!)
        queryParams.put("longitude",longitude!!)
        DLog("MAIN ACTIVITY : ", "inside send location")
        networkCall?.APIRequest("api/v1/search", queryParams, LocationResult::class.java, this, 1, "Fetching...")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> {
                DLog("API CALL : ", "inside UserRequest and onSuccess when")
                val result = response as LocationResult
                if(result.status){
                    DLog("API CALL : ", "success")
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