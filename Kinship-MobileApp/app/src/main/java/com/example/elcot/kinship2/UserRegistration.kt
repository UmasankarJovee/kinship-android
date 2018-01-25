package com.example.elcot.kinship2

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_registration.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserRegistration : AppCompatActivity() {

    var buttonConfirmOTP : AppCompatButton? = null
    var buttonConfirmPassword : AppCompatButton? = null

    var editTextotp : EditText? = null
    var editTextpassword : EditText? = null
    var editTextConfirmPassword : EditText? = null

    var blood_group : String? = null
    var otp : Long? = null
    var user_id : Int? = null

    var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable: Disposable? = null

    var progressDialog: ProgressDialog? = null
    var alertDialog1 : AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        var categories=ArrayList<String>()
        /*categories.add("Select Blood Group")
        categories.add("Blood Group (AB+)")
        categories.add("Blood Group (AB-)")
        categories.add("Blood Group (A+)")
        categories.add("Blood Group (A-)")
        categories.add("Blood Group (B+)")
        categories.add("Blood Group (B-")
        categories.add("Blood Group (O+)")
        categories.add("Blood Group (O-)")*/

        categories.add("AB+")
        categories.add("AB-")
        categories.add("A+")
        categories.add("A-")
        categories.add("B+")
        categories.add("B-")
        categories.add("O+")
        categories.add("O-")

        val dataAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_blood_group.adapter=dataAdapter

        spinner_blood_group.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                blood_group=categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        mApiInterface=RetrofitClient.getClient()

        button_register.setOnClickListener{
            //val i= Intent(applicationContext,UserProfile::class.java)
            //startActivity(i)
            //confirmotp()
            userRegister()
        }









    }

    private fun userRegister() {
        progressDialog = ProgressDialog(this@UserRegistration, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Registering...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.UserRegister(editText_phone_number.text.toString(), this!!.blood_group!!)!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            //progressBar.visibility=View.GONE
                            //Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                            Toast.makeText(this,result.message,Toast.LENGTH_LONG).show()
                            Toast.makeText(applicationContext,"$result.otp",Toast.LENGTH_LONG).show()
                            otp = result.otp
                            user_id = result.user_id
                            confirmotp()
                            //displayLog("response")
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


    private fun confirmotp() {

        val li=LayoutInflater.from(this)
        val confirmDialog = li.inflate(R.layout.alert_otp_get,null)
        buttonConfirmOTP = confirmDialog.findViewById<AppCompatButton>(R.id.buttonConfirmOTP) as AppCompatButton
        editTextotp = confirmDialog.findViewById<EditText>(R.id.editTextOtp) as EditText

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        val alertDialog = alert.create()
        alertDialog.show()

        //buttonConfirmOTP?.text="$otp"
        buttonConfirmOTP!!.setOnClickListener{
            if(editTextotp?.text.toString().toLong() == otp)
            {
                Toast.makeText(applicationContext,"Given OTP is Correct",Toast.LENGTH_LONG).show()
                alertDialog.dismiss()
                confirmPassword()
            }
            else
            {
                Toast.makeText(applicationContext,"in correct", Toast.LENGTH_LONG).show()
            }
            //val i= Intent(applicationContext,UserProfile::class.java)
            //startActivity(i)
        }
    }

    private fun confirmPassword() {

        val li1=LayoutInflater.from(this)
        val confimDialog1 = li1.inflate(R.layout.alert_password_get,null)
        buttonConfirmPassword = confimDialog1.findViewById<AppCompatButton>(R.id.buttonConfirmPassword) as AppCompatButton
        editTextpassword = confimDialog1.findViewById<EditText>(R.id.editText_password) as EditText
        editTextConfirmPassword = confimDialog1.findViewById<EditText>(R.id.editText_confirm_password) as EditText

        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        alertDialog1 = alert1.create()
        alertDialog1?.show()

        buttonConfirmPassword!!.setOnClickListener{
            if(editTextpassword?.text.toString() == editTextConfirmPassword?.text.toString())
            {
                sendPassword()
            }
        }

    }

    private fun sendPassword() {
        progressDialog = ProgressDialog(this@UserRegistration, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Setting Password...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.sendPassword(this!!.user_id!!,editTextpassword?.text.toString())!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            if(result.status_password == true)
                            {
                                alertDialog1?.dismiss()
                                val i=Intent(applicationContext,UserProfile::class.java)
                                startActivity(i)
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


}
