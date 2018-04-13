package com.joveeinfotech.bloodex.notification

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.bloodex.utils.Others.DLog
import kotlinx.android.synthetic.main.system_alert_get_permission_of_donor_get.view.*

/**
 * Created by prandex-and-05 on 16/3/18.
 */
class System_alert_Get_Permission_of_Donor : Service() {

    var networkCall : APICall? = null
    var objPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        objPlayer?.start()
        /*onCreate()

        SharedPreferenceHelper.setBooleanPreference(this, "isDisplay", false)

        var isDisplay = SharedPreferenceHelper.getBooleanPreference(this, "isDisplay", false)
        Log.e("FcmMessagingService2 : ","${isDisplay}")*/
        //return super.onStartCommand(intent, flags, startId)
        DLog("SystemAlert : ", "onStartCommand1")
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        DLog("SystemAlert : ", "onCreate1")
        networkCall = APICall(this)
        objPlayer = MediaPlayer.create(this, R.raw.alert_tones)

        var v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            v.vibrate(500)
        }

        /*Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            //deprecated in API 26
            v.vibrate(500)
        }*/

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

        val myView = inflater.inflate(R.layout.system_alert_get_permission_of_donor_get, null)

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                wm.addView(myView, params1)
            }
        } else {
            wm.addView(myView, params1)
        }

        myView.system_alert_get_permission_of_donor_ok_textView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                var isClickYes = getBooleanPreference(this@System_alert_Get_Permission_of_Donor, "isClickYes", false)
                DLog("SampleService : ", "Called in click ${isClickYes}")
                if(isClickYes) {
                    DLog("SampleService : ", "inside if")
                    setBooleanPreference(this@System_alert_Get_Permission_of_Donor, "isClickNo", false)
                    myView.visibility = View.INVISIBLE

                    var intent = Intent(this@System_alert_Get_Permission_of_Donor, SendingPostImageResponse::class.java)
                    intent.putExtra("permission", "yes")
                    this@System_alert_Get_Permission_of_Donor.startService(intent)
                }
                return true
            }
        })

        myView.system_alert_get_permission_of_donor_cancel_textView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                var isClickNo = getBooleanPreference(this@System_alert_Get_Permission_of_Donor, "isClickNo", false)
                DLog("SampleService : ", "Called in click ${isClickNo}")
                if(isClickNo) {
                    DLog("SampleService : ", "inside if")
                    setBooleanPreference(this@System_alert_Get_Permission_of_Donor, "isClickNo", false)
                    myView.visibility = View.INVISIBLE

                    var intent = Intent(this@System_alert_Get_Permission_of_Donor, SendingPostImageResponse::class.java)
                    intent.putExtra("permission", "no")
                    this@System_alert_Get_Permission_of_Donor.startService(intent)
                }
                return true
            }
        })

        this.stopSelf()
    }
}