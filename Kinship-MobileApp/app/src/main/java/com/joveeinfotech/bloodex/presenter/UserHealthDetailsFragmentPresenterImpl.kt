package com.joveeinfotech.bloodex.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import android.util.Log
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.model.UserHealthDetailsResult
import com.joveeinfotech.bloodex.utils.Others.DLog
import java.util.*
import org.json.JSONObject


class UserHealthDetailsFragmentPresenterImpl : APIListener, UserHealthDetailsFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private var userHealthDetailsFragmentView : UserHealthDetailsFragmentView? = null

    var mContext: Context
    var networkCall : APICall? = null
    var trans : FragmentTransaction? = null

    constructor(trans: FragmentTransaction?, view: UserHealthDetailsFragmentView, context: Context){
        this.trans=trans
        this.mContext=context
        userHealthDetailsFragmentView=view
        initPresenter()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun sendHealthDetails(healthDetails: JSONObject) {
        var user_id = SharedPreferenceHelper.getStringPreference(mContext, "user_id", "56")
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        //queryParams.put("user_id", user_id!!)
        //val jsArray = JSONArray(healthDetails)
        //val Gson=Gson()
        queryParams.put("user_id","161")
        queryParams.put("diseases", healthDetails.toString())
        DLog("Result","${healthDetails.toString()}")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/health", queryParams, UserHealthDetailsResult::class.java, this, 1, "Your Details are storing...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User profile
                val result = response as UserHealthDetailsResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (result.status){
                    userHealthDetailsFragmentView?.navigateFragment()
                    /*if(true){
                        trans?.replace(R.id.activity_user_details_frame_layout, UserAddressFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()
                    }*/
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {

                }
            }
        }
    }
}