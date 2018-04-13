package com.joveeinfotech.bloodex

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.model.*
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.utils.SharedData
import com.joveeinfotech.bloodex.view.UserRegistration
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

class AppRegister : AppCompatActivity(), APIListener {

    var networkCall : APICall? = null
    var session: SharedData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_register)

        networkCall = APICall(this)
        session = SharedData(this)
        //sendAppRegister()

        if (Network_check.isNetworkAvailable(this)) {

            sendAppRegister()

        }else{
            showDialog(0)
        }


    }

    private fun sendAppRegister() {
        var deviceId = Settings.Secure.getString(this.contentResolver,
                Settings.Secure.ANDROID_ID)
        var deviceModel= android.os.Build.MODEL
        var deviceName= android.os.Build.MANUFACTURER
        var deviceVersion= android.os.Build.VERSION.SDK_INT
        var device_manufacturer = Build.MANUFACTURER

        val queryParams = HashMap<String, String>()
        queryParams.put("android_id",deviceId)
        queryParams.put("device_name",deviceName)
        queryParams.put("device_version",deviceVersion.toString())
        queryParams.put("device_model",deviceModel)
        queryParams.put("manufacturer",device_manufacturer)
        networkCall?.APIRequest("api/v1/appRegister", queryParams, AppRegisterResult::class.java, this, 1, "Setting your Password...",false)
    }


    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> {
                val result1 = response as AppRegisterResult
                var result = result1.data
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (true) {

                    SharedPreferenceHelper.setStringPreference(this, "client_id", result.client_id)
                    SharedPreferenceHelper.setStringPreference(this, "client_secret", result.client_secret)

                    DLog("qqqqqqqqqqqqqqqq", "call inside if condition in isFirstinstall")
                    session?.createFirstInstall()
                    val i = Intent(applicationContext, UserRegistration::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(i)
                    //loginView.closeActivity()
                    finish()

                    //database.use{}

                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")

                } else {
                    //snackbar(this,)
                    snackbar((this as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
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

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            0 -> return AlertDialog.Builder(this)
                    .setIcon(R.mipmap.logo_mipmap)
                    .setTitle("Check Your Internet Connection")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                                //System.exit(0);
                                //finish();
                                //int pid = android.os.Process.myPid();
                                //android.os.Process.killProcess(pid);
                                //System.exit(0);
                                //Intent.FLAG_ACTIVITY_NEW_TASK;
                                finishAffinity()

                                //System.exit(0);
                                //Toast.makeText(getBaseContext(),"OK clicked!", Toast.LENGTH_SHORT).show();
                            }
                    )
                    .setNegativeButton("Retry",
                            DialogInterface.OnClickListener { dialog, which ->
                                if (Network_check.isNetworkAvailable(this)) {
                                    dismissDialog(0)
                                    sendAppRegister()
                                }else{
                                    showDialog(0)
                                }
                            }
                    ).create()
        }
        return null
    }

}
