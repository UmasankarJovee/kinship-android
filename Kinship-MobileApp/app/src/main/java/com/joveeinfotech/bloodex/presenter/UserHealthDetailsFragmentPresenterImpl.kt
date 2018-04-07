package com.joveeinfotech.bloodex.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.model.UserHealthDetailsResult
import com.joveeinfotech.bloodex.view.UserAddressFragment
import java.util.*

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

    override fun sendHealthDetails(healthDetails: Array<Array<String>?>) {
        var user_id = SharedPreferenceHelper.getStringPreference(mContext, "user_id", "56")
        val queryParams = HashMap<String, String>()
        //queryParams.put("user_id", user_id!!)
        queryParams.put("disease", Arrays.deepToString(healthDetails))
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