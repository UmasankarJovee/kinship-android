package com.joveeinfotech.bloodex.presenter

import android.support.v4.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.Home
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.model.UserDetailResult
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.view.*
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserDetailsPresenterImpl : APIListener, UserDetailsPresenter {

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private lateinit var userDetailsView : UserDetailsView

    var mContext: Context
    var networkCall : APICall? = null

    var phone_number : String? = null

    var trans : FragmentTransaction? = null

    constructor(trans : FragmentTransaction?, view: UserDetailsView, context: Context){
        this.trans = trans
        userDetailsView=view
        mContext=context
        initPresenter()
        onLoad()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun onLoad() {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("fgf", "jhuy")
        DLog("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/userdetail",queryParams, UserDetailResult::class.java,this, 1, "Authenticating...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val userDetailResult = response as UserDetailResult
                DLog("API CALL : ", "inside UserDetails activity and onSuccess")

                userDetailsView.setNavigationFragmentValues(userDetailResult.isRegisterUserProfile,
                        userDetailResult.isRegisterHomeAddress,userDetailResult.isRegisterAdditionalDetails,
                        userDetailResult.isRegisterHealthDetails)

                if(!userDetailResult.isRegisterUserProfile){
                    trans?.replace(com.joveeinfotech.bloodex.R.id.activity_user_details_frame_layout, UserProfileFragment.newInstance())
                    trans?.commit()
                }else if(!userDetailResult.isRegisterHomeAddress){
                    trans?.replace(com.joveeinfotech.bloodex.R.id.activity_user_details_frame_layout, UserAddressFragment.newInstance())
                    trans?.commit()
                }else if(!userDetailResult.isRegisterHealthDetails){
                    trans?.replace(com.joveeinfotech.bloodex.R.id.activity_user_details_frame_layout, UserHealthDetailsFragment.newInstance())
                    trans?.commit()
                }else if(!userDetailResult.isRegisterAdditionalDetails){
                    trans?.replace(com.joveeinfotech.bloodex.R.id.activity_user_details_frame_layout, UserAdditionalDetailsFragment.newInstance())
                    trans?.commit()
                }else{
                    mContext.startActivity(Intent(mContext, Home::class.java))
                    userDetailsView.closeActivity()
                }
            }
        }
    }
}