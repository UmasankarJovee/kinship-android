package com.example.elcot.kinship2

import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_registration.*

class Login : AppCompatActivity() {

    var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable: Disposable? = null

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mApiInterface=RetrofitClient.getClient()


        btnlogin.setOnClickListener{
            //val i= Intent(applicationContext,Home::class.java)
            //startActivity(i)
            if(editText_login_phone_number.text.toString() != null && editText_login_password.text.toString() != null)
            {
                userLogin()
            }
            else
            {
                showDialog(2)
            }
        }

        register.setOnClickListener{
            val i=Intent(applicationContext,UserRegistration::class.java)
            startActivity(i)
        }
        //shanmugaraj jovee infotech
    }

    private fun userLogin() {
        progressDialog = ProgressDialog(this@Login, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Authenticating...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.userLogin(editText_login_phone_number.text.toString(), editText_login_password.text.toString())!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            if(result.status_Login == true)
                            {
                                val i= Intent(applicationContext,Home::class.java)
                                startActivity(i)
                            }
                            else
                            {
                                //Toast.makeText(this,, Toast.LENGTH_LONG).show()
                                showDialog(0)
                            }
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


    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            0 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Given Phone number and Password is Incorrect")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                                //System.exit(0);
                                //finish();
                                //int pid = android.os.Process.myPid();
                                //android.os.Process.killProcess(pid);
                                //System.exit(0);
                                //Intent.FLAG_ACTIVITY_NEW_TASK;
                                //finishAffinity();

                                //System.exit(0);
                                //Toast.makeText(getBaseContext(),"OK clicked!", Toast.LENGTH_SHORT).show();
                            }
                    ).create()
            1 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Given Phone number is not correct format")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                                //System.exit(0);
                                //finish();
                                //int pid = android.os.Process.myPid();
                                //android.os.Process.killProcess(pid);
                                //System.exit(0);
                                //Intent.FLAG_ACTIVITY_NEW_TASK;
                                //finishAffinity();

                                //System.exit(0);
                                //Toast.makeText(getBaseContext(),"OK clicked!", Toast.LENGTH_SHORT).show();
                            }
                    ).create()
            2 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Please fill two fields")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                                //System.exit(0);
                                //finish();
                                //int pid = android.os.Process.myPid();
                                //android.os.Process.killProcess(pid);
                                //System.exit(0);
                                //Intent.FLAG_ACTIVITY_NEW_TASK;
                                //finishAffinity();

                                //System.exit(0);
                                //Toast.makeText(getBaseContext(),"OK clicked!", Toast.LENGTH_SHORT).show();
                            }
                    ).create()
        }
        return null
    }

}
