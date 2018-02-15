package com.joveeinfotech.kinship.view

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
import com.joveeinfotech.kinship.*
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.LoginResult
import com.joveeinfotech.kinship.presenter.LoginPresenter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

class Login : AppCompatActivity(), LoginView {

    var loginPresenter : LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter= LoginPresenter(this,this)
        Log.e("qqqqqqqqqqqqqqqq","call inside login")

        loginPresenter?.navigateActivity()
        btnlogin.setOnClickListener{
            loginPresenter?.userPhoneNumberAndPassword(editText_login_phone_number.text.toString(),editText_login_password.text.toString())
        }

        register.setOnClickListener{
           loginPresenter?.callRegisterActivity()
        }
    }

    override fun closeActivity() {  finish()  }

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

}
