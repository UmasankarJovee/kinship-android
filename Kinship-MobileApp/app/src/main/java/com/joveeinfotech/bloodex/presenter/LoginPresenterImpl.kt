package com.joveeinfotech.bloodex.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.joveeinfotech.bloodex.*
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.bloodex.model.ForgotPasswordSendOTPToPhoneNumber
import com.joveeinfotech.bloodex.model.ForgotPasswordSendPasswordResult
import com.joveeinfotech.bloodex.model.ForgotPasswordVerifyOTP
import com.joveeinfotech.bloodex.model.LoginResult
import org.jetbrains.anko.design.snackbar
import java.util.HashMap
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.SharedData
import com.joveeinfotech.bloodex.utils.Validation
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
            Log.e("qqqqqqqqqqqqqqqq","call inside if condition in isFirstinstall")
            session?.createFirstInstall()
            val i = Intent(mContext, AboutUS::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
        var isClickDonated = getBooleanPreference(mContext, "isClickDonated", false)
        if(isClickDonated){
            Log.e("qqqqqqqqqqqqqqqq","call inside if condition islogin")
            val i = Intent(mContext, RequestResponse::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
        if (session?.isLoggedIn()!!) {
            Log.e("qqqqqqqqqqqqqqqq","call inside if condition islogin")
            val i = Intent(mContext, Home::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
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
        queryParams.put("phone_number",phone_number)
        queryParams.put("password",password)
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/login",queryParams, LoginResult::class.java,this, 1, "Authenticating...")
    }

    override fun getPhoneNumber(phone_number: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number",phone_number)
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/forgotpassword",queryParams, ForgotPasswordSendOTPToPhoneNumber::class.java,this, 2, "Authenticating...")
    }

    override fun sendOTP(otp: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("otp",otp)
        queryParams.put("user_id",user_id!!)
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/otp",queryParams, ForgotPasswordVerifyOTP::class.java,this, 3, "Authenticating...")
    }

    override fun sendPassword(password : String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("password",password)
        queryParams.put("user_id",user_id!!)
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/password",queryParams, ForgotPasswordSendPasswordResult::class.java,this, 4, "Authenticating...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val loginResult = response as LoginResult
                Log.e("dgdgdsg : ","${loginResult.status}")
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (loginResult.status) {

                    setStringPreference(mContext,"user_id","168")
                    setBooleanPreference(mContext,"userOption${loginResult.user_id}",false)
                    session?.createLoginSession(phone_number!!, password!!)
                    CustomToast().normalToast(mContext,loginResult.message)
                    val i= Intent(mContext, UserDetails::class.java)
                    mContext.startActivity(i)
                    loginView.closeActivity()
                    //loginView.closeActivity()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            2 -> {
                val forgotPasswordResult = response as ForgotPasswordSendOTPToPhoneNumber
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (forgotPasswordResult.status) {
                    user_id = forgotPasswordResult.user_id
                    loginView.getOTP()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            3 -> {
                val forgotPasswordResult = response as ForgotPasswordVerifyOTP
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (forgotPasswordResult.status) {
                    loginView.resetPassword()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            4 -> {
                val forgotPasswordSendPasswordResult = response as ForgotPasswordSendPasswordResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (forgotPasswordSendPasswordResult.status) {
                    loginView.closePasswordDialog()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }
}