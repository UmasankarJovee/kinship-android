package com.joveeinfotech.kinship

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_registration.*
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

class Login : AppCompatActivity(), APIListener {

    //var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable: Disposable? = null

    var progressDialog: ProgressDialog? = null
    var session: SharedData? = null

    var networkCall : APICall? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        networkCall = APICall(this)
        Log.e("qqqqqqqqqqqqqqqq","call inside login")
        session = SharedData(this)

        if (session?.isFirstInstall()!!) {
            Log.e("qqqqqqqqqqqqqqqq","call inside if condition in isFirstinstall")
            session?.createFirstInstall()
            val i = Intent(this@Login,UserRegistration::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            this@Login.finish()
        }

        if (session?.isLoggedIn()!!) {
            Log.e("qqqqqqqqqqqqqqqq","call inside if condition islogin")
            val i = Intent(this@Login,Home::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            this@Login.finish()
        }

        //mApiInterface=RetrofitClient.getClient()
        Log.e("qqqqqqqqqqqqqqqq","call inside again login")
        btnlogin.setOnClickListener{
            if(!editText_login_phone_number.text.toString().isEmpty() && !editText_login_password.text.toString().isEmpty())
            {
                if(Validation.isValidPhoneNumber(editText_login_phone_number.text.toString())) {
                    userLogin()
                    /*session?.createLoginSession(editText_login_phone_number.text.toString(), editText_login_password.text.toString())
                    val i= Intent(applicationContext,Home::class.java)
                    startActivity(i)
                    finish()*/
                }
                else {
                    showDialog(1) // Invalid Phone number
                }
                //val i= Intent(applicationContext,Home::class.java)
                //startActivity(i)
                //finish()
            }
            else {
                showDialog(2)
            }
        }

        register.setOnClickListener{
            val i=Intent(applicationContext,UserRegistration::class.java)
            startActivity(i)
        }
    }

    private fun userLogin() {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number", editText_login_phone_number.text.toString())
        queryParams.put("password", editText_login_password.text.toString())
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v4/login",queryParams, LoginResult::class.java,this, 1, "Authenticating...")
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
                                val i= Intent(applicationContext,Home::class.java)
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

    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            0 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Given Phone number and Password is Incorrect")
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

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val loginResult = response as LoginResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (loginResult.status) {
                    session?.createLoginSession(editText_login_phone_number.text.toString(), editText_login_password.text.toString())
                    Toast.makeText(applicationContext,loginResult.message,Toast.LENGTH_LONG).show()
                    val i= Intent(applicationContext,Home::class.java)
                    startActivity(i)
                    finish()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(this,)
                    snackbar(this.findViewById(android.R.id.content), "Please wait some minutes")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {

    }
    override fun onNetworkFailure(from: Int) {

    }

}
