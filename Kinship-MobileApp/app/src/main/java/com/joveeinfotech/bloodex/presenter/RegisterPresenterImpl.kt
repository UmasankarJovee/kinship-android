package com.joveeinfotech.bloodex.presenter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.joveeinfotech.bloodex.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.model.CountryResult
import com.joveeinfotech.bloodex.model.OTPResult
import com.joveeinfotech.bloodex.model.PasswordResult
import com.joveeinfotech.bloodex.model.RegisterResult
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.utils.SharedData
import com.joveeinfotech.bloodex.utils.Validation
import com.joveeinfotech.bloodex.view.Login
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
        loadCountries()
    }

    private fun loadCountries() {
        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext,"client_id","")
        queryParams.put("client_id",client_id!!)
        networkCall?.APIRequest("api/v1/country", queryParams, CountryResult::class.java, this, 4, "Verifying Your OTP")
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun OtpContent(otp: String) {

        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext,"client_id","")
        queryParams.put("client_id",client_id!!)
        queryParams.put("otp", otp)
        networkCall?.APIRequest("api/v2/otp", queryParams, OTPResult::class.java, this, 2, "Verifying Your OTP")
    }

    override fun passwordContent(password: String, phone_number: String) {

        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext,"client_id","")
        queryParams.put("client_id",client_id!!)
        queryParams.put("password", password)
        queryParams.put("phone_number", phone_number)
        networkCall?.APIRequest("api/v3/password", queryParams, PasswordResult::class.java, this, 3, "Setting your Password...")
    }
    override fun userPhoneNumberAndBloodGroup(phone_number: String, blood_group: String) {
        this.phone_number = phone_number
        this.blood_group = blood_group
        if(phone_number.trim().isNotEmpty() && blood_group != "Select Blood Group"){
            if (Validation.isValidPhoneNumber(phone_number) && blood_group != "Select Blood Group") {
                userRegister()
            } else {
                CustomToast().alertToast(mContext,mContext.getString(R.string.phone_number_is_not_correct))
            }
        }
        else {
            var sp : SharedPreferences = mContext.getSharedPreferences("FCM_PREF", Context.MODE_PRIVATE)
            var token = sp.getString("FCM_TOKEN","")
            DLog("dg", "$token")
            DLog("dgdh","fcfc $token")
            CustomToast().alertToast(mContext,mContext.getString(R.string.please_fill_two_fields))
        }
    }

    private fun userRegister() {
        val queryParams = HashMap<String, String>()
        var client_id = getStringPreference(mContext,"client_id","")
        queryParams.put("client_id",client_id!!)
        queryParams.put("phone_number", phone_number!!)
        queryParams.put("blood_group", blood_group!!)
        queryParams.put("country","India")
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/register", queryParams, RegisterResult::class.java, this, 1, "Registering...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User Register
                val registerResult = response as RegisterResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (registerResult.status) {
                    registerView.confirmOTP()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(.findViewById(android.R.id.content), "Please wait some minutes")
                }
            }
            2 -> { // Send OTP
                val registerResult = response as OTPResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (registerResult.status) {
                    registerView.confirmPassword()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    CustomToast().alertToast(mContext,mContext.getString(R.string.click_again_submit))
                    //snackbar(this,)
                }
            }
            3 -> { // Send Password
                val registerResult = response as PasswordResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (registerResult.status) {
                    registerView.closePasswordDialog()
                    val i = Intent(mContext, Login::class.java)
                    mContext.startActivity(i)
                    registerView.closeActivity()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    CustomToast().alertToast(mContext,mContext.getString(R.string.try_again))
                    //snackbar(this,)
                }
            }
            4 -> { // Send Password
                val registerResult = response as CountryResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                registerView.setCountries(registerResult.country)
                //registerResult.country
                /*if (registerResult.status) {
                    registerView.closePasswordDialog()
                    val i = Intent(mContext, Login::class.java)
                    mContext.startActivity(i)
                    registerView.closeActivity()
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    CustomToast().alertToast(mContext,mContext.getString(R.string.try_again))
                    //snackbar(this,)
                }*/
            }
        }
    }
}