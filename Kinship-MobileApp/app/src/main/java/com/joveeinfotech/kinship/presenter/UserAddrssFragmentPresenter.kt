package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.R.id.spinner_district
import com.joveeinfotech.kinship.UserAdditionalDetailsFragment
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.*
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserAddrssFragmentPresenter : APIListener, UserAddressFragmentPresenterPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private lateinit var userAddressFragmentView : UserAddrssFragmentView

    lateinit var mContext: Context
    var networkCall : APICall? = null
    var trans : FragmentTransaction? = null

    constructor(trans: FragmentTransaction?, view: UserAddrssFragmentView, context: Context){
        this.trans=trans
        this.mContext=context
        userAddressFragmentView=view
        initPresenter()
        loadCountries()

    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun loadCountries() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "country")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, CountryResult::class.java, this, 1, "Fetching...")
    }
    override fun sendCountryReceiveState(country: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "state")
        //queryParams.put("subFieldName", country!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, StateResult::class.java, this, 2, "Fetching...")
    }
    override fun sendStateReceiveDistrict(state: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "district")
        //queryParams.put("subFieldName", state!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, DistrictResult::class.java, this, 3, "Fetching...")
    }
    override fun userAddressDetails(country: String, state: String, district: String, city: String, locality: String, street: String) {
        if (country.trim().isNotEmpty() && state.trim().isNotEmpty() && city.trim().isNotEmpty() && locality.trim().isNotEmpty() && street.trim().isNotEmpty()){
            sendAddress(country, state, district, city, locality, street)
        } else {
            //showDialog(0) // Please fill the all the fields
            Toast.makeText(mContext,"Please fill the all the fields", Toast.LENGTH_LONG).show()
        }
    }
    private fun sendAddress(country: String, state: String, district: String, city: String, locality: String, street: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("country", country)
        queryParams.put("state", state)
        queryParams.put("district", district)
        queryParams.put("city", city)
        queryParams.put("locality","sdfg")
        queryParams.put("street", street)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, SendAddressResult::class.java, this, 4, "Sending your address...")
    }

    private fun sendAddress1() {
        val queryParams = HashMap<String, String>()
        queryParams.put("country", "India")
        queryParams.put("state", "TAmilnadu")
        queryParams.put("district","Madurai")
        queryParams.put("city","Madurai")
        queryParams.put("locality", "KK Nagar")
        queryParams.put("street_name", "Vinayagar")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, SendAddressResult::class.java, this, 4, "Sending your address...")
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
                userAddressFragmentView.setCountries(countryList)
            }
            2 -> { // Get State
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val stateList = response as StateResult
                userAddressFragmentView.setStates(stateList)
            }
            3 -> { // Get District
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val districtList = response as DistrictResult
                userAddressFragmentView.setDistricts(districtList)
            }
            4 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val addressResult = response as SendAddressResult
                if (addressResult.status) {
                    if(true){
                        trans?.replace(R.id.user_details_frame_layout, UserAdditionalDetailsFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()
                    }
                    Toast.makeText(mContext, "Successfully Stored", Toast.LENGTH_LONG).show()
                    //val i=Intent(applicationContext,UserAdditionalDetails::class.java)
                    //startActivity(i)
                }
            }
        }
    }
}