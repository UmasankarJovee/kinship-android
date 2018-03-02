package com.joveeinfotech.kinship.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIClient
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.SendingUserProfileEdit
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.UserProfileEditFragmentPresenterImpl
import com.joveeinfotech.kinship.utils.CustomToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*
import kotlinx.android.synthetic.main.fragment_user_profile_edit.view.*
import kotlinx.android.synthetic.main.alert_address_details.view.*
import kotlinx.android.synthetic.main.alert_user_details.view.*
import kotlinx.android.synthetic.main.top20_donars_list.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserProfileEditFragment] interface
 * to handle interaction events.
 * Use the [UserProfileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileEditFragment : Fragment(),UserProfileEditFragmentView {

    var bitmap: Bitmap? = null
    var ba: ByteArray? = null
    var progressDialog : ProgressDialog? = null
    var upefView:View?=null
    var networkCall: APICall? = null

    var country: String? = null
    var state: String? = null
    var district: String? = null

    var resolver: ContentResolver? = null
    lateinit var upefContext: Context
    var userProfileEditFragmentPresenter:UserProfileEditFragmentPresenterImpl?=null

    var cal = Calendar.getInstance()
    var address:String?=null

    var first_name:EditText?=null
    var last_name:EditText?=null

    override fun onAttach(context: Context) {
        this.upefContext = context
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        resolver = activity?.contentResolver
        val trans= fragmentManager?.beginTransaction()
        upefView=inflater.inflate(R.layout.fragment_user_profile_edit, container, false)

        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.setLines(1)
        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.setHorizontallyScrolling(true)
        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.setSingleLine()

        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setLines(1)
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setHorizontallyScrolling(true)
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setSingleLine()

        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.setLines(1)
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.setHorizontallyScrolling(true)
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.setSingleLine()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            Log.d("Message","Inside dataSetListener")
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            Log.d("Message","Before call updateDataInView()")
            updateDateInView()
            Log.d("Message","After finish updateDataInView()")
        }
        upefView?.activity_user_profile_edit_constraintLayout_userProfileEditIcon_imageView?.setOnClickListener {
            var intent=Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "select image"), 1)
        }

        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.setOnClickListener{
            val dialogbuilder: AlertDialog.Builder= AlertDialog.Builder(upefContext)
            val inflater:LayoutInflater=this.layoutInflater
            val dialogView:View=inflater.inflate(R.layout.alert_user_details,null)
            dialogbuilder.setView(dialogView)
            dialogbuilder.create().show()
            first_name=dialogView.findViewById(R.id.alert_user_details_firstName_editText)
            last_name=dialogView.findViewById(R.id.alert_user_details_lastName_editText)

            dialogView.alert_user_details_okButton.setOnClickListener {
                call("first_name",first_name?.text.toString(),"last_name",last_name?.text.toString())
                dialogbuilder.create().dismiss()
            }
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.visibility = View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.visibility = View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.setText(first_name?.text.toString()+last_name?.text.toString())
            upefView?.activity_user_profile_edit_constraintLayout_userName_textView?.setText(first_name?.text.toString()+last_name?.text.toString())
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.visibility = View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.visibility = View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.visibility=View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView?.visibility = View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.visibility = View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView?.visibility = View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView?.setText(activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.text.toString())
            call("phone_number",activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.text.toString(),"","")
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.visibility = View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView?.visibility = View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView?.visibility = View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView?.visibility=View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon3_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon3_imageView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon3_imageView?.visibility = View.VISIBLE

            Log.d("Message","Before DataPickerDialog")
            DatePickerDialog(upefContext,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            Log.d("Message","After DataPickerDialog")
        }

        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon3_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon3_imageView?.visibility = View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView?.visibility = View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon3_imageView?.visibility=View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_textView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText?.visibility = View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView?.visibility = View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_textView?.setText(activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText.text.toString())
            call("weight",activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText.text.toString(),"","")
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_textView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText?.visibility =View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView?.visibility =View.GONE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView?.visibility=View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setText(activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.text.toString())
            call("E-mail",activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.text.toString(),"","")
            Toast.makeText(upefContext,activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.text.toString(),Toast.LENGTH_SHORT).show()
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView?.visibility=View.GONE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon6_imageView?.setOnClickListener {
            userProfileEditFragmentPresenter = UserProfileEditFragmentPresenterImpl(trans,this,upefContext)
            val dialogbuilder: AlertDialog.Builder= AlertDialog.Builder(upefContext)
            val inflater:LayoutInflater=this.layoutInflater
            val dialogView:View=inflater.inflate(R.layout.alert_address_details,null)
            dialogbuilder.setView(dialogView)
            dialogbuilder.create().show()
           /* val street: EditText =dialogView.findViewById(R.id.alert_address_details_scrollView_linearLayout1_street_editText)
            val locality: EditText =dialogView.findViewById(R.id.alert_address_details_scrollView_linearLayout1_locality_editText)
            val city: EditText =dialogView.findViewById(R.id.alert_address_details_scrollView_linearLayout1_city_editText)*/
            dialogView.alert_address_details_scrollView_linearLayout1_linearLayout2_cancelButton.setOnClickListener {
                dialogbuilder.create().dismiss()
            }
            dialogView.alert_address_details_scrollView_linearLayout1_linearLayout2_linearLayout3_okButton.setOnClickListener {
                userProfileEditFragmentPresenter?.sendAddress1()
                dialogbuilder.create().dismiss()
                /* if (country?.trim()?.length == 0 && state?.trim()?.length == 0 && district?.trim()?.length == 0
                         && editText_city.text.trim().isNotEmpty() && editText_locality.text.trim().isNotEmpty()
                         && editText_street.text.trim().isNotEmpty()){
                     userAddressFragmentPresenter?.userAddressDetails(country!!,state!!,district!!,editText_city.text.toString(),"gdgdg",editText_street.text.toString())
                 } else {
                     //showDialog(0) // Please fill the all the fields
                     Toast.makeText(mContext,"Please fill the all the fields", Toast.LENGTH_LONG).show()
                 }*/
                /*address="${street.text.toString()},${locality.text.toString()},${city.text.toString()},${district.text},${state.text}"
                Toast.makeText(upefContext,address,Toast.LENGTH_LONG).show()*/
            }
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon6_imageView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.visibility=View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.setOnClickListener {
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.setText(address)
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon6_imageView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.visibility=View.GONE
        }

        return upefView
    }
    /*override fun setCountries(countryList: CountryResult) {
        val dataAdapter = ArrayAdapter(upefContext, android.R.layout.simple_spinner_item, countryList.country)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alert_address_details_scrollView_linearLayout1_country_spinner.adapter=dataAdapter
        alert_address_details_scrollView_linearLayout1_country_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                country = countryList.country[position].toString()
                userProfileEditFragmentPresenter?.sendCountryReceiveState(country!!)
                Toast.makeText(upefContext, country, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun setStates(stateList: StateResult) {
        val dataAdapter = ArrayAdapter(upefContext, android.R.layout.simple_spinner_item, stateList.state)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alert_address_details_scrollView_linearLayout1_state_spinner.adapter = dataAdapter
        alert_address_details_scrollView_linearLayout1_state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                state = stateList.state[position].toString()
                userProfileEditFragmentPresenter?.sendStateReceiveDistrict(state!!)
                Toast.makeText(upefContext, state, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun setDistricts(districtList: DistrictResult) {
        val dataAdapter = ArrayAdapter(upefContext, android.R.layout.simple_spinner_item, districtList.district)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        alert_address_details_scrollView_linearLayout1_district_spinner.adapter = dataAdapter
        alert_address_details_scrollView_linearLayout1_district_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                district = districtList.district[position].toString()
                //sendStateReceiveDistrict()
                Toast.makeText(upefContext, district, Toast.LENGTH_LONG).show()
            }
        }
    }*/

    override fun call(field: String, value: String,field1:String,value1:String) {

        var intent= Intent(upefContext,SendingUserProfileEdit::class.java)
        intent.putExtra("field",field)
        intent.putExtra("value",value)
        intent.putExtra("field1",field1)
        intent.putExtra("value1",value1)
        upefContext.startService(intent)

    }

    override fun updateDateInView() {
        Log.d("Message","Before updateDataInView()")
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        Log.d("Message","after sdf")
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView.setText(sdf.format(cal.time))
        call("date_of_birth",activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView.text.toString(),"","")
        Log.d("Message","set Value ")
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
                Log.e("inside : ", ba.toString())
                upefView?.activity_user_profile_edit_constraintLayout_userProfile_imageView?.setImageBitmap(bitmap)
                val isr = resolver?.openInputStream(I.data!!)
                uploadImage(getBytes(isr))

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

    private fun uploadImage(imageBytes: ByteArray) {

        progressDialog = ProgressDialog(upefContext, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Authenticating...")
        progressDialog?.show()

        val retrofitInterface = APIClient.getClient()

        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)

        val body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile)

        retrofitInterface?.uploadImage(body)?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            CustomToast().normalToast(upefContext,"Uploaded Successfully")
                        },
                        { error ->
                            progressDialog?.dismiss()
                        }
                )
    }

   /* private fun setCircle() {

        val paint = Paint()
        // Get source bit map width and height
        val srcBitmapWidth = bitmap?.width
        val srcBitmapHeight = bitmap?.height

        *//*
                    IMPORTANT NOTE : You should experiment with border and shadow width
                    to get better circular ImageView as you expected.
                    I am confused about those size.
                *//*
        // Define border and shadow width
        val borderWidth = 25
        val shadowWidth = 10

        // destination bitmap width
        val dstBitmapWidth = Math.min(srcBitmapWidth!!, srcBitmapHeight!!) + borderWidth * 2
        //float radius = Math.min(srcBitmapWidth,srcBitmapHeight)/2;

        // Initializing a new bitmap to draw source bitmap, border and shadow
        val dstBitmap = Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888)

        // Initialize a new canvas
        val canvas = Canvas(dstBitmap)

        // Draw a solid color to canvas
        canvas.drawColor(Color.WHITE)

        // Draw the source bitmap to destination bitmap by keeping border and shadow spaces
        canvas.drawBitmap(bitmap, ((dstBitmapWidth - srcBitmapWidth) / 2).toFloat(), ((dstBitmapWidth - srcBitmapHeight) / 2).toFloat(), null)

        // Use Paint to draw border
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = (borderWidth * 2).toFloat()
        paint.color = Color.RED

        // Draw the border in destination bitmap
        canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), (canvas.width / 2).toFloat(), paint)

        // Use Paint to draw shadow
        paint.color = Color.LTGRAY
        paint.strokeWidth = shadowWidth.toFloat()

        // Draw the shadow on circular bitmap
        canvas.drawCircle((canvas.width / 2).toFloat(), (canvas.height / 2).toFloat(), (canvas.width / 2).toFloat(), paint)

        *//*
                    RoundedBitmapDrawable
                        A Drawable that wraps a bitmap and can be drawn with rounded corners. You
                        can create a RoundedBitmapDrawable from a file path, an input stream, or
                        from a Bitmap object.
                *//*
        // Initialize a new RoundedBitmapDrawable object to make ImageView circular
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources!!, dstBitmap)

        *//*
                    setCircular(boolean circular)
                        Sets the image shape to circular.
                *//*
        // Make the ImageView image to a circular image
        roundedBitmapDrawable.isCircular = true

        *//*
                    setAntiAlias(boolean aa)
                        Enables or disables anti-aliasing for this drawable.
                *//*
        roundedBitmapDrawable.setAntiAlias(true)

        // Set the ImageView image as drawable object
        //activity_profile_display_profile_image.setImageDrawable(roundedBitmapDrawable)
        top20_donars_list_Linear_layout_CardView_ImageView_profile.setImageDrawable(roundedBitmapDrawable)
    }*/
    companion object {
      fun newInstance(): UserProfileEditFragment {
          return UserProfileEditFragment()
      }
    }
}// Required empty public constructor
