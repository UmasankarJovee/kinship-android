package com.joveeinfotech.kinship.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.System_alert_notification
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.kinship.view.Login
import org.json.JSONObject



/**
 * Created by prandex-and-05 on 20/2/18.
 */
class FcmMessagingService : FirebaseMessagingService() {

        /*var srcBitmap : Bitmap? = null
        private var mResources: Resources? = null
*/
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {


            var json = JSONObject(remoteMessage?.data.toString())
            var data = json.getJSONObject("data")

            var phone_number = data.getString("phone_number")
            var blood_group = data.getString("blood_group")
            var units = data.getString("units")
            var hospital = data.getString("hospital")
            var time_to_arrive = data.getString("time_to_arrive")

            setStringPreference(this,"phone_number",phone_number)
            setStringPreference(this,"blood_group",blood_group)
            setStringPreference(this,"units",units)
            setStringPreference(this,"hospital",hospital)
            setStringPreference(this,"time_to_arrive",time_to_arrive)


        //if(getBooleanPreference(this,"notification",true)) {
            /*var title = p0?.notification?.title
            var message = p0?.notification?.body
*/
           /* mResources = resources
            srcBitmap = BitmapFactory.decodeResource(mResources, R.drawable.ic_logo)
*/
            /*var title = "Kinship"
            var message = "Are you ready to give your blood?"
            var notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            notificationBuilder.setContentTitle(title)
            notificationBuilder.setContentText(message)
            notificationBuilder.setSmallIcon(R.drawable.ic_logo);
            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC)
            notificationBuilder.setVibrate(longArrayOf(1000, 1000))
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH)

            val yesReceive = Intent()
            yesReceive.action = "YES_ACTION"
            val pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.addAction(R.drawable.call, "Call", pendingIntentYes)

            val yesReceive2 = Intent()
            yesReceive2.action = "NO_ACTION"
            val pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.addAction(R.drawable.reject, "Reject", pendingIntentYes2)

            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            //notificationBuilder.setSound(uri)

            notificationBuilder.setSound(Uri.parse("android.resource://com.example.prandex_and_05.mobilenotification/"+R.raw.alert_tones))

            var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
*/

/*

            var title ="Kinship"
            var message = "Are you ready to give your blood?"

            */
/*var intent = Intent(applicationContext, GetActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            var pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)*//*


            var notificationBuilder: android.support.v4.app.NotificationCompat.Builder = android.support.v4.app.NotificationCompat.Builder(this)
            notificationBuilder.setContentTitle(title)
            notificationBuilder.setContentText(message)
*/

            //notificationBuilder.setLargeIcon(srcBitmap)
           /* notificationBuilder.setSmallIcon(R.drawable.kinship_logo)
            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC)
            notificationBuilder.setVibrate(longArrayOf(1000, 1000))
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH)

            val yesReceive = Intent()
            yesReceive.action = "YES_ACTION"
            val pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.addAction(R.drawable.call, "Call the Requester", pendingIntentYes)

            *//*if(Settings.canDrawOverlays()){

            }*//*
            //val yesReceive2 = Intent(Login.ACTION_MANAGE_OVERLAY_PERMISSION)
            val yesReceive2 = Intent()
            yesReceive2.action = "NO_ACTION"
            val pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.addAction(R.drawable.reject, "Reject Requester", pendingIntentYes2)
*/


           /* var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
*/

            /*setBooleanPreference(this, "isDisplay", true)

            var isDisplay = getBooleanPreference(this, "isDisplay", false)
            Log.e("FcmMessagingService1 : ","${isDisplay}")*/

            setBooleanPreference(this,"isClickYes",true)
            setBooleanPreference(this,"isClickNo",true)

            startService(Intent(this, System_alert_notification::class.java))
    }
}