package com.joveeinfotech.bloodex

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference

//import android.widget.Toast

import com.joveeinfotech.bloodex.model.SendingUserProfileEditResult
import com.joveeinfotech.bloodex.utils.Others.DLog
import io.reactivex.disposables.Disposable
import java.util.HashMap

class SendingUserProfileEdit : Service(), APIListener{

    var lm: LocationManager? = null
    var locationListener: LocationListener? = null
    var context: Context? = this
    internal var mCompositeDisposable: Disposable? = null

    var networkCall : APICall? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //mApiInterface= RetrofitClient.getClient()
        networkCall = APICall(this)
        var i =intent?.extras
        var field = i?.getString("field")
        var value = i?.getString("value")
        var field1 =i?.getString("field1")
        var value1=i?.getString("value1")
        var user_id = getStringPreference(context!!,"user_id","81")
        if(field1 == "" && value1== "") {
            val queryParams = HashMap<String, String>()
            queryParams.put("user_id", user_id!!)
            queryParams.put("user_id", "161")
            queryParams.put("field", field!!)
            queryParams.put("value", value!!)
            DLog("MAIN ACTIVITY : ", "inside location true ${field}${value}")
            networkCall?.APIRequest("api/v1/profile1", queryParams, SendingUserProfileEditResult::class.java, this, 1, "Sending Location...",false)
        } else{
            val queryParams = HashMap<String, String>()
            queryParams.put("user_id", user_id!!)
            queryParams.put("user_id", "161")
            queryParams.put("field", field!!)
            queryParams.put("value", value!!)
            queryParams.put("field1",field1!!)
            queryParams.put("value1",value1!!)
            DLog("MAIN ACTIVITY : ", "inside location${field1}${value1}")
            networkCall?.APIRequest("api/v1/profile1", queryParams, SendingUserProfileEditResult::class.java, this, 1, "Sending Location...",false)
        }
        //intent?.putExtra("key1","value1")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Send Additional Details
                DLog("API CALL : ", "inside Main activity and onSuccess when")
                val sendingUserProfileEditResult = response as SendingUserProfileEditResult
                if(sendingUserProfileEditResult.status){
                    //Toast.makeText(context,"${sendingUserProfileEditResult.status}",Toast.LENGTH_SHORT).show()
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