package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.kinship.model.*
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.Others
import com.joveeinfotech.kinship.utils.Others.DLog
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 27/2/18.
 */
class UserProfileEditFragmentPresenterImpl( var view: UserProfileEditFragmentView, var context: Context):APIListener,UserProfileEditFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    var networkCall : APICall? = null
    var addressNetworkCall:APICall?=null
    init {
        initPresenter()
        loadProfileDetails()
    }
    override fun initPresenter() {
        networkCall = APICall(context)
    }
    override fun loadProfileDetails() {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number", "8189922043")
        DLog("new","hello")
        networkCall?.APIRequest("api/v1/profile", queryParams, UserProfileDisplayResult::class.java, this, 4, "Fetching...")
    }
    override fun loadCountries() {
        Others.DLog("Root", "2i1")
        addressNetworkCall= APICall(context)
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "countries")
        Log.e("MAIN ACTIVITY : ", "inside button")
        addressNetworkCall?.APIRequest("api/v1/address", queryParams, CountryResult::class.java, this, 1, "Fetching...")
    }
    override fun sendCountryReceiveState() {
        Others.DLog("Root", "2i3")
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "states")
        //queryParams.put("subFieldName", country!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        addressNetworkCall?.APIRequest("api/v1/address", queryParams, StateResult::class.java, this, 2, "Fetching...")
    }
    override fun sendStateReceiveDistrict() {
        Others.DLog("Root", "2i5")
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "districts")
        //queryParams.put("subFieldName", state!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        addressNetworkCall?.APIRequest("api/v1/address", queryParams, DistrictResult::class.java, this, 3, "Fetching...")
    }
    override fun userAddressDetails(country: String, state: String, district: String, city: String, locality: String, street: String) {
        /*if (country.trim().isNotEmpty() && state.trim().isNotEmpty() && district.trim().isNotEmpty() && city.trim().isNotEmpty() && locality.trim().isNotEmpty() && street.trim().isNotEmpty()){
            sendAddress(country, state, district, city, locality, street)
        } else {
            //showDialog(0) // Please fill the all the fields
            Toast.makeText(context,"Please fill the all the fields", Toast.LENGTH_LONG).show()
        }*/
        val queryParams = HashMap<String, String>()
        var user_id = getStringPreference(context,"user_id","")
        queryParams.put("addressable_id","106")
        queryParams.put("countries", country)
        queryParams.put("states", state)
        queryParams.put("districts", district)
        queryParams.put("city", city)
        queryParams.put("locality",locality)
        queryParams.put("street_name", street)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v6/address", queryParams, SendAddressResult::class.java, this, 6, "Sending your address...")
    }
   /* private fun sendAddress(country: String, state: String, district: String, city: String, locality: String, street: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("id","169")
        queryParams.put("country", country)
        queryParams.put("state", state)
        queryParams.put("district", district)
        queryParams.put("city", city)
        queryParams.put("locality",locality)
        queryParams.put("street_name", street)
        Log.e("MAIN ACTIVITY : ", "inside button")
        addressNetworkCall?.APIRequest("api/v1/address", queryParams, SendAddressResult::class.java, this, 6, "Sending your address...")
    }*/

    /*fun sendAddress1() {
        val queryParams = HashMap<String, String>()
        queryParams.put("country", "India")
        queryParams.put("state", "Tamilnadu")
        queryParams.put("district","Madurai")
        queryParams.put("city","Madurai")
        queryParams.put("locality", "KK Nagar")
        queryParams.put("street_name", "Vinayagar")
        Log.e("MAIN ACTIVITY : ", "inside button")
        addressNetworkCall?.APIRequest("api/v1/address", queryParams, SendAddressResult::class.java, this, 4, "Sending your address...")
    }*/

    override fun sendImageString(imageString: String) {
        val str:String="data:image/png;base64,"
        val queryParams = HashMap<String, String>()
        queryParams.put("field","image")
        queryParams.put("value", "${str}${imageString}")
        DLog("ImageString","${str}${imageString}")
        networkCall?.APIRequest("api/v1/profile1", queryParams, UserProfileDisplayResult::class.java, this, 5, "Fetching...")
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

                Log.e("API CALL : ", "inside CountryResult API CALL and onSuccess when")
                val countryList = response as CountryResult
                view.setCountries(countryList.country)
            }
            2 -> { // Get State
                Log.e("API CALL : ", "inside StateResult API CALL and onSuccess when")
                val stateList = response as StateResult
                view.setStates(stateList.state)
            }
            3 -> { // Get District
                Log.e("API CALL : ", "inside DistrictResult API CALL and onSuccess when")
                val districtList = response as DistrictResult
                view.setDistricts(districtList.districts)
            }
            4 -> {
                val result = response as UserProfileDisplayResult
                Log.e("API CALL : ", "inside UserProfileDisplayResult API CALL and onSuccess when")
                if (true) {
                    setStringPreference(context,"image_url","http://192.168.0.56/images/")
                    var image_url= getStringPreference(context,"image_url","http://192.168.0.56/images/")
                    view.setProfileDetails("${image_url}${result.image_url}", "${result.first_name} ${result.last_name}",result.date_of_birth,result.weight,"${result.street_name},${result.locality},${result.city},${result.district},${result.state},${result.country}",result.phone_number, result.email)
                    CustomToast().normalToast(context,"${result.message}")
                    //val imageView : ImageView = ImageView(this)
                    var imageUrl = result.image_url
                    //Picasso.with().load(imageUrl).into(fragment_profile_display_user_profile_image)

                    //Picasso.with(this).load(imageUrl).into(fragment_profile_display_user_profile_image)
                    //srcBitmap = BitmapFactory.

                    //srcBitmap = (imageView.drawable as BitmapDrawable).bitmap
                    // srcBitmap = BitmapFactory.decodeFile("https://130513-375933-1-raikfcquaxqncofqfm.stackpathdns.com/wp-content/uploads/2016/10/Shreya-Ghoshal-2.jpg")
                    //setCircle()


                    //var address = "${result.street_name},${result.locality},${result.city},${result.district},${result.state}"


                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                }

            }
            5 -> {
                Log.e("API CALL : ", "inside sendImageString  API CALL and onSuccess when")
            }
            6 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val addressResult = response as SendAddressResult
                if (addressResult.status) {
                    if(true){
                       /* trans?.replace(R.id.activity_user_details_frame_layout, UserAdditionalDetailsFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()*/
                    }
                    Toast.makeText(context, "Successfully Stored", Toast.LENGTH_LONG).show()
                    //val i=Intent(applicationContext,UserAdditionalDetails::class.java)
                    //startActivity(i)
                }
            }
        }
    }
}