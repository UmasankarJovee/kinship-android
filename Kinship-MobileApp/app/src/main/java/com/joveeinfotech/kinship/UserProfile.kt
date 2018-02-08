package com.joveeinfotech.kinship

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.example.prandex_and_05.userregistration.APICall
import com.example.prandex_and_05.userregistration.APIListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import org.jetbrains.anko.design.snackbar
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserProfile : AppCompatActivity(), APIListener {

    var year : Int=0
    var month: Int =0
    var day : Int =0

    var bitmap : Bitmap?=null
    var ba:ByteArray?=null
    var cal = Calendar.getInstance()
    var gender : Int? = null

    var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable: Disposable? = null

    var progressDialog: ProgressDialog? = null

    var networkCall : APICall? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        networkCall = APICall(this)

        mApiInterface=RetrofitClient.getClient()

        imageView.setImageResource(R.drawable.profile_image)
        imageView.setOnClickListener{
            val i= Intent()
            i.type="image/*"
            i.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i,"select image"),1)
        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        editText_date_of_birth!!.setOnClickListener {
            DatePickerDialog(this@UserProfile,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        radio_male.setOnClickListener{
            gender = 1
            radio_female.isChecked = false
        }

        radio_female.setOnClickListener{
            gender = 2
            radio_male.isChecked = false
        }

        user_profile_submit.setOnClickListener{
            if(editText_first_name.text.toString() != null && editText_last_name.text.toString() != null && editText_date_of_birth.text.toString() != null && gender != null)
            {
                sendUserProfile()
            }
            else
            {
                showDialog(2) // Please fill the all details
            }

        }
    }

    private fun sendUserProfile() {
        val queryParams = HashMap<String, String>()
        queryParams.put("user_id","27")
        queryParams.put("first_name", editText_first_name.text.toString())
        queryParams.put("last_name", editText_last_name.text.toString())
        queryParams.put("date_of_birth", editText_date_of_birth.text.toString())
        queryParams.put("gender", gender.toString())
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v5/persons",queryParams, UserProfileResult::class.java,this, 1, "Your Details are storing...")
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText_date_of_birth.setText(sdf.format(cal.time))
    }

    private fun sendUserProfile1() {
        progressDialog = ProgressDialog(this@UserProfile, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Your Details are Storing...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.sendUserProfile(27,editText_first_name.text.toString(),editText_last_name.text.toString(),editText_date_of_birth.text.toString(), this!!.gender!!)!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            if(result.status == true)
                            {
                                //Toast.makeText(applicationContext,result.message,Toast.LENGTH_LONG).show()
                                Toast.makeText(this,"Your Details are Uploaded", Toast.LENGTH_LONG).show()
                                val i= Intent(applicationContext,Home::class.java)
                                startActivity(i)
                            }
                            else
                            {
                                showDialog(0) // Try again
                            }
                            //displayLog("response")
                        },
                        { error ->
                            progressDialog?.dismiss()
                            //Toast.makeText(this,"Your Details are Uploaded", Toast.LENGTH_LONG).show()
                            //val i= Intent(applicationContext,Home::class.java)
                            //startActivity(i)
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                )
    }

    override fun onActivityResult(RC: Int, RQC: Int, I: Intent?) {
        super.onActivityResult(RC,RQC,I)

        if(RC == 1 && RQC == Activity.RESULT_OK && I != null && I.data != null)
        {
            val uri=I.data
            try {
                bitmap =  MediaStore.Images.Media.getBitmap(contentResolver,uri)
                val bStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG,100,bStream)
                ba= bStream.toByteArray()
                imageView.setImageBitmap(bitmap)

            }
            catch (e : IOException){

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putByteArray("myarray",ba)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        ba=savedInstanceState?.getByteArray("myarray")
        bitmap = BitmapFactory.decodeByteArray(ba,0,ba!!.size)
        imageView.setImageBitmap(bitmap)
    }



    override fun onCreateDialog(id: Int): Dialog? {
        when (id) {
            0 -> return AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Try again")
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
                    .setTitle("Please fill all Details")
                    .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, whichButton ->
                            }
                    ).create()
        }
        return null
    }


    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User profile
                val result = response as UserProfileResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    val i= Intent(applicationContext,Home::class.java)
                    startActivity(i)
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else
                {
                    //snackbar(this,)
                    snackbar(this.findViewById(android.R.id.content), "Please wait some minutes")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onNetworkFailure(from: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

