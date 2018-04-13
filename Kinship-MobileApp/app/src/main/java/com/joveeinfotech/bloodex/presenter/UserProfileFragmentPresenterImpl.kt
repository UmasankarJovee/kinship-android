package com.joveeinfotech.bloodex.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.model.UserProfileResult
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.view.UserHealthDetailsFragment
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserProfileFragmentPresenterImpl : APIListener, UserProfileFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private var userProfileFragmentView : UserProfileFragmentView

    var mContext: Context
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

    override fun  userProfileDetails(imageString : String, first_name: String, last_name: String, date_of_birth: String, weight: Int, gender: Int) {
        if (imageString.trim().isNotEmpty() && first_name.trim().isNotEmpty() && last_name.trim().isNotEmpty()
                && date_of_birth.trim().isNotEmpty() && (gender == 1 || gender == 2)
                && weight != null) {
            sendUserProfile(imageString,first_name, last_name, date_of_birth, weight, gender)
        } else {
            //showDialog(2) // Please fill the all details
            CustomToast().alertToast(mContext,mContext.getString(R.string.fill_all_the_fields))
        }
    }

    private fun sendUserProfile(imageString: String, first_name: String, last_name: String, date_of_birth: String, weight: Int, gender: Int) {
        //var user_id = getStringPreference(mContext,"user_id","56")

        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        //queryParams.put("user_id", "168")
        queryParams.put("imageString",imageString)
        queryParams.put("first_name", first_name)
        queryParams.put("last_name", last_name)
        queryParams.put("date_of_birth", date_of_birth)
        queryParams.put("weight",weight.toString())
        queryParams.put("gender", gender.toString())
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/persons", queryParams, UserProfileResult::class.java, this, 1, "Your Details are storing...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // User profile
                val result = response as UserProfileResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (result.status){
                    userProfileFragmentView.navigateFragment()
                    /*if(true){
                        trans?.replace(R.id.activity_user_details_frame_layout, UserHealthDetailsFragment.newInstance())
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