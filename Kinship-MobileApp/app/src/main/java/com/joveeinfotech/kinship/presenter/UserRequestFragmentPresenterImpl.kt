package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.*
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.view.UserAdditionalDetailsFragment
import kinship.joveeinfotech.kinship.HomeFragment
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
        //loadDistricts()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun loadDistricts() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "country")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search_district", queryParams, DistrictResult::class.java, this, 1, "Fetching...")
    }

    override fun sendDistrictsReceiveHospitals(district: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "district")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search_hospitals", queryParams, SearchHospitalResult::class.java, this, 2, "Fetching...")
    }

    override fun sendUserRequestDetails(search_blood_group: String, search_units: String, search_district: String, search_hospital: String) {
        if(!search_blood_group.trim().isEmpty() && !search_units.trim().isEmpty()
                && !search_district.trim().isEmpty() && !search_hospital.trim().isEmpty()){
                    sendUserRequestToServer(search_blood_group,search_units,search_district,search_hospital)
        } else {
            CustomToast().alertToast(mContext,"Fill the all fields")
        }
    }

    override fun sendUserRequestToServer(search_blood_group: String, search_units: String, search_district: String, search_hospital: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("blood_group", search_blood_group)
        queryParams.put("units",search_units)
        queryParams.put("district",search_district)
        queryParams.put("hospital",search_hospital)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search_blood_group", queryParams, SearchHospitalResult::class.java, this, 3, "Sending Your Request...")
    }

    fun sendUserRequestToServer1() {
        val queryParams = HashMap<String, String>()
        queryParams.put("blood_group", "AB+")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, ImageUpload::class.java, this, 4, "Sending Your Request...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Get Districts
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val districtsList = response as DistrictResult
                userRequestFragmentView.setDistricts(districtsList)
            }
            2 -> { // Get Hospitals
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val hospitalsList = response as SearchHospitalResult
                userRequestFragmentView.setHospitals(hospitalsList)
            }
            3 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val searchResult = response as SearchBloodInUserRequest
                if (searchResult.status) {
                        trans?.replace(R.id.activity_user_details_frame_layout, HomeFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()
                    CustomToast().normalToast(mContext,"Your Request has been send")
                    //Toast.makeText(mContext, "Successfully Stored", Toast.LENGTH_LONG).show()
                    //val i=Intent(applicationContext,UserAdditionalDetails::class.java)
                    //startActivity(i)
                }
            }
            4 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val searchResult = response as ImageUpload
                if (searchResult.status) {
                    CustomToast().normalToast(mContext,"Your request has been send")
                }
            }
        }
    }
}