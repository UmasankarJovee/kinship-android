package com.joveeinfotech.bloodex.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.presenter.LoginPresenterImpl
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.LocationService
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity(), LoginView {

    var loginPresenter : LoginPresenterImpl? = null
    var buttonSubmitPhoneNumber : AppCompatButton? = null
    var editTextPhoneNumber : EditText? = null

    var buttonConfirmOTP: AppCompatButton? = null
    var editTextotp: EditText? = null

    var buttonConfirmPassword: AppCompatButton? = null
    var editTextpassword: EditText? = null
    var editTextConfirmPassword: EditText? = null

    var alertDialogGetPhoneNumber: AlertDialog? = null
    var alertDialogGetOTP: AlertDialog? = null
    var alertDialogGetPassword: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter= LoginPresenterImpl(this,this)
        Log.e("qqqqqqqqqqqqqqqq","call inside login")

        loginPresenter?.navigateActivity()

        activity_login_loginButton.setOnClickListener{
            //loginPresenter?.userPhoneNumberAndPassword(activity_login_phone_number_editText.text.toString(),activity_login_password_editText.text.toString())
            val i= Intent(applicationContext, UserDetails::class.java)
            startActivity(i)
        }

        activity_login_textView_register.setOnClickListener{
           loginPresenter?.callRegisterActivity()
        }

        activity_login_textview_forget_password.setOnClickListener{
            displayForgotPasswordGetPhoneNumber()
        }
    }

    private fun displayForgotPasswordGetPhoneNumber() {
        val li = LayoutInflater.from(this)
        val confirmDialog = li.inflate(com.joveeinfotech.bloodex.R.layout.alert_forgot_password, null)
        buttonSubmitPhoneNumber = confirmDialog.findViewById<AppCompatButton>(com.joveeinfotech.bloodex.R.id.alert_forgot_password_Button_submit) as AppCompatButton
        editTextPhoneNumber = confirmDialog.findViewById<EditText>(com.joveeinfotech.bloodex.R.id.alert_forgot_password_EditText_phone_number) as EditText

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        alertDialogGetPhoneNumber = alert.create()
        alertDialogGetPhoneNumber?.show()
        alertDialogGetPhoneNumber?.setCancelable(false)

        buttonSubmitPhoneNumber!!.setOnClickListener {
            if(editTextPhoneNumber!!.text.isNotEmpty()){
                loginPresenter?.getPhoneNumber(editTextPhoneNumber?.text.toString())
            }else{
                CustomToast().alertToast(this,"Fill the OTP")
            }
        }
    }

    override fun closeActivity() {  finish()  }

    override fun getOTP() {
        alertDialogGetPhoneNumber?.dismiss()
        val li = LayoutInflater.from(this)
        val confirmDialog = li.inflate(com.joveeinfotech.bloodex.R.layout.alert_otp_get, null)
        buttonConfirmOTP = confirmDialog.findViewById<AppCompatButton>(com.joveeinfotech.bloodex.R.id.alert_otp_get_confirmOTPButton) as AppCompatButton
        editTextotp = confirmDialog.findViewById<EditText>(com.joveeinfotech.bloodex.R.id.alert_otp_get_otp_EditText) as EditText

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        alertDialogGetOTP = alert.create()
        alertDialogGetOTP?.show()
        alertDialogGetOTP?.setCancelable(false)

        buttonConfirmOTP!!.setOnClickListener {
            if(editTextotp!!.text.isNotEmpty()){
                loginPresenter?.sendOTP(editTextotp?.text.toString())
            }else{
                CustomToast().alertToast(this,"Fill the OTP")
            }
        }
    }

    override fun resetPassword() {
        alertDialogGetOTP?.dismiss()
        val li1 = LayoutInflater.from(this)
        val confimDialog1 = li1.inflate(com.joveeinfotech.bloodex.R.layout.alert_password_get, null)
        buttonConfirmPassword = confimDialog1.findViewById<AppCompatButton>(com.joveeinfotech.bloodex.R.id.alert_password_get_confirmPasswordButton) as AppCompatButton
        editTextpassword = confimDialog1.findViewById<EditText>(com.joveeinfotech.bloodex.R.id.alert_password_get_password_editText) as EditText
        editTextConfirmPassword = confimDialog1.findViewById<EditText>(com.joveeinfotech.bloodex.R.id.alert_password_get_confirm_password_editText) as EditText

        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        alertDialogGetPassword = alert1.create()
        alertDialogGetPassword?.show()
        alertDialogGetPassword?.setCancelable(false)

        buttonConfirmPassword!!.setOnClickListener {
            if(editTextpassword!!.text.isNotEmpty() && editTextConfirmPassword!!.text.isNotEmpty()){
                if (editTextpassword?.text.toString() == editTextConfirmPassword?.text.toString()) {
                    loginPresenter?.sendPassword(editTextpassword?.text.toString())
                }else{
                    CustomToast().alertToast(this,"Both passwords must be equal")
                }
            }else{
                CustomToast().alertToast(this,"Fill the all fields")
            }
        }
    }

    override fun closePasswordDialog(){
        alertDialogGetPassword?.dismiss()
    }



    private fun setAlarm() {
        val intent = Intent(this, LocationService::class.java)
        val pendingIntent = PendingIntent.getService(this.applicationContext, 234324243, intent, 0)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10 * 1000, 1000 ,pendingIntent)
        //Toast.makeText(this, "Alarm after 5 seconds", Toast.LENGTH_SHORT).show()
    }

    /*private fun userLogin1() {
        progressDialog = ProgressDialog(this@Login, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Authenticating...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.userLogin(editText_login_phone_number.text.toString(), editText_login_password.text.toString())!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            if(result.status == true)
                            {
                                session?.createLoginSession(editText_login_phone_number.text.toString(), editText_login_password.text.toString())
                                Toast.makeText(applicationContext,result.message,Toast.LENGTH_LONG).show()
                                val i= Intent(applicationContext, Home::class.java)
                                startActivity(i)
                                finish()
                            }
                            else
                            {
                                showDialog(0) // Given phone number and password is incorrect
                            }
                        },
                        { error ->
                            progressDialog?.dismiss()
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                        }
                )
    }
*/

    override fun onBackPressed() {

        finishAffinity()
    }
}
