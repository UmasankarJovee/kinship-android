package com.joveeinfotech.kinship

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.kinship.model.LoginResult
import kotlinx.android.synthetic.main.system_alert_notification_get.view.*
import java.util.HashMap

/**
 * Created by prandex-and-05 on 16/3/18.
 */
class System_alert_notification : Service() {

    var networkCall : APICall? = null

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*onCreate()

        SharedPreferenceHelper.setBooleanPreference(this, "isDisplay", false)

        var isDisplay = SharedPreferenceHelper.getBooleanPreference(this, "isDisplay", false)
        Log.e("FcmMessagingService2 : ","${isDisplay}")*/
        //return super.onStartCommand(intent, flags, startId)
        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        networkCall = APICall(this)

        var LAYOUT_FLAG: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE
        }

        var params1 = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT)

        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val myView = inflater.inflate(R.layout.system_alert_notification_get, null)

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                wm.addView(myView, params1)
            }
        } else {
            wm.addView(myView, params1)
        }


        var phone_number = getStringPreference(this, "phone_number", "")
        var blood_group = getStringPreference(this, "blood_group", "")
        var units = getStringPreference(this, "units", "")
        var hospital = getStringPreference(this, "hospital", "")
        var time_to_arrive = getStringPreference(this, "time_to_arrive", "")

        myView.system_alert_notification_get_TextView_blood_group.text = blood_group
        myView.system_alert_notification_get_TextView_units.text = units
        myView.system_alert_notification_get_TextView_hospital.text = hospital
        myView.system_alert_notification_get_TextView_time_to_arrive.text = time_to_arrive

        myView.system_alert_notification_get_TextView_yes_option.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                var isClickYes = getBooleanPreference(this@System_alert_notification, "isClickYes", false)
                Log.e("SampleService : ", "Called in click ${isClickYes}")
                if(isClickYes){
                    Log.e("SampleService : ", "inside if")
                    setBooleanPreference(this@System_alert_notification, "isClick", false)
                    myView.visibility = View.INVISIBLE

                    var intent= Intent(this@System_alert_notification,SendingRequestResponse::class.java)
                    intent.putExtra("response","yes")
                    startService(intent)

                    var i = Intent(this@System_alert_notification,EmptyActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    i.putExtra("phone_number",phone_number)
                    startActivity(i)

                }
                //layout.removeAllViewsInLayout()
                return true
            }
        })

        myView.system_alert_notification_get_TextView_no_option.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                var isClickNo = getBooleanPreference(this@System_alert_notification, "isClickNo", false)
                Log.e("SampleService : ", "Called in click ${isClickNo}")
                if(isClickNo){
                    Log.e("SampleService : ", "inside if")
                    setBooleanPreference(this@System_alert_notification, "isClick", false)
                    myView.visibility = View.INVISIBLE


                    var intent= Intent(this@System_alert_notification,SendingRequestResponse::class.java)
                    intent.putExtra("response","no")
                    startService(intent)
                    //sendNoResponse()
                }
                //layout.removeAllViewsInLayout()
                return true
            }
        })
    }
}