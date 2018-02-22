package com.joveeinfotech.kinship.view

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
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.UserProfileFragmentPresenterImpl
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserProfileFragment : Fragment(), UserProfileFragmentView {

    var bitmap: Bitmap? = null
    var ba: ByteArray? = null
    var cal = Calendar.getInstance()
    var gender: Int? = null

    lateinit var mContext: Context

    var resolver: ContentResolver? = null

    var userProfileFragmentPresenter : UserProfileFragmentPresenterImpl? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        resolver = activity?.contentResolver
        val trans = fragmentManager?.beginTransaction()
        userProfileFragmentPresenter = UserProfileFragmentPresenterImpl(trans,this,mContext)
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
            userProfileFragmentPresenter?.userProfileDetails(editText_first_name.text.toString(),editText_last_name.text.toString(), editText_date_of_birth.text.toString(),gender!!)
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

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        editText_date_of_birth.setText(sdf.format(cal.time))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
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

