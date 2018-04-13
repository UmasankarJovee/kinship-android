package com.joveeinfotech.bloodex.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.joveeinfotech.bloodex.*
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.bloodex.model.*
import com.joveeinfotech.bloodex.utils.*
import com.joveeinfotech.bloodex.utils.Others.DLog
import org.jetbrains.anko.design.snackbar
import java.util.HashMap
import com.joveeinfotech.bloodex.view.RequestResponse
import com.joveeinfotech.bloodex.view.UserDetails
import com.joveeinfotech.bloodex.view.UserRegistration


/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class LoginPresenterImpl : APIListener, LoginPresenter {

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private var loginView : LoginView
    var session: SharedData? = null

    var mContext: Context
    var networkCall : APICall? = null

    var phone_number : String? = null
    var password : String? = null

    var user_id : String? = null

    constructor(view: LoginView,context: Context){
        loginView=view
        mContext=context
        initPresenter()
        session = SharedData(mContext)
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun navigateActivity() {
        if (session?.isFirstInstall()!!) {
            //loginView.setContentNothing()
            /*if (Network_check.isNetworkAvailable(mContext)) {

                //sendAppRegister()

            }else{
                // Display Not Network Connected in activity
                loginView.showNetworkError()
            }*/


            session?.createFirstInstall()
            val i = Intent(mContext, AboutUS::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
        var isClickDonated = getBooleanPreference(mContext, "isClickDonated", false)
        if(isClickDonated){
            DLog("qqqqqqqqqqqqqqqq", "call inside if condition islogin")
            val i = Intent(mContext, RequestResponse::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
        if (session?.isLoggedIn()!!) {
            DLog("qqqqqqqqqqqqqqqq","call inside if condition islogin")
            val i = Intent(mContext, Home::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
    }

    private fun sendAppRegister() {
        var deviceId = Settings.Secure.getString(mContext.contentResolver,
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
        networkCall?.APIRequest("api/v1/app", queryParams, PasswordResult::class.java, this, 5, "Setting your Password...",false)
    }

    override fun callRegisterActivity() {
        val i=Intent(mContext, UserRegistration::class.java)
        mContext.startActivity(i)
    }
    override fun userPhoneNumberAndPassword(phone_number: String, password: String) {
          this.phone_number=phone_number
          this.password=password
          if(phone_number.trim().isNotEmpty() && password.trim().isNotEmpty())
          {
               if(Validation.isValidPhoneNumber(phone_number)){
                   userLogin(phone_number,password)
               }
               else {
                   CustomToast().alertToast(mContext,"Phone number is not Correct")
               }
          }
          else {
              CustomToast().alertToast(mContext,"Please fill two fields")
          }
    }

    private fun userLogin(phone_number: String, password: String) {
        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext, "client_id", "")
        var client_secret = getStringPreference(mContext,"client_secret","")
        queryParams.put("client_id",client_id!!)
        queryParams.put("client_secret",client_secret!!)
        queryParams.put("username",phone_number)
        queryParams.put("password",password)
        queryParams.put("grant_type","password")
        queryParams.put("scope","*")
        DLog("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/login",queryParams, LoginResult::class.java,this, 1, "Authenticating...")
    }

    override fun getPhoneNumber(phone_number: String) {
        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext, "client_id", "")
        queryParams.put("client_id",client_id!!)
        queryParams.put("phone_number",phone_number)
        DLog("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/forgotpassword",queryParams, ForgotPasswordSendOTPToPhoneNumber::class.java,this, 2, "Authenticating...")
    }

    override fun sendOTP(otp: String) {
        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext, "client_id", "")
        queryParams.put("client_id",client_id!!)
        queryParams.put("otp",otp)
        //queryParams.put("user_id",user_id!!)
        DLog("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/otp",queryParams, ForgotPasswordVerifyOTP::class.java,this, 3, "Authenticating...")
    }

    override fun sendPassword(password : String) {
        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext, "client_id", "")
        queryParams.put("client_id",client_id!!)
        queryParams.put("password",password)
        //queryParams.put("user_id",user_id!!)
        DLog("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/password",queryParams, ForgotPasswordSendPasswordResult::class.java,this, 4, "Authenticating...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val loginResult = response as LoginResult
                DLog("dgdgdsg : ","${loginResult.status}")
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (true) {

                    loginView.setContentview()

                    setStringPreference(mContext,"access_token",loginResult.access_token)
                    setStringPreference(mContext,"refresh_token",loginResult.refresh_token)

                    setStringPreference(mContext,"user_id","168")
                    setBooleanPreference(mContext,"userOption${loginResult.user_id}",false)
                    DLog("loginPresenter : ", "loginPresenter1")
                    session?.createLoginSession(phone_number!!, password!!)
                    DLog("loginPresenter : ", "loginPresenter2")
                    val i= Intent(mContext, UserDetails::class.java)
                    DLog("loginPresenter : ", "loginPresenter3")
                    mContext.startActivity(i)
                    DLog("loginPresenter : ", "loginPresenter4")
                    loginView.closeActivity()
                    //loginView.closeActivity()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            2 -> {
                val forgotPasswordResult = response as ForgotPasswordSendOTPToPhoneNumber
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (forgotPasswordResult.status) {
                    user_id = forgotPasswordResult.user_id
                    loginView.getOTP()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            3 -> {
                val forgotPasswordResult = response as ForgotPasswordVerifyOTP
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (forgotPasswordResult.status) {
                    loginView.resetPassword()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            4 -> {
                val forgotPasswordSendPasswordResult = response as ForgotPasswordSendPasswordResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (forgotPasswordSendPasswordResult.status) {
                    loginView.closePasswordDialog()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
           /* 5 -> {
                val result = response as AppRegisterResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {

                    setStringPreference(mContext,"client_id",result.client_id)
                    setStringPreference(mContext,"client_secret",result.client_secret)

                    Log.e("qqqqqqqqqqqqqqqq", "call inside if condition in isFirstinstall")
                    session?.createFirstInstall()
                    val i = Intent(mContext, UserRegistration::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mContext.startActivity(i)
                    loginView.closeActivity()

                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")

                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }*/
        }
    }

    /*private fun setAlarm() {
        val intent = Intent(mContext, LocationService::class.java)
        val pendingIntent = PendingIntent.getService(mC, 234324243, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, 1000 ,pendingIntent)
        //Toast.makeText(this, "Alarm after 5 seconds", Toast.LENGTH_SHORT).show()
    }*/
}