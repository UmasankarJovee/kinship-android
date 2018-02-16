package com.joveeinfotech.kinship.presenter

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import com.joveeinfotech.kinship.*
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.LoginResult
import com.joveeinfotech.kinship.model.OTPResult
import com.joveeinfotech.kinship.model.PasswordResult
import com.joveeinfotech.kinship.model.RegisterResult
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.view.Login
import kotlinx.android.synthetic.main.activity_user_registration.*
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class RegisterPresentermpl : APIListener, RegisterPresenter{

    var buttonConfirmOTP: AppCompatButton? = null
    var buttonConfirmPassword: AppCompatButton? = null

    var editTextotp: EditText? = null
    var editTextpassword: EditText? = null
    var editTextConfirmPassword: EditText? = null
    var alertDialog1: AlertDialog? = null

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private lateinit var registerView : RegisterView
    var session: SharedData? = null

    lateinit var mContext: Context
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
    override fun confirmOTP() {
        val li = LayoutInflater.from(mContext)
        val confirmDialog = li.inflate(com.joveeinfotech.kinship.R.layout.alert_otp_get, null)
        buttonConfirmOTP = confirmDialog.findViewById<AppCompatButton>(com.joveeinfotech.kinship.R.id.buttonConfirmOTP) as AppCompatButton
        editTextotp = confirmDialog.findViewById<EditText>(com.joveeinfotech.kinship.R.id.editTextOtp) as EditText

        val alert = AlertDialog.Builder(mContext)
        alert.setView(confirmDialog)

        val alertDialog = alert.create()
        alertDialog.show()
        alertDialog.setCancelable(false)

        buttonConfirmOTP!!.setOnClickListener {

            //sendOTP()
            val queryParams = HashMap<String, String>()
            queryParams.put("otp", editTextotp?.getText().toString())
            networkCall?.APIRequest("api/v2/otp", queryParams, OTPResult::class.java, this, 2, "Verifying Your OTP")
        }
    }

    override fun confirmPassword() {
        val li1 = LayoutInflater.from(mContext)
        val confimDialog1 = li1.inflate(com.joveeinfotech.kinship.R.layout.alert_password_get, null)
        buttonConfirmPassword = confimDialog1.findViewById<AppCompatButton>(com.joveeinfotech.kinship.R.id.buttonConfirmPassword) as AppCompatButton
        editTextpassword = confimDialog1.findViewById<EditText>(com.joveeinfotech.kinship.R.id.editText_password) as EditText
        editTextConfirmPassword = confimDialog1.findViewById<EditText>(com.joveeinfotech.kinship.R.id.editText_confirm_password) as EditText

        val alert1 = AlertDialog.Builder(mContext)
        alert1.setView(confimDialog1)

        alertDialog1 = alert1.create()
        alertDialog1?.show()
        alertDialog1?.setCancelable(false)

        buttonConfirmPassword!!.setOnClickListener {
            if (editTextpassword?.text.toString() == editTextConfirmPassword?.text.toString()) {
                //sendPassword
                val queryParams = HashMap<String, String>()
                queryParams.put("password", editTextpassword?.getText().toString())
                queryParams.put("phone_number", phone_number!!)
                networkCall?.APIRequest("api/v3/password", queryParams, PasswordResult::class.java, this, 3, "Setting your Password...")
            }
        }
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
            CustomToast().alertToast(mContext,"Please fill two fields")
        }
    }

    private fun userRegister() {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number", phone_number!!)
        queryParams.put("blood_group", blood_group!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/register", queryParams, RegisterResult::class.java, this, 1, "Registering...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User Register
                val registerResult = response as RegisterResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (registerResult.status) {
                    confirmOTP()
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
                    confirmPassword()
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
                    val i = Intent(mContext, Login::class.java)
                    mContext.startActivity(i)
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    CustomToast().alertToast(mContext,"Try again")
                    //snackbar(this,)
                }
            }
        }
    }
}