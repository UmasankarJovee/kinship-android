package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.kinship.model.*
import com.joveeinfotech.kinship.view.UserAdditionalDetailsFragment
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserAddrssFragmentPresenterImpl : APIListener, UserAddressFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private var userAddressFragmentView : UserAddrssFragmentView

    var mContext: Context
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
        queryParams.put("input", "countries")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, CountryResult::class.java, this, 1, "Fetching...")
    }

    override fun loadStates() {
            val queryParams = HashMap<String, String>()
            queryParams.put("input","states")
            //queryParams.put("subFieldName", country!!)
            Log.e("MAIN ACTIVITY : ", "inside button")
            networkCall?.APIRequest("api/v1/address", queryParams, StateResult::class.java, this, 2, "Fetching...")
    }

    override fun loadDistricts() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input","districts")
        //queryParams.put("subFieldName", state!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, DistrictResult::class.java, this, 3, "Fetching...")
    }

    /*override fun userAddressDetails(country: String, state: String, district: String, city: String, locality: String, street: String) {
        if (country.trim().isNotEmpty() && state.trim().isNotEmpty() && district.trim().isNotEmpty() && city.trim().isNotEmpty() && locality.trim().isNotEmpty() && street.trim().isNotEmpty()){
            sendAddress(country, state, district, city, locality, street)
        } else {
            //showDialog(0) // Please fill the all the fields
            Toast.makeText(mContext,"Please fill the all the fields", Toast.LENGTH_LONG).show()
        }
    }*/

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

    override fun userAddressDetails(country: String, state: String, district: String, city: String, locality: String, street: String) {
        val queryParams = HashMap<String, String>()
        var user_id = getStringPreference(mContext,"user_id","")
        queryParams.put("user_id","168")
        queryParams.put("country", country)
        queryParams.put("state", state)
        queryParams.put("district", district)
        queryParams.put("city", city)
        queryParams.put("locality",locality)
        queryParams.put("street", street)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, SendAddressResult::class.java, this, 4, "Sending your address...")
    }

    fun sendAddress1() {
        val queryParams = HashMap<String, String>()
        var user_id = SharedPreferenceHelper.getStringPreference(mContext, "user_id", "56")
        queryParams.put("user_id",user_id!!)
        queryParams.put("country", "India")
        queryParams.put("state", "Tamilnadu")
        queryParams.put("district","Madurai")
        queryParams.put("city","Madurai")
        queryParams.put("locality", "KK Nagar")
        queryParams.put("street_name", "Vinayagar")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/address", queryParams, SendAddressResult::class.java, this, 4, "Sending your address...")
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
                userAddressFragmentView.setCountries(countryList.country)
            }
            2 -> { // Get State
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val stateList = response as StateResult
                userAddressFragmentView.setStates(stateList.state)
            }
            3 -> { // Get District
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val districtList = response as DistrictResult
                userAddressFragmentView.setDistricts(districtList.districts)
            }
            4 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val addressResult = response as SendAddressResult
                if (addressResult.status) {
                    if(true){
                        trans?.replace(R.id.activity_user_details_frame_layout, UserAdditionalDetailsFragment.newInstance())
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