package com.joveeinfotech.kinship

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserProfileFragment : Fragment(), APIListener {

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0

    var bitmap: Bitmap? = null
    var ba: ByteArray? = null
    var cal = Calendar.getInstance()
    var gender: Int? = null

    //var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable: Disposable? = null

    var progressDialog: ProgressDialog? = null

    lateinit var mContext: Context
    var networkCall: APICall? = null

    var resolver: ContentResolver? = null
   //var view1 : View? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        resolver = activity?.contentResolver
        var view : View = inflater.inflate(R.layout.fragment_user_profile, container, false)
        view.imageView.setImageResource(R.drawable.profile_image)
        view.imageView.setOnClickListener {
            val i = Intent()
            i.type = "image/*"
            i.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(i, "select image"), 1)
        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        view.editText_date_of_birth!!.setOnClickListener {
            DatePickerDialog(mContext,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        view.radio_male.setOnClickListener {
            gender = 1
            radio_female.isChecked = false
        }

        view.radio_female.setOnClickListener {
            gender = 2
            radio_male.isChecked = false
        }

        view.user_profile_submit.setOnClickListener {
            if (editText_first_name.text.toString() != null && editText_last_name.text.toString() != null && editText_date_of_birth.text.toString() != null && gender != null) {
                sendUserProfile()
            } else {
                //showDialog(2) // Please fill the all details
            }

        }

        Log.e("inside create view : " ,ba.toString())
       /* if(savedInstanceState!=null){
            ba = savedInstanceState.getByteArray("myarray")
            view1?.editText_first_name?.setText(savedInstanceState.getString("first_name"))
            if(bitmap != null) {

                bitmap = BitmapFactory.decodeByteArray(ba, 0, ba!!.size)
                view1?.imageView?.setImageBitmap(bitmap)
                Log.e("inside create view an: " ,ba.toString())
            }
        }*/

        return view
    }

    private fun sendUserProfile() {
        val queryParams = HashMap<String, String>()
        queryParams.put("first_name", editText_first_name.text.toString())
        queryParams.put("last_name", editText_last_name.text.toString())
        queryParams.put("date_of_birth", editText_date_of_birth.text.toString())
        queryParams.put("gender", gender.toString())
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v5/persons", queryParams, UserProfileResult::class.java, this, 1, "Your Details are storing...")
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText_date_of_birth.setText(sdf.format(cal.time))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
        networkCall = APICall(mContext)
    }

    override fun onActivityResult(RC: Int, RQC: Int, I: Intent?) {
        super.onActivityResult(RC, RQC, I)

        if (RC == 1 && RQC == Activity.RESULT_OK && I != null && I.data != null) {
            val uri = I.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)
                val bStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, bStream)
                ba = bStream.toByteArray()
                Log.e("inside : ",ba.toString())
                imageView.setImageBitmap(bitmap)

            } catch (e: IOException) {

            }
        }
    }

    companion object {
        fun newInstance(): UserProfileFragment {
            return UserProfileFragment()
        }
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User profile
                val result = response as UserProfileResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    if(!UserDetails().isCompleteAddress!!){
                        val trans = fragmentManager?.beginTransaction()
                        trans?.replace(R.id.user_details_frame_layout,UserAddressFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()
                    }
                    /*val i = Intent(mContext, Home::class.java)
                    startActivity(i)*/
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(this,)
                    //snackbar(this.findViewById(android.R.id.content), "Please wait some minutes")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

  /*  override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("checking image array",ba.toString())

        outState.putByteArray("myarray", ba)
        outState.putString("first_name",editText_first_name.text.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(bitmap != null) {
            bitmap = BitmapFactory.decodeByteArray(ba, 0, ba!!.size)
        }
        Log.e("inside onActivityCre : ",ba.toString())
        //if(savedInstanceState!=null){
            //ba = savedInstanceState?.getByteArray("myarray")

            view1?.imageView?.setImageBitmap(bitmap)
    }*/

    /*override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        ba = savedInstanceState?.getByteArray("myarray")
        if(bitmap != null) {
            bitmap = BitmapFactory.decodeByteArray(ba, 0, ba!!.size)
        }
        imageView?.setImageBitmap(bitmap)
    }*/

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }
}

