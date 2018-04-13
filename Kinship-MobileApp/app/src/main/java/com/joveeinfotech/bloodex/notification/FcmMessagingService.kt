package com.joveeinfotech.bloodex.notification

import android.content.Intent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.bloodex.model.*
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.utils.SharedData
import org.json.JSONObject
import java.util.HashMap


/**
 * Created by prandex-and-05 on 20/2/18.
 */
class FcmMessagingService : FirebaseMessagingService(), APIListener {

    /*var srcBitmap : Bitmap? = null
    private var mResources: Resources? = null
*/

    var session: SharedData? = null
    var networkCall : APICall? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

            session = SharedData(this)
            networkCall = APICall(this)

            var json = JSONObject(remoteMessage?.data.toString())
            //var data = json.getJSONObject("data")

            DLog("data  :", "${json}")
            var data = json.getInt("data")

            if(data == 1){ // Get Blood Request // design and Code
                val queryParams = HashMap<String, String>()
                queryParams.put("input","bloodRequest")
                queryParams.put("data",data.toString())
                DLog("MAIN ACTIVITY : ","inside get blood Request" )
                networkCall?.APIRequest("api/v1/getRequest",queryParams, FcmRequestData::class.java,this, 1, "Authenticating...",false)
            }
            else if(data == 2){ // Get Permission of Post Image in donor //
                val queryParams = HashMap<String, String>()
                queryParams.put("input","permission")
                DLog("MAIN ACTIVITY : ","inside get permission of post image of donor" )
                networkCall?.APIRequest("api/v1/getRequest",queryParams, FcmRequestData::class.java,this, 2, "Authenticating...",false)
            }
            else if(data == 3){
                val queryParams = HashMap<String, String>()
                queryParams.put("input","postImage")
                queryParams.put("data",data.toString())
                DLog("MAIN ACTIVITY : ","inside button" )
                networkCall?.APIRequest("api/v1/getRequest",queryParams, FcmRequestData::class.java,this, 3, "Authenticating...",false)
            }
            else if(data == 4){ // Display Information // design and code
                val queryParams = HashMap<String, String>()
                queryParams.put("info","info")
                DLog("MAIN ACTIVITY : ","inside display information" )
                networkCall?.APIRequest("api/v1/getInfo",queryParams, FcmDisplayInfoResult::class.java,this, 4, "Authenticating...",false)
            }
            else if(data == 5){ // user logout
                session?.logoutUser()
            }
            else if(data == 6){ // Birthday Notification
                val queryParams = HashMap<String, String>()
                queryParams.put("birthday","birthday")
                DLog("MAIN ACTIVITY : ","inside birthday notification" )
                networkCall?.APIRequest("api/v1/getBirthdayNotification",queryParams, FcmDisplayInfoResult::class.java,this, 6, "Authenticating...",false)
            }
            else if(data == 7){ // Blood donation Day Notification // design
                startService(Intent(this, System_alert_Blood_donation_Day::class.java))
            }
            else if(data == 8){ // Donor Invite // design
                val queryParams = HashMap<String, String>()
                queryParams.put("invite","invite")
                DLog("MAIN ACTIVITY : ","inside invite blood donor" )
                networkCall?.APIRequest("api/v1/getBirthdayNotification",queryParams, FcmBloodDonorInviteResult::class.java,this,8 , "Authenticating...",false)
            }
            else if(data == 9){ // Blood Donation Camp // design
                val queryParams = HashMap<String, String>()
                queryParams.put("camp","camp")
                DLog("MAIN ACTIVITY : ","inside invite blood donation Camp" )
                networkCall?.APIRequest("api/v1/getBloodCamp",queryParams, FcmBloodDonationCamp::class.java,this,9 , "Authenticating...",false)
            }

            //setBooleanPreference(this,"isClickYes",true)
            //setBooleanPreference(this,"isClickNo",true)

        //setStringPreference(this,"phone_number",phone_number)

            /*setStringPreference(this,"blood_group",blood_group)
            setStringPreference(this,"units",units)
            setStringPreference(this,"hospital",hospital)
            setStringPreference(this,"time_to_arrive",time_to_arrive)*/


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

            /*setBooleanPreference(this,"isClickYes",true)
            setBooleanPreference(this,"isClickNo",true)

            startService(Intent(this, System_alert_notification::class.java))*/
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val result = response as FcmRequestData
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (true) {
                    setStringPreference(this,"blood_group",result.blood_group)
                    setStringPreference(this,"units",result.units)
                    setStringPreference(this,"hospital",result.hospital_name)
                    setStringPreference(this,"time_in_number",result.time_in_number)
                    setStringPreference(this,"time_in_string",result.time_in_string)

                    setBooleanPreference(this,"isClickYes",true)
                    setBooleanPreference(this,"isClickNo",true)

                    startService(Intent(this, System_alert_notification::class.java))

                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    //snackbar((this as Activity).findViewById(R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            2 -> {

            }
            3 -> {

            }
            4 -> {
                val result = response as FcmDisplayInfoResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (true) {
                    setStringPreference(this,"info",result.info)
                    startService(Intent(this, System_alert_User_Information::class.java))

                    startService(Intent(this, System_alert_User_Information::class.java))
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                }
            }
            8 -> {
                val result = response as FcmBloodDonorInviteResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (true) {
                    setStringPreference(this,"inviteDate",result.date)
                    setStringPreference(this,"inviteTime",result.time)
                    setStringPreference(this,"inviteVenue",result.venue)

                    startService(Intent(this, System_alert_Blood_Donor_Invite::class.java))
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                }
            }
            9 -> {
                val result = response as FcmBloodDonationCamp
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (true) {
                    setStringPreference(this,"campDate",result.date)
                    setStringPreference(this,"campTime",result.time)
                    setStringPreference(this,"campVenue",result.venue)

                    startService(Intent(this, System_alert_Blood_Donation_Camp::class.java))
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {

    }
    override fun onNetworkFailure(from: Int) {

    }
}