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
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import kotlinx.android.synthetic.main.system_alert_blood_donation_camp_get.view.*

/**
 * Created by prandex-and-05 on 16/3/18.
 */
class System_alert_Blood_Donation_Camp : Service() {

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
        Log.e("SystemAlert : ", "onStartCommand1")
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        Log.e("SystemAlert : ", "onCreate1")
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

        val myView = inflater.inflate(R.layout.system_alert_blood_donation_camp_get, null)

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                wm.addView(myView, params1)
            }
        } else {
            wm.addView(myView, params1)
        }


        var date = getStringPreference(this, "campDate", "")
        var time = getStringPreference(this, "campTime", "")
        var venue = getStringPreference(this, "campVenue", "")

        myView.system_alert_blood_donation_camp_date_textView.text = date
        myView.system_alert_blood_donation_camp_time_textView.text = time
        myView.system_alert_blood_donation_camp_venue_textView.text = venue

        myView.system_alert_blood_donation_camp_ok_textView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                myView.visibility = View.INVISIBLE
                return true
            }
        })
        this.stopSelf()
    }
}