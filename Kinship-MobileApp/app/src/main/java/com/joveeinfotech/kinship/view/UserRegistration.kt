package com.joveeinfotech.kinship.view

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import com.joveeinfotech.kinship.*
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.RegisterPresenterImpl
import com.joveeinfotech.kinship.utils.CustomToast
import kotlinx.android.synthetic.main.activity_user_registration.*
import kotlinx.android.synthetic.main.alert_otp_get.*


class UserRegistration : AppCompatActivity(), RegisterView {

    var blood_group: String? = null

    var registerPresenter : RegisterPresenterImpl? = null
    var buttonConfirmOTP: AppCompatButton? = null
    var buttonConfirmPassword: AppCompatButton? = null

    var editTextotp: EditText? = null
    var editTextpassword: EditText? = null
    var editTextConfirmPassword: EditText? = null
    var alertDialog1: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
        registerPresenter = RegisterPresenterImpl(this, this)



        var categories = ArrayList<String>()
        categories.add("Select Blood Group")
        categories.add("AB+")
        categories.add("AB-")
        categories.add("A+")
        categories.add("A-")
        categories.add("B+")
        categories.add("B-")
        categories.add("O+")
        categories.add("O-")

        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activity_user_registration_scrollView_constraintLayout_blood_group_spinner.adapter = dataAdapter

        activity_user_registration_scrollView_constraintLayout_blood_group_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        activity_user_registration_scrollView_constraintLayout_registerButton.setOnClickListener {
            var sp : SharedPreferences = applicationContext.getSharedPreferences("FCM_PREF", Context.MODE_PRIVATE)
            var token = sp.getString("FCM_TOKEN","")
            Log.e("dg","$token")
            registerPresenter?.userPhoneNumberAndBloodGroup(activity_user_registration_scrollView_constraintLayout_phone_number_editText.text.toString(), blood_group!!)
        }
    }

    override fun confirmOTP() {
        val li = LayoutInflater.from(this)
        val confirmDialog = li.inflate(com.joveeinfotech.kinship.R.layout.alert_otp_get, null)
        buttonConfirmOTP = confirmDialog.findViewById<AppCompatButton>(com.joveeinfotech.kinship.R.id.alert_otp_get_confirmOTPButton) as AppCompatButton
        editTextotp = confirmDialog.findViewById<EditText>(com.joveeinfotech.kinship.R.id.alert_otp_get_otp_EditText) as EditText

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        val alertDialog = alert.create()
        alertDialog.show()
        alertDialog.setCancelable(false)

        buttonConfirmOTP!!.setOnClickListener {
            if(editTextotp!!.text.isNotEmpty()){
                registerPresenter?.OtpContent(editTextotp?.text.toString())
            }else{
                CustomToast().alertToast(this,"Fill the OTP")
            }
        }
    }

    override fun confirmPassword() {
        val li1 = LayoutInflater.from(this)
        val confimDialog1 = li1.inflate(com.joveeinfotech.kinship.R.layout.alert_password_get, null)
        buttonConfirmPassword = confimDialog1.findViewById<AppCompatButton>(com.joveeinfotech.kinship.R.id.alert_password_get_confirmPasswordButton) as AppCompatButton
        editTextpassword = confimDialog1.findViewById<EditText>(com.joveeinfotech.kinship.R.id.alert_password_get_password_editText) as EditText
        editTextConfirmPassword = confimDialog1.findViewById<EditText>(com.joveeinfotech.kinship.R.id.alert_password_get_confirm_password_editText) as EditText

        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        alertDialog1 = alert1.create()
        alertDialog1?.show()
        alertDialog1?.setCancelable(false)

        buttonConfirmPassword!!.setOnClickListener {
            if(editTextpassword!!.text.isNotEmpty() && editTextConfirmPassword!!.text.isNotEmpty()){
                if (editTextpassword?.text.toString() == editTextConfirmPassword?.text.toString()) {
                    registerPresenter?.passwordContent(editTextpassword?.text.toString(),activity_user_registration_scrollView_constraintLayout_phone_number_editText.text.toString())
                }else{
                    CustomToast().alertToast(this,"Both passwords must be equal")
                }
            }else{
                CustomToast().alertToast(this,"Fill the all fields")
            }
        }
    }

    /*private fun userRegister1() {
        progressDialog = ProgressDialog(this@UserRegistration, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Registering...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.UserRegister(editText_phone_number.text.toString(), this!!.blood_group!!)!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            Toast.makeText(applicationContext,"${result.otp}",Toast.LENGTH_LONG).show()
                            //otp = result.otp
                            //user_id = result.user_id
                            if(result.status == true)
                            {
                                confirmotp()
                            }
                        },
                        { error ->
                            progressDialog?.dismiss()
                            //progressBar.visibility=View.GONE
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                            //displayLog("error")
                        }
                )
    }
*/

    override fun closeActivity() { finish() }
}

