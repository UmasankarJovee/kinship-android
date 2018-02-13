package com.joveeinfotech.kinship

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_user_address.*
import kotlinx.android.synthetic.main.fragment_user_address.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UserAddressFragment : Fragment(), APIListener {

    var networkCall: APICall? = null

    private var mCountryResult: ArrayList<CountryResult>? = null
    private var mStateResult: ArrayList<CountryResult>? = null
    private var mDistrictResult: ArrayList<CountryResult>? = null

    var country: String? = null
    var state: String? = null
    var district: String? = null

    var resolver: ContentResolver? = null
    lateinit var mContext: Context
    //var view1 : View? = null

    var sf : AppCompatActivity? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

       sf = AppCompatActivity()
        resolver = activity?.contentResolver
        var view : View = inflater.inflate(R.layout.fragment_user_address, container, false)

      /*  val toolbar = view.findViewById<Toolbar>(R.id.MyToolbar) as Toolbar
        //activity.setSupportActionBar(toolbar)
        (sf)?.setSupportActionBar(toolbar)
        (sf)?.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        */
        val collapsingToolbarLayout = view.findViewById<CollapsingToolbarLayout>(R.id.collapse_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = "Home Address"
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#fcfbfb"))
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#fcfbfb"))
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#FF919297"))

        val context = this
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(mContext, R.color.toolBarColor))

        getCountryDetails()

        view.button_send_address.setOnClickListener {
            if (country != null && state != null && district != null && editText_city.text.toString() != null && editText_street.text.toString() != null) {
                sendAddress()
            } else {
                //showDialog(0) // Please fill the all the fields
                Toast.makeText(mContext,"Please fill the all the fields", Toast.LENGTH_LONG).show()
            }
        }
        return view
    }

    private fun getCountryDetails() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "country")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, CountryResult::class.java, this, 1, "Fetching...")
    }

    private fun sendCountryReceiveState() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "state")
        //queryParams.put("subFieldName", country!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, StateResult::class.java, this, 2, "Fetching...")
    }

    private fun sendStateReceiveDistrict() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "district")
        //queryParams.put("subFieldName", state!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, DistrictResult::class.java, this, 3, "Fetching...")
    }

    private fun sendAddress() {
        val queryParams = HashMap<String, String>()
        queryParams.put("country", country!!)
        queryParams.put("state", state!!)
        queryParams.put("district", district!!)
        queryParams.put("city", editText_city.text.toString())
        queryParams.put("street", editText_street.text.toString())
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, SendAddressResult::class.java, this, 4, "Sending your address...")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
        networkCall = APICall(mContext)
    }

    companion object {
        fun newInstance(): UserAddressFragment {
            return UserAddressFragment()
        }
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Get Country
                /*val countryList = response as List<CountryResult>
                mCountryResult = ArrayList(countryList)
                Log.e("API CALL : ", "inside Main activity and onSuccess")

                val dataAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,mCountryResult)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_counry.adapter=dataAdapter

                spinner_counry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>? ) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        country=mCountryResult?.get(position).toString()
                        Toast.makeText(applicationContext,country,Toast.LENGTH_LONG).show()
                    }
                }*/

                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val countryList = response as CountryResult
                val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, countryList.country)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_counry.adapter = dataAdapter

                spinner_counry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        country = countryList.country[position].toString()
                        sendCountryReceiveState()
                        Toast.makeText(mContext, country, Toast.LENGTH_LONG).show()
                    }
                }
            }
            2 -> { // Get State
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val stateList = response as StateResult
                val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, stateList.state)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_state.adapter = dataAdapter

                spinner_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        state = stateList.state[position].toString()
                        sendStateReceiveDistrict()
                        Toast.makeText(mContext, state, Toast.LENGTH_LONG).show()
                    }
                }
            }
            3 -> { // Get District
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val districtList = response as DistrictResult
                val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, districtList.district)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_district.adapter = dataAdapter

                spinner_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        district = districtList.district[position].toString()
                        //sendStateReceiveDistrict()
                        Toast.makeText(mContext, district, Toast.LENGTH_LONG).show()
                    }
                }
            }
            4 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val addressResult = response as SendAddressResult
                if (addressResult.status) {
                    Toast.makeText(mContext, "Successfully Stored", Toast.LENGTH_LONG).show()
                    //val i=Intent(applicationContext,UserAdditionalDetails::class.java)
                    //startActivity(i)
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