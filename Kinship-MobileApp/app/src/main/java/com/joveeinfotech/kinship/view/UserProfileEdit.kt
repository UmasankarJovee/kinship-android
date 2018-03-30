package com.joveeinfotech.kinship.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
//import android.support.v4.app.FragmentTransaction
import android.support.v4.app.FragmentTransaction
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.SendingUserProfileEdit
import com.joveeinfotech.kinship.contract.KinshipContract
import com.joveeinfotech.kinship.model.DistrictResult
import com.joveeinfotech.kinship.model.StateResult
import com.joveeinfotech.kinship.presenter.LoginPresenterImpl
import com.joveeinfotech.kinship.presenter.UserProfileEditFragmentPresenterImpl
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.LocationService
import com.joveeinfotech.kinship.utils.Others
import com.joveeinfotech.kinship.utils.Others.DLog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.alert_address_details.*
import kotlinx.android.synthetic.main.alert_address_details.view.*
import kotlinx.android.synthetic.main.alert_user_details.view.*
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*
import kotlinx.android.synthetic.main.fragment_user_profile_edit.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserProfileEdit : AppCompatActivity(), KinshipContract.UserProfileEditFragmentView {

    var bitmap: Bitmap? = null
    var byteArray: ByteArray? = null
    var progressDialog : ProgressDialog? = null
    //var upefView: View?=null
    var networkCall: APICall? = null

    var country: String? = null
    var state: String? = null
    var district: String? = null

    var dialogView : View? = null
    var resolver: ContentResolver? = null
    //lateinit var upefContext: Context
    var userProfileEditFragmentPresenterImpl: UserProfileEditFragmentPresenterImpl?=null

    var cal = Calendar.getInstance()
    var address:String?=null

    var first_name:EditText?=null
    var last_name:EditText?=null
    var country_spinner : Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_user_profile_edit)

        resolver=contentResolver
        val trans= fragmentManager?.beginTransaction()

        userProfileEditFragmentPresenterImpl = UserProfileEditFragmentPresenterImpl(this,this)

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.setLines(1)
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.setHorizontallyScrolling(true)
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.setSingleLine()

        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setLines(1)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setHorizontallyScrolling(true)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setSingleLine()

        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.setLines(1)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.setHorizontallyScrolling(true)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.setSingleLine()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            Others.DLog("Message", "Inside dataSetListener")
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            Others.DLog("Message", "Before call updateDataInView()")
            updateDateInView()
            Others.DLog("Message", "After finish updateDataInView()")
        }
        activity_user_profile_edit_constraintLayout_userProfileEditIcon_imageView?.setOnClickListener {
            var intent=Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "select image"), 1)
        }

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.setOnClickListener{
            val dialogbuilder: AlertDialog.Builder= AlertDialog.Builder(this)
            val inflater:LayoutInflater=this.layoutInflater
            dialogView =inflater.inflate(R.layout.alert_user_details,null)
            dialogbuilder.setView(dialogView)
            val dialogbuilderCall=dialogbuilder.create()
            dialogbuilderCall.show()
            first_name=dialogView?.findViewById(R.id.alert_user_details_firstName_editText)
            last_name=dialogView?.findViewById(R.id.alert_user_details_lastName_editText)
            //country_spinner = dialogView?.findViewById<Spinner>(R.id.alert_address_details_scrollView_linearLayout1_country_spinner) as Spinner

            dialogView?.alert_user_details_okButton?.setOnClickListener {
                Toast.makeText(this,"sorry doalogBuilder is error", Toast.LENGTH_SHORT).show()
                dialogbuilderCall.dismiss()
            }
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.visibility = View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.setText(first_name?.text.toString()+last_name?.text.toString())
            activity_user_profile_edit_constraintLayout_userName_textView?.setText(first_name?.text.toString()+last_name?.text.toString())
            call("first_name",first_name?.text.toString(),"last_name",last_name?.text.toString())
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView?.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView?.visibility = View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView?.setText(activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.text.toString())
            call("phone_number",activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.text.toString(),"","")
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText?.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon2_imageView?.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView?.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon2_imageView?.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon3_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon3_imageView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon3_imageView?.visibility = View.VISIBLE

            Others.DLog("Message", "Before DataPickerDialog")
            DatePickerDialog(this,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            Others.DLog("Message", "After DataPickerDialog")
        }

        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon3_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon3_imageView?.visibility = View.GONE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView?.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon3_imageView?.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_textView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText?.visibility = View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView?.visibility = View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_textView?.setText(activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText.text.toString())
            call("weight",activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText.text.toString(),"","")
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_textView?.visibility=View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon4_imageView?.visibility=View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText?.visibility =View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon4_imageView?.visibility =View.GONE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.visibility=View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView?.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView?.setOnClickListener{
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.setText(activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.text.toString())
            call("email",activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.text.toString(),"","")
            Toast.makeText(this,activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.text.toString(), Toast.LENGTH_SHORT).show()
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView?.visibility=View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon5_imageView?.visibility=View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon5_imageView?.visibility=View.GONE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon6_imageView?.setOnClickListener {
            Others.DLog("Root", "1")
            val dialogbuilder: AlertDialog.Builder= AlertDialog.Builder(this)
            val inflater:LayoutInflater=this.layoutInflater
            dialogView =inflater.inflate(R.layout.alert_address_details,null)
            dialogbuilder.setView(dialogView)
            val dialogBuilderCall=dialogbuilder.create()
            dialogBuilderCall.show()
            Others.DLog("Root", "2")
            userProfileEditFragmentPresenterImpl?.loadCountries()
            Others.DLog("Root", "3")
            /* val street: EditText =dialogView.findViewById(R.id.alert_address_details_scrollView_linearLayout1_street_editText)
             val locality: EditText =dialogView.findViewById(R.id.alert_address_details_scrollView_linearLayout1_locality_editText)
             val city: EditText =dialogView.findViewById(R.id.alert_address_details_scrollView_linearLayout1_city_editText)*/
            dialogView?.alert_address_details_scrollView_linearLayout1_linearLayout2_cancelButton?.setOnClickListener {
                //dialogbuilder.create().dismiss()
                dialogBuilderCall.dismiss()
            }
            dialogView?.alert_address_details_scrollView_linearLayout1_linearLayout2_linearLayout3_okButton?.setOnClickListener {
                //userProfileEditFragmentPresenterImpl?.sendAddress()
                dialogBuilderCall.dismiss()
                if (country?.trim()?.length == 0 && state?.trim()?.length == 0 && district?.trim()?.length == 0
                        && alert_address_details_scrollView_linearLayout1_city_editText.text.trim().isNotEmpty() && alert_address_details_scrollView_linearLayout1_locality_editText.text.trim().isNotEmpty()
                        && alert_address_details_scrollView_linearLayout1_street_editText.text.trim().isNotEmpty()){
                    userProfileEditFragmentPresenterImpl?.userAddressDetails(country!!,state!!,district!!,alert_address_details_scrollView_linearLayout1_city_editText.text.toString(),alert_address_details_scrollView_linearLayout1_locality_editText.text.toString(),alert_address_details_scrollView_linearLayout1_street_editText.text.toString())
                } else {
                    //showDialog(0) // Please fill the all the fields
                    Toast.makeText(this,"Please fill the all the fields", Toast.LENGTH_LONG).show()
                }
                /*address="${street.text.toString()},${locality.text.toString()},${city.text.toString()},${district.text},${state.text}"
                Toast.makeText(upefContext,address,Toast.LENGTH_LONG).show()*/
            }
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon6_imageView?.visibility=View.GONE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.visibility=View.VISIBLE
        }
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.setOnClickListener {
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.setText(address)
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.visibility=View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon6_imageView?.visibility=View.VISIBLE
            activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.visibility=View.GONE
        }


    }

    override fun setProfileDetails(image_url: String, name: String, date_of_birth: String, weight: String, address: String, phone_number: String, email: String) {
        Others.DLog("New Message", "${image_url}")
        Picasso.with(this).load(image_url).into(activity_user_profile_edit_constraintLayout_userProfile_imageView)
        activity_user_profile_edit_constraintLayout_userName_textView.text=name
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView.text=name
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phoneNumber_textView.text=phone_number
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.setText(phone_number)
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView.text=date_of_birth
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_textView.text=weight
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_weight_editText.setText(weight)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_textView.text=email
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.setText(email)
        activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView.text=address
    }

    override fun setCountries(countryList: ArrayList<String>) {
        //countryList.add(0,this.getString(R.string.user_address_select_your_country))
        Others.DLog("Root", "2i2")
        val dataAdapter = ArrayAdapter(this@UserProfileEdit, android.R.layout.simple_spinner_item, countryList)
        DLog("Root1","${countryList}")
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        DLog("Root2","${dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)}")
        dialogView?.alert_address_details_scrollView_linearLayout1_country_spinner?.adapter=dataAdapter
        //country_spinner?.adapter = dataAdapter
        DLog("Root3","${dataAdapter}")
        dialogView?.alert_address_details_scrollView_linearLayout1_country_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                country = countryList[position].toString()
                userProfileEditFragmentPresenterImpl?.sendCountryReceiveState(country!!)
                Toast.makeText(this@UserProfileEdit, country, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun setStates(stateList: StateResult) {
        Others.DLog("Root", "2i4")
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList.state)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogView?.alert_address_details_scrollView_linearLayout1_state_spinner?.adapter = dataAdapter
        dialogView?.alert_address_details_scrollView_linearLayout1_state_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                state = stateList.state[position].toString()
                userProfileEditFragmentPresenterImpl?.sendStateReceiveDistrict(state!!)
                Toast.makeText(this@UserProfileEdit, state, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun setDistricts(districtList: DistrictResult) {
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districtList.districts)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogView?.alert_address_details_scrollView_linearLayout1_district_spinner?.adapter = dataAdapter
        dialogView?.alert_address_details_scrollView_linearLayout1_district_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                district = districtList.districts[position].toString()
                //sendStateReceiveDistrict()
                Toast.makeText(this@UserProfileEdit, district, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun call(field: String, value: String,field1:String,value1:String) {

        Others.DLog("call Message", "Before call SendingUserProfileEddit")
        var intent= Intent(this, SendingUserProfileEdit::class.java)
        intent.putExtra("field",field)
        intent.putExtra("value",value)
        intent.putExtra("field1",field1)
        intent.putExtra("value1",value1)
        Others.DLog("call Message", "${field},${value},${field1},${value1}")
        this.startService(intent)

    }

    override fun updateDateInView() {
        Others.DLog("Message", "Before updateDataInView()")
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        Others.DLog("Message", "after sdf")
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView.setText(sdf.format(cal.time))
        call("date_of_birth",activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView.text.toString(),"","")
        Others.DLog("Message", "set Value ")
    }

    override fun onActivityResult(RC: Int, RQC: Int, I: Intent?) {
        super.onActivityResult(RC, RQC, I)

        if (RC == 1 && RQC == Activity.RESULT_OK && I != null && I.data != null) {
            val uri = I.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(resolver, uri)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                byteArray = byteArrayOutputStream.toByteArray()
                val imageString= Base64.encodeToString(byteArray, Base64.DEFAULT)
                Others.DLog("inside : ", imageString)
                activity_user_profile_edit_constraintLayout_userProfile_imageView?.setImageBitmap(bitmap)
                userProfileEditFragmentPresenterImpl?.sendImageString(imageString)
                //val isr = resolver?.openInputStream(I.data!!)
                //uploadImage(getBytes(isr))

            } catch (e: IOException) {

            }
        }
    }

}