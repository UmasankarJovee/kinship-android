package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.widget.EditText
import com.joveeinfotech.kinship.*
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.OTPResult
import com.joveeinfotech.kinship.model.PasswordResult
import com.joveeinfotech.kinship.model.RegisterResult
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.SharedData
import com.joveeinfotech.kinship.utils.Validation
import com.joveeinfotech.kinship.view.Login
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class RegisterPresenterImpl : APIListener, RegisterPresenter {

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private var registerView : RegisterView
    var session: SharedData? = null

    var mContext: Context
    var networkCall : APICall? = null

    var phone_number : String? = null
    var blood_group : String? = null

    constructor(view: RegisterView, context: Context){
        registerView=view
        mContext=context
        initPresenter()
        session = SharedData(mContext)
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun OtpContent(otp: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("otp", otp)
        queryParams.put("client_id","56")
        networkCall?.APIRequest("api/v2/otp", queryParams, OTPResult::class.java, this, 2, "Verifying Your OTP")
    }

    override fun passwordContent(password: String, phone_number: String) {

        var deviceId = Settings.Secure.getString(mContext.contentResolver,
                Settings.Secure.ANDROID_ID)
        var deviceModel= android.os.Build.MODEL
        var deviceName= android.os.Build.MANUFACTURER
        var deviceVersion= android.os.Build.VERSION.SDK_INT

        val queryParams = HashMap<String, String>()
        queryParams.put("password", password)
        queryParams.put("phone_number", phone_number)

        queryParams.put("app_id","com.joveeinfotech.kinship")
        queryParams.put("android_id",deviceId)
        queryParams.put("device_name",deviceName)
        queryParams.put("device_version",deviceVersion.toString())
        queryParams.put("device_model",deviceModel)
        queryParams.put("client_id","56")
        networkCall?.APIRequest("api/v3/password", queryParams, PasswordResult::class.java, this, 3, "Setting your Password...")
    }
    override fun userPhoneNumberAndBloodGroup(phone_number: String, blood_group: String) {
        this.phone_number = phone_number
        this.blood_group = blood_group
        if(phone_number.trim().isNotEmpty() && blood_group != "Select Blood Group"){
            if (Validation.isValidPhoneNumber(phone_number) && blood_group != "Select Blood Group") {
                userRegister()
            } else {
                CustomToast().alertToast(mContext,"Phone number is not Correct")
            }
        }
        else {
            var sp : SharedPreferences = mContext.getSharedPreferences("FCM_PREF", Context.MODE_PRIVATE)
            var token = sp.getString("FCM_TOKEN","")
            Log.e("dg","$token")
            Log.e("dgdh","fcfc $token")
            CustomToast().alertToast(mContext,"Please fill two fields")
        }
    }

    private fun userRegister() {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number", phone_number!!)
        queryParams.put("blood_group", blood_group!!)
        queryParams.put("client_id","56")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/register", queryParams, RegisterResult::class.java, this, 1, "Registering...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User Register
                val registerResult = response as RegisterResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (registerResult.status) {
                    registerView.confirmOTP()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(.findViewById(android.R.id.content), "Please wait some minutes")
                }
            }
            2 -> { // Send OTP
                val registerResult = response as OTPResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (registerResult.status) {
                    registerView.confirmPassword()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    CustomToast().alertToast(mContext,"Click Resend")
                    //snackbar(this,)
                }
            }
            3 -> { // Send Password
                val registerResult = response as PasswordResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (registerResult.status) {
                    registerView.closePasswordDialog()
                    val i = Intent(mContext, Login::class.java)
                    mContext.startActivity(i)
                    registerView.closeActivity()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    CustomToast().alertToast(mContext,"Try again")
                    //snackbar(this,)
                }
            }
        }
    }
}