package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.view.UserAddressFragment
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.UserProfileResult
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserProfileFragmentPresenterImpl : APIListener, UserProfileFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private lateinit var userProfileFragmentView : UserProfileFragmentView

    lateinit var mContext: Context
    var networkCall : APICall? = null
    var trans : FragmentTransaction? = null

    constructor(trans: FragmentTransaction?,view: UserProfileFragmentView, context: Context){
        this.trans=trans
        this.mContext=context
        userProfileFragmentView=view
        initPresenter()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun userProfileDetails(first_name: String, last_name: String, date_of_birth: String, gender: Int) {
        if (first_name.trim().isNotEmpty() && last_name.trim().isNotEmpty() && date_of_birth.trim().isNotEmpty() && (gender == 1 || gender == 2)) {
            sendUserProfile(first_name, last_name, date_of_birth, gender)
        } else {
            //showDialog(2) // Please fill the all details
        }
    }

    private fun sendUserProfile(first_name: String, last_name: String, date_of_birth: String, gender: Int) {
        val queryParams = HashMap<String, String>()
        queryParams.put("user_id", "81")
        queryParams.put("first_name", first_name)
        queryParams.put("last_name", last_name)
        queryParams.put("date_of_birth", date_of_birth)
        queryParams.put("gender", gender.toString())
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/persons", queryParams, UserProfileResult::class.java, this, 1, "Your Details are storing...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User profile
                val result = response as UserProfileResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    if(true){
                        trans?.replace(R.id.user_details_frame_layout, UserAddressFragment.newInstance())
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