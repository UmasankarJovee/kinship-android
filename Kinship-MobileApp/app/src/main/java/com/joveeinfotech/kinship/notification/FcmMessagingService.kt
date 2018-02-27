package com.joveeinfotech.kinship.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.joveeinfotech.kinship.AlertActivity
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.kinship.view.Login

/**
 * Created by prandex-and-05 on 20/2/18.
 */
class FcmMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {

        if(getBooleanPreference(this,"notification",true)) {
            var title = p0?.notification?.title
            var message = p0?.notification?.body

            /*var intent = Intent(applicationContext, Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            var pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
         */ var notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            notificationBuilder.setContentTitle(title)
            notificationBuilder.setContentText(message)
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC)
            notificationBuilder.setVibrate(longArrayOf(1000, 1000))

            val yesReceive = Intent()
            yesReceive.action = "YES_ACTION"
            val pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.addAction(R.mipmap.ic_launcher, "Yes", pendingIntentYes)

            val yesReceive2 = Intent()
            yesReceive2.action = "NO_ACTION"
            val pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.addAction(R.mipmap.ic_launcher, "No", pendingIntentYes2)

            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notificationBuilder.setSound(uri)

            var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        }
    }
}