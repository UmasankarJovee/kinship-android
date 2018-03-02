package com.joveeinfotech.kinship.view

import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.SendingUserProfileEdit
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.CountryResult
import com.joveeinfotech.kinship.model.DistrictResult
import com.joveeinfotech.kinship.model.StateResult
import com.joveeinfotech.kinship.presenter.UserProfileEditFragmentPresenterImpl
import com.joveeinfotech.kinship.utils.LocationService
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*
import kotlinx.android.synthetic.main.fragment_user_profile_edit.view.*
import kotlinx.android.synthetic.main.alert_address_details.*
import kotlinx.android.synthetic.main.alert_address_details.view.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserProfileEditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserProfileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileEditFragment : Fragment(),UserProfileEditFragmentView {

    var upefView:View?=null
    var networkCall: APICall? = null

    var country: String? = null
    var state: String? = null
    var district: String? = null

    var resolver: ContentResolver? = null
    lateinit var upefContext: Context
    var userProfileEditFragmentPresenter:UserProfileEditFragmentPresenterImpl?= null

    var cal = Calendar.getInstance()
    var address:String?=null

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

        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.visibility = View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_editIcon1_imageView?.visibility=View.GONE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_editText?.visibility = View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.visibility = View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_checkIcon1_imageView?.setOnClickListener{
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_textView?.setText(activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_editText.text.toString())
            //call("",activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_editText.text.toString())
            upefView?.activity_user_profile_edit_constraintLayout_userName_textView?.setText(activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_editText.text.toString())
            upefView?.activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_userName_editText?.visibility = View.GONE
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
            //call("",activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_phone_number_editText.text.toString())
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
            call("E-mail",activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_email_editText.text.toString())
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
            //activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_editText.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.visibility=View.VISIBLE
        }
        upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_checkIcon6_imageView?.setOnClickListener {
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.setText(address)
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_textView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_editIcon6_imageView?.visibility=View.VISIBLE
            upefView?.activity_user_profile_edit_constraintLayout_cardView2_constraintLayout_address_editText?.visibility=View.GONE
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

    override fun call(field: String, value: String) {

        var intent= Intent(upefContext,SendingUserProfileEdit::class.java)
        intent.putExtra("filed",field)
        intent.putExtra("value",field)
        upefContext.startService(intent)

    }

    override fun updateDateInView() {
        Log.d("Message","Before updateDataInView()")
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        Log.d("Message","after sdf")
        activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView.setText(sdf.format(cal.time))
        call("E-mail",activity_user_profile_edit_constraintLayout_cardView1_constraintLayout_dateOfBirth_textView.text.toString())
        Log.d("Message","set Value ")
    }
    companion object {
      fun newInstance(): UserProfileEditFragment {
          return UserProfileEditFragment()
      }
    }
}// Required empty public constructor
