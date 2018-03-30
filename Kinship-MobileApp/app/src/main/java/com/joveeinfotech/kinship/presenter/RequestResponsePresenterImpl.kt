package com.joveeinfotech.kinship.presenter

import android.R
import android.app.Activity
import android.content.Context
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.adapter.RequestResponseListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.*
import com.joveeinfotech.kinship.utils.SharedData
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
        queryParams.put("district", "Madurai")
        networkCall?.APIRequest("api/v1/donorList", queryParams, RequestResponseResult::class.java, this, 1, "Sending your other details...")
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun isDonated(booleanValue: Boolean) {
        val queryParams = HashMap<String, String>()
        queryParams.put("district", "Madurai")
        networkCall?.APIRequest("api/v1/", queryParams, SendIsDonatedResult::class.java, this, 1, "Sending your other details...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val result = response as RequestResponseResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (true) {
                    var details = result.donorList
                    //var details = result.donors
                    //user_id = details.user_id
                    mRequestResponseArrayList = ArrayList(details)
                    requestResponseListAdapter = RequestResponseListAdapter(mRequestResponseArrayList!!, requestResponseListener!!, mContext!!)
                    requestResponseView?.setRequestResponse(requestResponseListAdapter)

                    //requestResponseView?.setRequestResponse(result.details)
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
            2 -> {
                val result = response as SendIsDonatedResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    requestResponseView?.closeConfirmDialog()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
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