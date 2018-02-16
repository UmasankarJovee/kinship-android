package com.joveeinfotech.kinship.presenter

import android.R
import android.app.Activity
import android.support.v4.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.Home
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.LoginResult
import com.joveeinfotech.kinship.model.UserDetailResult
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.SharedData
import com.joveeinfotech.kinship.utils.Validation
import com.joveeinfotech.kinship.view.*
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserDetailsPresenterImpl : APIListener, UserDetailsPresenter {

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private lateinit var userDetailsView : UserDetailsView

    lateinit var mContext: Context
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
        queryParams.put("fgf", "jhuy")
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/userdetail",queryParams, UserDetailResult::class.java,this, 1, "Authenticating...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val userDetailResult = response as UserDetailResult
                Log.e("API CALL : ", "inside UserDetails activity and onSuccess")

                userDetailsView.setNavigationFragmentValues(userDetailResult.isRegisterUserProfile,
                        userDetailResult.isRegisterHomeAddress,userDetailResult.isRegisterAdditionalDetails)

                if(!userDetailResult.isRegisterUserProfile){
                    trans?.replace(com.joveeinfotech.kinship.R.id.user_details_frame_layout, UserProfileFragment.newInstance())
                    trans?.commit()
                }else if(!userDetailResult.isRegisterHomeAddress){
                    trans?.replace(com.joveeinfotech.kinship.R.id.user_details_frame_layout, UserAddressFragment.newInstance())
                    trans?.commit()
                }else if(!userDetailResult.isRegisterAdditionalDetails){
                    trans?.replace(com.joveeinfotech.kinship.R.id.user_details_frame_layout, UserAdditionalDetailsFragment.newInstance())
                    trans?.commit()
                }
            }
        }
    }
}