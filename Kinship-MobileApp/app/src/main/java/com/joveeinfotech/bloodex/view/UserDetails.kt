package com.joveeinfotech.bloodex.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RadioButton
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.Home
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.bloodex.presenter.UserDetailsPresenterImpl
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others.DLog

class UserDetails : AppCompatActivity(), UserDetailsView {

    var networkCall : APICall? = null

    var isCompleteProfile : Boolean? = null
    var isCompleteAddress : Boolean? = null
    var isCompleteAdditionalDetails : Boolean? = null
    var isCompleteHealthDetails : Boolean? = null

    private val LOCATION_PERMISSION_CONSTANT = 100
    private val REQUEST_PERMISSION_SETTING = 101
    private var sentToSettings = false

    var userDetailsPresenter : UserDetailsPresenterImpl? = null

    var radioRequestor : RadioButton? = null
    var radioDonor : RadioButton? = null
    var buttonNext : Button? = null
    var alertOptionsGet : AlertDialog? = null
    var searchOption : String? = null

    var user_id : String? = null
    var userOption : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        //permissions()
        //proceedAfterPermission()
        val trans = supportFragmentManager.beginTransaction()
        userDetailsPresenter = UserDetailsPresenterImpl(trans,this,this)

        /*user_id = getStringPreference(this,"user_id","")
        DLog("UserDetails : ", "UserDetails1 ${user_id}")
        userOption = getBooleanPreference(this,"userOption${user_id}",true)
        if(userOption!!) {
            displayDialog()
        }else{
            proceedAfterPermission()
        }*/
        //val trans = supportFragmentManager.beginTransaction()
        //userDetailsPresenter = UserDetailsPresenterImpl(trans,this,this)
       /* trans?.replace(com.joveeinfotech.bloodex.R.id.activity_user_details_frame_layout, UserProfileFragment.newInstance())
        trans?.commit()*/

    }

    private fun displayDialog() {
        val li1 = LayoutInflater.from(this)
        val confimDialog1 = li1.inflate(com.joveeinfotech.bloodex.R.layout.alert_requestor_or_donor_options_get, null)
        radioRequestor = confimDialog1.findViewById<RadioButton>(com.joveeinfotech.bloodex.R.id.alert_requestor_or_donor_options_get_requestor_radio) as RadioButton
        radioDonor = confimDialog1.findViewById<RadioButton>(com.joveeinfotech.bloodex.R.id.alert_requestor_or_donor_options_get_donor_radio) as RadioButton

        buttonNext = confimDialog1.findViewById<Button>(com.joveeinfotech.bloodex.R.id.alert_requestor_or_donor_options_get_next_button) as Button

        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        alertOptionsGet = alert1.create()
        alertOptionsGet?.show()
        alertOptionsGet?.setCancelable(false)

        radioRequestor!!.setOnClickListener{
            radioDonor!!.isChecked = false
            searchOption = "Requestor"
        }
        radioDonor!!.setOnClickListener{
            radioRequestor!!.isChecked = false
            searchOption = "Donor"
        }
        buttonNext!!.setOnClickListener {
            if(searchOption!!.trim().isNotEmpty()){
                if(searchOption == "Requestor"){
                    alertOptionsGet?.dismiss()
                    setBooleanPreference(this,"userOption${user_id}",false)
                    setStringPreference(this,"userOptionString${user_id}","Requestor")
                    var i = Intent(applicationContext, Home::class.java)
                    startActivity(i)
                    finishAffinity()
                }else{
                    alertOptionsGet?.dismiss()
                    setBooleanPreference(this,"userOption${user_id}",false)
                    setStringPreference(this,"userOptionString${user_id}","Donor")
                    proceedAfterPermission()
                }
            }else{
                CustomToast().alertToast(this,this.getString(R.string.select_any_one_option))
            }
            //alertDialogNotDonatedConfirm?.dismiss()
        }
    }

    private fun permissions() {
        if (ActivityCompat.checkSelfPermission(this@UserDetails, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@UserDetails, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show Information about why you need the permission
                val builder = AlertDialog.Builder(this@UserDetails)
                builder.setTitle("Need Location Permission")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    ActivityCompat.requestPermissions(this@UserDetails, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CONSTANT)
                }
                builder.setNegativeButton("Cancel") {
                    dialog, which ->
                        proceedAfterPermission()
                        dialog.cancel()
                }
                builder.show()
            //} else if (permissionStatus.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
            } else if (SharedPreferenceHelper.getBooleanPreference(this, Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                val builder = AlertDialog.Builder(this@UserDetails)
                builder.setTitle("Need Location Permission")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    sentToSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                    //Toast.makeText(baseContext, "Go to Permissions to Grant Location", Toast.LENGTH_LONG).show()
                    CustomToast().normalToast(this,this.getString(R.string.go_to_permissions_to_grant_location))
                }
                builder.setNegativeButton("Cancel") {
                    dialog, which ->
                    proceedAfterPermission()
                    dialog.cancel()
                }
                builder.show()
            } else {
                //just request the permission
                ActivityCompat.requestPermissions(this@UserDetails, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CONSTANT)
            }

            /*val editor = permissionStatus.edit()
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true)
            editor.commit()*/
            SharedPreferenceHelper.setBooleanPreference(this, Manifest.permission.ACCESS_FINE_LOCATION, true)

        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission()
        }
    }

    private fun proceedAfterPermission() {
        val trans = supportFragmentManager.beginTransaction()
        //userDetailsPresenter = UserDetailsPresenterImpl(trans,this,this)
        trans?.replace(com.joveeinfotech.bloodex.R.id.activity_user_details_frame_layout, UserProfileFragment.newInstance())
        trans?.commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CONSTANT) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@UserDetails, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    val builder = AlertDialog.Builder(this@UserDetails)
                    builder.setTitle("Need Location Permission")
                    builder.setMessage("This app needs Location permission")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()
                        ActivityCompat.requestPermissions(this@UserDetails, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), LOCATION_PERMISSION_CONSTANT)
                    }
                    builder.setNegativeButton("Cancel") {
                        dialog, which ->
                        proceedAfterPermission()
                        dialog.cancel()
                    }
                    builder.show()
                } else {
                    proceedAfterPermission()
                    //Toast.makeText(baseContext, "Unable to get Permission", Toast.LENGTH_LONG).show()
                    CustomToast().normalToast(this,this.getString(R.string.unable_to_get_permissions))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this@UserDetails, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(this@UserDetails, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    override fun setNavigationFragmentValues(isCompleteProfile: Boolean, isCompleteAddress: Boolean, isCompleteAdditionalDetails: Boolean, isCompleteHealthDetails: Boolean) {
        this.isCompleteProfile = isCompleteProfile
        this.isCompleteAddress = isCompleteAddress
        this.isCompleteAdditionalDetails = isCompleteAdditionalDetails
        this.isCompleteHealthDetails = isCompleteHealthDetails
    }

    fun getIsCompleteProfile() : Boolean{
        return isCompleteProfile!!
    }
    fun getIsCompleteAddress() : Boolean{
        return isCompleteAddress!!
    }
    fun getIsCompleteAdditionalDetails() : Boolean{
        return isCompleteAdditionalDetails!!
    }
    fun getIsCompleteHealthDetails() : Boolean{
        return isCompleteHealthDetails!!
    }
    override fun closeActivity() {
        finish()
    }
}
