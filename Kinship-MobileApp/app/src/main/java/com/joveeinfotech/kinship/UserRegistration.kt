package com.joveeinfotech.kinship

import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
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
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_registration.*
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

class UserRegistration : AppCompatActivity(), APIListener {

    var buttonConfirmOTP : AppCompatButton? = null
    var buttonConfirmPassword : AppCompatButton? = null

    var editTextotp : EditText? = null
    var editTextpassword : EditText? = null
    var editTextConfirmPassword : EditText? = null

    var blood_group : String? = null
    var otp : Long? = null
    var user_id : Int? = null

    //var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable: Disposable? = null

    var progressDialog: ProgressDialog? = null
    var alertDialog1 : AlertDialog? = null
    var session: SharedData? = null
    //var mValidation : Validation? =null

    var networkCall : APICall? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        networkCall = APICall(this)

        var categories =ArrayList<String>()
        /*categories.add("Select Blood Group")
        categories.add("Blood Group (AB+)")
        categories.add("Blood Group (AB-)")
        categories.add("Blood Group (A+)")
        categories.add("Blood Group (A-)")
        categories.add("Blood Group (B+)")
        categories.add("Blood Group (B-")
        categories.add("Blood Group (O+)")
        categories.add("Blood Group (O-)")*/

        categories.add("Select Blood Group")
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

        //mApiInterface=RetrofitClient.getClient()

        button_send_address.setOnClickListener{

            if(Validation.isValidPhoneNumber(editText_phone_number.text.toString()) && blood_group != "Select Blood Group")
            {
                //val i=Intent(applicationContext,UserProfile::class.java)
                //startActivity(i)

                userRegister()
                //confirmotp()
            }
            else
            {
                showDialog(0) // Please fill the correct phone number and blood group
            }
        }
    }

    private fun userRegister() {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number", editText_phone_number.getText().toString())
        queryParams.put("password", blood_group!!)
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/register",queryParams, RegisterResult::class.java,this, 1, "Registering...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Register
                val registerResult = response as RegisterResult
                Log.e("API CALL : ","inside Main activity and onSuccess")
                if (registerResult.status) {
                    confirmotp()
                    Log.e("API CALL : ","inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(this,)
                    snackbar(this.findViewById(android.R.id.content), "Please wait some minutes")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            2 -> { // Send OTP
                val registerResult = response as OTPResult
                Log.e("API CALL : ","inside Main activity and onSuccess")
                if (registerResult.status) {
                    confirmPassword()
                    Log.e("API CALL : ","inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(this,)
                }

            }
            3 -> { // Send Password
                val registerResult = response as PasswordResult
                Log.e("API CALL : ","inside Main activity and onSuccess")
                if (registerResult.status) {
                    val i=Intent(applicationContext,Login::class.java)
                    //startAlert()
                    startActivity(i)
                    Log.e("API CALL : ","inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(this,)
                }

            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {

    }

    override fun onNetworkFailure(from: Int) {

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

    private fun confirmotp() {

        val li=LayoutInflater.from(this)
        val confirmDialog = li.inflate(R.layout.alert_otp_get,null)
        buttonConfirmOTP = confirmDialog.findViewById<AppCompatButton>(R.id.buttonConfirmOTP) as AppCompatButton
        editTextotp = confirmDialog.findViewById<EditText>(R.id.editTextOtp) as EditText

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        val alertDialog = alert.create()
        alertDialog.show()
        alertDialog.setCancelable(false)

        buttonConfirmOTP!!.setOnClickListener{

            //sendOTP()
            val queryParams = HashMap<String, String>()
            queryParams.put("otp", editTextotp?.getText().toString())
            networkCall?.APIRequest("api/v2/otp",queryParams, OTPResult::class.java,this, 2, "Verifying Your OTP")
        }
    }

   /* private fun sendOTP() {
        progressDialog = ProgressDialog(this@UserRegistration, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Sending Your OTP...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.sendOTP(editTextotp?.text.toString())!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            if(result.status == true)
                            {
                                alertDialog1?.dismiss()
                                //Toast.makeText(applicationContext,result.message,Toast.LENGTH_LONG).show()
                                confirmPassword()
                                //val i=Intent(applicationContext,UserProfile::class.java)
                                //startActivity(i)
                            }
                            else
                            {
                                Toast.makeText(applicationContext,"Retry",Toast.LENGTH_LONG).show()
                            }
                        },
                        { error ->
                            progressDialog?.dismiss()
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                        }
                )
    }*/

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
        alertDialog1?.setCancelable(false)

        buttonConfirmPassword!!.setOnClickListener{
            if(editTextpassword?.text.toString() == editTextConfirmPassword?.text.toString())
            {
                //sendPassword
                val queryParams = HashMap<String, String>()
                queryParams.put("password", editTextpassword?.getText().toString())
                queryParams.put("phone_number", editText_phone_number.getText().toString())
                networkCall?.APIRequest("api/v3/password",queryParams, PasswordResult::class.java,this, 3, "Setting your Password...")
            }
        }
    }

   /* private fun sendPassword() {
        progressDialog = ProgressDialog(this@UserRegistration, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Setting Password...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.sendPassword(editTextpassword?.text.toString(),editText_phone_number.text.toString())!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            if(result.status == true)
                            {
                                alertDialog1?.dismiss()
                                Toast.makeText(applicationContext,result.message,Toast.LENGTH_LONG).show()
                                val i=Intent(applicationContext,UserProfile::class.java)
                                //startAlert()
                                startActivity(i)
                            }
                        },
                        { error ->
                            progressDialog?.dismiss()
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                        }
                )
    }*/

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            0 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Please fill the correct Phone number and Blood Group")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                            }
                    ).create()
            1 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Given Phone number is not correct format")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                            }
                    ).create()
            2 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Please fill two fields")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                            }
                    ).create()
        }
        return null
    }




}

