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
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import com.joveeinfotech.kinship.model.SendingRequestResponseResult
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.Others
import java.util.HashMap

class EmptyActivity : AppCompatActivity(), APIListener {

    var check1_sleep : CheckBox? = null
    var check2_antibiotics : CheckBox? = null
    var check3_alcohol : CheckBox? = null
    var check4_reaction : CheckBox? = null
    var check5_tooth_extraction : CheckBox? = null

    var bn : AppCompatButton? = null

    var phone_number : String? = null
    var response : String? = null
    var networkCall : APICall? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)

        networkCall = APICall(this)
        var i =intent?.extras
        phone_number = i?.getString("phone_number")
        response = i?.getString("response")
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
                    sendRequestResponse()
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9600862338"))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                } else{
                    sendRequestResponse()
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9600862338"))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                }
            }
        }
    }

    private fun sendRequestResponse() {
        val queryParams = HashMap<String, String>()
        queryParams.put("response",response!!)
        Log.e("MAIN ACTIVITY : ", "inside onStartCommand ${response}")
        networkCall?.APIRequest("api/v1/requestresponse", queryParams, SendingRequestResponseResult::class.java, this, 1, "Sending Location...",false)
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Send Additional Details
                Log.e("API CALL : ", "inside Sending Request Response and onSuccess when")
                val sendingRequestResponseResult = response as SendingRequestResponseResult
                if(sendingRequestResponseResult.status){
                    Others.DLog("SendingRequestResponse :", "Sending Response")
                    //Toast.makeText(context, "${sendingRequestResponseResult.status}", Toast.LENGTH_SHORT).show()
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
