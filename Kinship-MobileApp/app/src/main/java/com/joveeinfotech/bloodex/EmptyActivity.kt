package com.joveeinfotech.bloodex

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.Toast
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.model.SendingRequestResponseResult
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others
import com.joveeinfotech.bloodex.view.UserProfileFragment
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

    private val LOCATION_PERMISSION_CONSTANT = 100
    private val REQUEST_PERMISSION_SETTING = 101
    private var sentToSettings = false

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
        val confirmDialog = li.inflate(com.joveeinfotech.bloodex.R.layout.alert_one_day_diseases_get, null)
        check1_sleep = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.bloodex.R.id.alert_one_day_diseases_get_checkbox1_sleep_well) as CheckBox
        check2_antibiotics = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.bloodex.R.id.alert_one_day_diseases_get_checkbox2_antibiotics) as CheckBox
        check3_alcohol = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.bloodex.R.id.alert_one_day_diseases_get_checkbox3_alcohol) as CheckBox
        check4_reaction = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.bloodex.R.id.alert_one_day_diseases_get_checkbox4_reaction) as CheckBox
        check5_tooth_extraction = confirmDialog.findViewById<CheckBox>(com.joveeinfotech.bloodex.R.id.alert_one_day_diseases_get_checkbox5_tooth_extraction) as CheckBox
        bn = confirmDialog.findViewById<AppCompatButton>(com.joveeinfotech.bloodex.R.id.alert_one_day_diseases_get_submit_button) as AppCompatButton

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        val alertDialog = alert.create()
        alertDialog.show()
        alertDialog.setCancelable(false)

        bn!!.setOnClickListener{

            permissions()
            //procreedAfterPermission()

        }
    }

    private fun permissions() {
        if (ActivityCompat.checkSelfPermission(this@EmptyActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@EmptyActivity, Manifest.permission.CALL_PHONE)) {
                //Show Information about why you need the permission
                val builder = AlertDialog.Builder(this@EmptyActivity)
                builder.setTitle("Need Phone Call Permission")
                builder.setMessage("This app needs Phone Call permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(this@EmptyActivity, arrayOf(Manifest.permission.CALL_PHONE), LOCATION_PERMISSION_CONSTANT)
                }
                builder.setNegativeButton("Cancel") {
                    dialog, which ->
                    proceedAfterPermission()
                    dialog.cancel()
                }
                builder.show()
                //} else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
            } else if (SharedPreferenceHelper.getBooleanPreference(this, Manifest.permission.CALL_PHONE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                val builder = AlertDialog.Builder(this@EmptyActivity)
                builder.setTitle("Need Phone Call Permission")
                builder.setMessage("This app needs Phone Call permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    sentToSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                    Toast.makeText(baseContext, "Go to Permissions to Grant Phone Call", Toast.LENGTH_LONG).show()
                }
                builder.setNegativeButton("Cancel") {
                    dialog, which ->
                    proceedAfterPermission()
                    dialog.cancel()
                }
                builder.show()
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(this@EmptyActivity, arrayOf(Manifest.permission.CALL_PHONE), LOCATION_PERMISSION_CONSTANT)
            }

            /*val editor = permissionStatus.edit()
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true)
            editor.commit()*/
            SharedPreferenceHelper.setBooleanPreference(this, Manifest.permission.CALL_PHONE, true)

        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission()
        }
    }

    /*private fun proceedAfterPermission() {
        val trans = supportFragmentManager.beginTransaction()
        //userDetailsPresenter = UserDetailsPresenterImpl(trans,this,this)
        trans?.replace(com.joveeinfotech.bloodex.R.id.activity_user_details_frame_layout, UserProfileFragment.newInstance())
        trans?.commit()
    }*/

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CONSTANT) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@EmptyActivity, Manifest.permission.CALL_PHONE)) {
                    //Show Information about why you need the permission
                    val builder = AlertDialog.Builder(this@EmptyActivity)
                    builder.setTitle("Need Phone Call Permission")
                    builder.setMessage("This app needs Phone Call permission")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()
                        ActivityCompat.requestPermissions(this@EmptyActivity, arrayOf(Manifest.permission.CALL_PHONE), LOCATION_PERMISSION_CONSTANT)
                    }
                    builder.setNegativeButton("Cancel") {
                        dialog, which ->
                        proceedAfterPermission()
                        dialog.cancel()
                    }
                    builder.show()
                } else {
                    proceedAfterPermission()
                    Toast.makeText(baseContext, "Unable to get Permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this@EmptyActivity, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(this@EmptyActivity, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }


    private fun proceedAfterPermission() {
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

    private fun sendRequestResponse() {
        val queryParams = HashMap<String, String>()
        queryParams.put("response",response!!)
        Log.e("MAIN ACTIVITY : ", "inside onStartCommand ${response}")
        networkCall?.APIRequest("api/v1/putRequestresponse", queryParams, SendingRequestResponseResult::class.java, this, 1, "Sending Location...",false)
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
