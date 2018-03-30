package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.DistrictResult
import com.joveeinfotech.kinship.model.ImageUpload
import com.joveeinfotech.kinship.model.SearchBloodInUserRequest
import com.joveeinfotech.kinship.model.SearchHospitalResult
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.view.RequestResponse
import kinship.joveeinfotech.kinship.HomeFragment
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class SomeOneRequestFragmentPresenterImpl : APIListener, SomeOneRequestFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private lateinit var someOneRequestFragmentView : SomeOneRequestFragmentView

    lateinit var mContext: Context
    var networkCall : APICall? = null
    var trans : FragmentTransaction? = null

    constructor(view: SomeOneRequestFragmentView, context: Context?){
        this.trans=trans
        this.mContext=context!!
        someOneRequestFragmentView=view
        initPresenter()
        loadDistricts()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun loadDistricts() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "districts")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, DistrictResult::class.java, this, 1, "Fetching...")
    }

    override fun loadHospitals() {
        val queryParams = HashMap<String, String>()
        queryParams.put("input", "hospitals")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, SearchHospitalResult::class.java, this, 1, "Fetching...")
    }

    /* override fun sendUserRequestDetails(name: String, phone_number: String, search_blood_group: String, search_units: String, search_district: String, search_hospital: String, relationship: String) {
        if(!name.trim().isEmpty() && !phone_number.trim().isEmpty() && !search_blood_group.trim().isEmpty()
                && !search_units.trim().isEmpty() && !search_district.trim().isEmpty()
                && !search_hospital.trim().isEmpty() && !relationship.trim().isEmpty()){
                    sendUserRequestToServer(name,phone_number,search_blood_group,search_units,search_district,search_hospital,relationship)
        } else {
            CustomToast().alertToast(mContext,"Fill the all fields")
        }
    }*/

    override fun sendUserRequestToServer(name: String, phone_number: String, search_blood_group: String, units: String, district: String, hospital: String, relationship: String, time_in_number: String, time_in_string: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("name",name)
        queryParams.put("phone_number",phone_number)
        queryParams.put("blood_group", search_blood_group)
        queryParams.put("units",units)
        queryParams.put("district",district)
        queryParams.put("hospital",hospital)
        queryParams.put("relationship",relationship)
        queryParams.put("time_in_number",time_in_number)
        queryParams.put("time_in_string",time_in_string)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search_blood_group", queryParams, SearchHospitalResult::class.java, this, 3, "Sending Your Request...")
    }


   /* override fun sendUserRequestToServer(name: String, phone_number : String, search_blood_group: String, search_units: String, search_district: String, search_hospital: String,relationship: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("name",name)
        queryParams.put("phone_number",phone_number)
        queryParams.put("blood_group", search_blood_group)
        queryParams.put("units",search_units)
        queryParams.put("district",search_district)
        queryParams.put("hospital",search_hospital)
        queryParams.put("relationship",relationship)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search_blood_group", queryParams, SearchHospitalResult::class.java, this, 3, "Sending Your Request...")
    }*/

    fun sendUserRequestToServer1() {
        val queryParams = HashMap<String, String>()
        queryParams.put("blood_group", "AB+")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/search", queryParams, ImageUpload::class.java, this, 3, "Sending Your Request...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Get Districts
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val districtsList = response as DistrictResult
                someOneRequestFragmentView.setDistricts(districtsList)
            }
            2 -> { // Get Hospitals
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val hospitalsList = response as SearchHospitalResult
                someOneRequestFragmentView.setHospitals(hospitalsList)
            }
            3 -> { // Send Address
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val searchResult = response as SearchBloodInUserRequest
                if (searchResult.status) {
                        /*trans?.replace(R.id.activity_user_details_frame_layout, HomeFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()*/
                    mContext.startActivity(Intent(mContext, RequestResponse::class.java))
                    someOneRequestFragmentView.closeActivity()
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