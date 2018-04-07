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
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.APIClient
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.UserProfileFragmentPresenterImpl
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.Others
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_profile.*
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import kotlinx.android.synthetic.main.fragment_user_profile_edit.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class UserProfileFragment : Fragment(), UserProfileFragmentView {

    var bitmap: Bitmap? = null
    var byteArray: ByteArray? = null
    var cal = Calendar.getInstance()
    var gender: Int? = null
    var imageString : String? = null

    var progressDialog : ProgressDialog? = null

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
        view.fragment_user_profile_constraintLayout_profile_imageView.setImageResource(R.drawable.profile_image)
        view.fragment_user_profile_constraintLayout_profile_imageView.setOnClickListener {
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

        view.fragment_user_profile_constraintLayout_dateOfBirth_editText!!.setOnClickListener {
            DatePickerDialog(mContext,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        view.fragment_user_profile_constraintLayout_relativeLayout_male_radioButton.setOnClickListener {
            gender = 1
            fragment_user_profile_constraintLayout_relativeLayout_female_radioButton.isChecked = false
        }

        view.fragment_user_profile_constraintLayout_relativeLayout_female_radioButton.setOnClickListener {
            gender = 2
            fragment_user_profile_constraintLayout_relativeLayout_male_radioButton.isChecked = false
        }

        view.fragment_user_profile_constraintLayout_submit_button.setOnClickListener {
            if(fragment_user_profile_constraintLayout_firstName_editText.text.isNotEmpty() && fragment_user_profile_constraintLayout_lastName_editText.text.isNotEmpty()
                    && fragment_user_profile_constraintLayout_dateOfBirth_editText.text.isNotEmpty() && gender != null
                    && fragment_user_profile_constraintLayout_weight_editText.text.toString().toInt() != null){
                userProfileFragmentPresenter?.userProfileDetails(imageString!!,fragment_user_profile_constraintLayout_firstName_editText.text.toString(),fragment_user_profile_constraintLayout_lastName_editText.text.toString(), fragment_user_profile_constraintLayout_dateOfBirth_editText.text.toString(),fragment_user_profile_constraintLayout_weight_editText.text.toString().toInt(),gender!!)
            }else{
                CustomToast().alertToast(mContext,"Fill the all fields")
            }
        }

        Log.e("inside create view : " ,byteArray.toString())
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
        fragment_user_profile_constraintLayout_dateOfBirth_editText.setText(sdf.format(cal.time))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
    }

    override fun onActivityResult(RC: Int, RQC: Int, I: Intent?) {
        super.onActivityResult(RC, RQC, I)

        /*if (RC == 1 && RQC == Activity.RESULT_OK && I != null && I.data != null) {
            val uri = I.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                byteArray = byteArrayOutputStream.toByteArray()
                Log.e("inside : ",byteArray.toString())
                fragment_user_profile_constraintLayout_profile_imageView.setImageBitmap(bitmap)
                val isr = resolver?.openInputStream(I.data!!)
                //uploadImage(getBytes(isr))

            } catch (e: IOException) {

            }
        }*/


        if (RC == 1 && RQC == Activity.RESULT_OK && I != null && I.data != null) {
            val uri = I.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                byteArray = byteArrayOutputStream.toByteArray()
                imageString= Base64.encodeToString(byteArray, Base64.DEFAULT)
                Others.DLog("inside : ", imageString!!)
                fragment_user_profile_constraintLayout_profile_imageView.setImageBitmap(bitmap!!)
                //upefView?.activity_user_profile_edit_constraintLayout_userProfile_imageView?.setImageBitmap(bitmap)
                //userProfileEditFragmentPresenterImpl?.sendImageString(imageString)
                //val isr = resolver?.openInputStream(I.data!!)
                //uploadImage(getBytes(isr))

            } catch (e: IOException) {

            }
        }
    }
    private fun getBytes(inputStream: InputStream?): ByteArray {
        val byteBuff = ByteArrayOutputStream()

        val buffSize = 1024
        val buff = ByteArray(buffSize)

        var len = 0
        len = inputStream!!.read(buff)
        while ( len != -1) {
            byteBuff.write(buff, 0, len)
            len = inputStream!!.read(buff)
        }
        return byteBuff.toByteArray()
    }

    /*private fun uploadImage(imageBytes: ByteArray) {

        progressDialog = ProgressDialog(mContext, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Authenticating...")
        progressDialog?.show()

        val retrofitInterface = APIClient.getClient(mcontext)

        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)

        val body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)

        retrofitInterface?.uploadImage(body)?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            CustomToast().normalToast(mContext,"Uploaded Successfully")
                        },
                        { error ->
                            progressDialog?.dismiss()
                        }
                )
    }
*/
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

