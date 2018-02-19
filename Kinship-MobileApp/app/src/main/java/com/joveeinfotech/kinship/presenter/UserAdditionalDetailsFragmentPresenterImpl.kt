package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.Home
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.R.id.editText_additional_phone_number
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.UserAdditionalDetailsResult
import com.joveeinfotech.kinship.view.UserAddressFragment
import kotlinx.android.synthetic.main.fragment_user_additional_details.*
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class UserAdditionalDetailsFragmentPresenterImpl : APIListener, UserAdditionalDetailsFragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private var userAdditionalDetailsFragmentView : UserAdditionalDetailsFragmentView

    var mContext: Context
    var networkCall : APICall? = null
    var trans : FragmentTransaction? = null

    var category_of_person : String? = null
    var additionalPhoneNumber : String? = null
    var additionalEmail : String? = null
    var socialProfile : String? = null

    constructor(trans: FragmentTransaction?, view: UserAdditionalDetailsFragmentView, context: Context){
        this.trans=trans
        this.mContext=context
        userAdditionalDetailsFragmentView=view
        initPresenter()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun userAdditionalDetails(category_of_person1: String, additionalPhoneNumber1: String, additionalEmail1: String, socialProfile1: String) {
        category_of_person = category_of_person1
        if(additionalPhoneNumber1.trim().isEmpty()){
            additionalPhoneNumber = "empty"
        }
        if(additionalEmail1.trim().isEmpty()){
            additionalEmail = "empty"
        }
        if(socialProfile1.trim().isEmpty()){
            socialProfile = "empty"
        }
        sendAdditionalDetails()
    }
    override fun moveSkiptoHome() {
        val i = Intent(mContext, Home::class.java)
        mContext.startActivity(i)
    }
    private fun sendAdditionalDetails() {
        val queryParams = HashMap<String, String>()
        queryParams.put("occupation", category_of_person!!)
        queryParams.put("phone_number", additionalPhoneNumber!!)
        queryParams.put("address", additionalEmail!!)
        queryParams.put("social_profile",socialProfile!!)
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/optional", queryParams, UserAdditionalDetailsResult::class.java, this, 1, "Sending your other details...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Send Additional Details
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val userAdditionalDetailsResult = response as UserAdditionalDetailsResult
                if(userAdditionalDetailsResult.status){
                    val i= Intent(mContext, Home::class.java)
                    mContext.startActivity(i)
                }
            }
        }
    }
}