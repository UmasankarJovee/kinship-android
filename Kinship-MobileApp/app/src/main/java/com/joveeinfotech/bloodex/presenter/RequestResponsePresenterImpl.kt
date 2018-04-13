package com.joveeinfotech.bloodex.presenter

import android.R
import android.app.Activity
import android.content.Context
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.adapter.RequestResponseListAdapter
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.model.*
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.utils.SharedData
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class RequestResponsePresenterImpl : APIListener, RequestResponsePresenter {

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private var requestResponseView : RequestResponseView? = null
    var session: SharedData? = null

    var mContext: Context
    var networkCall : APICall? = null

    var phone_number : String? = null
    var password : String? = null
    var user_id : String? = null

    var requestResponseListener : RequestResponseListener? = null

    private var mRequestResponseArrayList : ArrayList<InnerRequestResponseResult>? = null

    var requestResponseListAdapter : RequestResponseListAdapter? = null

    constructor(view : RequestResponseView, context: Context, listener: RequestResponseListener){
        requestResponseView = view
        mContext=context
        requestResponseListener = listener
        initPresenter()
        loadList()
    }

    private fun loadList() {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("user_id", "161")
        networkCall?.APIRequest("api/v1/donorList", queryParams, RequestResponseResult::class.java, this, 1, "Sending your other details...")
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun isDonated(person_id : String) {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        //queryParams.put("user_id", "161")
        networkCall?.APIRequest("api/v1/donorList", queryParams, SendIsDonatedResult::class.java, this, 2, "Sending your other details...")
    }

    override fun SendNoOneDonated() {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("district", "Madurai")
        networkCall?.APIRequest("api/v1/", queryParams, SendNoOneDonatedResult::class.java, this, 3, "Sending your other details...")
    }

    override fun clearRequestResponse(user_id: String?) {
        val queryParams = HashMap<String, String>()
        var access_token = getStringPreference(mContext, "access_token", "")
        queryParams.put("access_token", access_token!!)
        queryParams.put("user_id", user_id!!)
        networkCall?.APIRequest("api/v1/clearRequest", queryParams, ClearRequestResponseResult::class.java, this, 4, "Sending your other details...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val result = response as RequestResponseResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (true) {
                    var details = result.donorList
                    //var details = result.donors
                    //user_id = details.user_id
                    mRequestResponseArrayList = ArrayList(details)
                    requestResponseListAdapter = RequestResponseListAdapter(mRequestResponseArrayList!!, requestResponseListener!!, mContext!!)
                    requestResponseView?.setRequestResponse(requestResponseListAdapter)

                    //requestResponseView?.setRequestResponse(result.details)
                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            2 -> {
                val result = response as SendIsDonatedResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    requestResponseView?.closeConfirmDialog()

                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            3 -> {
                val result = response as SendNoOneDonatedResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    requestResponseView?.closeNotDonatedConfirmDialog()

                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            4 -> {
                val result = response as ClearRequestResponseResult
                DLog("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    requestResponseView?.closeActivity()

                    DLog("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }
}