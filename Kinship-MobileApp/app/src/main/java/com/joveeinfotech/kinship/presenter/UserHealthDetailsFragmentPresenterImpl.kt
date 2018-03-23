package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper
import com.joveeinfotech.kinship.model.UserHealthDetailsResult
import com.joveeinfotech.kinship.model.UserProfileResult
import com.joveeinfotech.kinship.view.UserAddressFragment
import java.util.HashMap

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

    override fun sendHealthDetails(healthDetails: StringBuffer?) {
        var user_id = SharedPreferenceHelper.getStringPreference(mContext, "user_id", "56")
        val queryParams = HashMap<String, String>()
        //queryParams.put("user_id", user_id!!)
        queryParams.put("disease", "Jaundice")
        queryParams.put("disease_time","2017-01-01")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/health", queryParams, UserHealthDetailsResult::class.java, this, 1, "Your Details are storing...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User profile
                val result = response as UserHealthDetailsResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status){
                    if(true){
                        trans?.replace(R.id.activity_user_details_frame_layout, UserAddressFragment.newInstance())
                        trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
                        trans?.commit()
                    }
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {

                }
            }
        }
    }
}