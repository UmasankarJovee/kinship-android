package com.joveeinfotech.bloodex.presenter

import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentTransaction
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.model.*
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.view.RequestResponse
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserRequestFragmentPresenterImpl : APIListener, UserRequestFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private lateinit var userRequestFragmentView : UserRequestFragmentView

    lateinit var mContext: Context
    var networkCall : APICall? = null
    var trans : FragmentTransaction? = null

    constructor( view: UserRequestFragmentView, context: Context){
        this.trans=trans
        this.mContext=context
        userRequestFragmentView=view
        initPresenter()
        loadDistricts()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun loadDistricts() {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("input", "districts")
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, DistrictResult::class.java, this, 1, "Fetching...")
    }

    override fun loadHospitals() {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("input", "hospitals")
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, SearchHospitalResult::class.java, this, 2, "Fetching...")
    }

    /*override fun sendUserRequestDetails(search_blood_group: String, search_units: String, search_district: String, search_hospital: String) {
        if(!search_blood_group.trim().isEmpty() && !search_units.trim().isEmpty()
                && !search_district.trim().isEmpty() && !search_hospital.trim().isEmpty()){
                    sendUserRequestToServer(search_blood_group,search_units,search_district,search_hospital)
        } else {
            CustomToast().alertToast(mContext,"Fill the all fields")
        }
    }*/

   /* override fun sendUserRequestToServer(search_blood_group: String, search_units: String, search_district: String, search_hospital: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("blood_group", search_blood_group)
        queryParams.put("units",search_units)
        queryParams.put("district",search_district)
        queryParams.put("hospital",search_hospital)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search_blood_group", queryParams, SearchHospitalResult::class.java, this, 3, "Sending Your Request...")
    }*/

    override fun sendUserRequestToServer(search_blood_group: String, units: String, district: String, hospital: String, time_in_number: String, time_in_string: String) {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("input", "bloodRequest")
        queryParams.put("blood_group", search_blood_group)
        queryParams.put("units",units)
        queryParams.put("district",district)
        queryParams.put("hospital",hospital)
        queryParams.put("time_in_number",time_in_number)
        queryParams.put("time_in_string",time_in_string)
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, SearchBloodInUserRequest::class.java, this, 3, "Sending Your Request...")
    }

    fun sendUserRequestToServer1() {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("blood_group", "AB+")
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, ImageUpload::class.java, this, 4, "Sending Your Request...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Get Districts
                DLog("API CALL : ", "inside UserRequest and onSuccess when")
                val districtsList = response as DistrictResult
                DLog("User Request : ","${districtsList}")
                userRequestFragmentView.setDistricts(districtsList)
            }
            2 -> { // Get Hospitals
                DLog("API CALL : ", "inside Main activity and onSuccess when")
                val hospitalsList = response as SearchHospitalResult
                userRequestFragmentView.setHospitals(hospitalsList)
            }
            3 -> { // Send Address
                DLog("API CALL : ", "inside Main activity and onSuccess when")
                val searchResult = response as SearchBloodInUserRequest
                if (searchResult.status) {
                        /*trans?.replace(R.id.activity_user_details_frame_layout, HomeFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()*/
                    mContext.startActivity(Intent(mContext,RequestResponse::class.java))
                    userRequestFragmentView.closeActivity()
                    CustomToast().normalToast(mContext,mContext.getString(R.string.your_request_has_been_send))
                    //Toast.makeText(mContext, "Successfully Stored", Toast.LENGTH_LONG).show()
                    //val i=Intent(applicationContext,UserAdditionalDetails::class.java)
                    //startActivity(i)
                }else{
                    CustomToast().normalToast(mContext,mContext.getString(R.string.try_again))
                }
            }
            /* 4 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val searchResult = response as ImageUpload
                if (searchResult.status) {
                    CustomToast().normalToast(mContext,"Your request has been send")
                }
            }*/
        }
    }
}