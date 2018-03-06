package com.joveeinfotech.kinship

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import com.joveeinfotech.kinship.utils.CustomToast

class EmptyActivity : AppCompatActivity() {


    var check1_sleep : CheckBox? = null
    var check2_antibiotics : CheckBox? = null
    var check3_alcohol : CheckBox? = null
    var check4_reaction : CheckBox? = null
    var check5_tooth_extraction : CheckBox? = null

    var bn : AppCompatButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        createDialog()

    }

    private fun createDialog() {
        val li = LayoutInflater.from(this)
        val confirmDialog = li.inflate(com.joveeinfotech.kinship.R.layout.alert_one_day_diseases_get, null)
        check1_sleep = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.kinship.R.id.alert_one_day_diseases_get_checkbox1_sleep_well) as CheckBox
        check2_antibiotics = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.kinship.R.id.alert_one_day_diseases_get_checkbox2_antibiotics) as CheckBox
        check3_alcohol = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.kinship.R.id.alert_one_day_diseases_get_checkbox3_alcohol) as CheckBox
        check4_reaction = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.kinship.R.id.alert_one_day_diseases_get_checkbox4_reaction) as CheckBox
        check5_tooth_extraction = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.kinship.R.id.alert_one_day_diseases_get_checkbox5_tooth_extraction) as CheckBox
        bn = confirmDialog.findViewById<AppCompatButton>(com.joveeinfotech.kinship.R.id.alert_one_day_diseases_get_submit_button) as AppCompatButton

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        val alertDialog = alert.create()
        alertDialog.show()
        alertDialog.setCancelable(false)

        bn!!.setOnClickListener{

            if(check1_sleep!!.isChecked || check2_antibiotics!!.isChecked
                    || check3_alcohol!!.isChecked || check4_reaction!!.isChecked
                    || check5_tooth_extraction!!.isChecked){
                CustomToast().normalToast(this,"You not able to give your Blood")
            }else{
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8189922043"))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                } else{
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "8189922043"))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                }
            }
        }
    }

    fun sendNotification() {

        var title ="Kinship"
        var message = "Are you ready to give your blood?"

        /*var intent = Intent(applicationContext, GetActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        var pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
       */
        var notificationBuilder: android.support.v4.app.NotificationCompat.Builder = android.support.v4.app.NotificationCompat.Builder(this)
        notificationBuilder.setContentTitle(title)
        notificationBuilder.setContentText(message)
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC)
        notificationBuilder.setVibrate(longArrayOf(1000, 1000, 1000))
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH)

        val yesReceive = Intent()
        yesReceive.action = "YES_ACTION"
        val pendingIntentYes = PendingIntent.getBroadcast(this, 12345, yesReceive, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.addAction(R.drawable.settings, "Yes", pendingIntentYes)

        val yesReceive2 = Intent()
        yesReceive2.action = "NO_ACTION"
        val pendingIntentYes2 = PendingIntent.getBroadcast(this, 12345, yesReceive2, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.addAction(R.drawable.settings, "No", pendingIntentYes2)

        /*notificationBuilder.setContentIntent(pendingIntent)
        notificationBuilder.addAction(R.mipmap.ic_launcher,"Accept",pendingIntent);
        notificationBuilder.addAction(R.mipmap.ic_launcher,"Deny",pendingIntent);*/

        val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        //notificationBuilder.setSound(uri)
        notificationBuilder.setSound(Uri.parse("android.resource://com.example.prandex_and_05.mobilenotification/"+R.raw.alert_tones))
        //notificationBuilder.click(1)

        var notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}
