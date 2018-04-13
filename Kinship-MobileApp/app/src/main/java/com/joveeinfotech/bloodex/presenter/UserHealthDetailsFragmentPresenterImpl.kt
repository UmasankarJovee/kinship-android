package com.joveeinfotech.bloodex.presenter

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.APIClient
import com.joveeinfotech.bloodex.APIListener
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.model.UserHealthDetailsResult
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.view.UserAddressFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
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

    override fun sendHealthDetails(healthDetails: JsonObject) {
        /*var user_id = SharedPreferenceHelper.getStringPreference(mContext, "user_id", "56")
        val queryParams = HashMap<String, String>()
        //queryParams.put("user_id", user_id!!)
        //val jsArray = JSONArray(healthDetails)
        //val Gson=Gson()
        queryParams.put("user_id","161")
        queryParams.put("diseases", healthDetails.toString())
        DLog("Result","${healthDetails.toString()}")
        Log.e("MAIN ACTIVITY : ", "inside button")
        networkCall?.APIRequest("api/v1/health", queryParams, UserHealthDetailsResult::class.java, this, 1, "Your Details are storing...")*/
        DLog("result",healthDetails.toString())
        sendData(healthDetails)

    }

    private fun sendData(toString: JsonObject) {
        val retrofitInterface = APIClient.getClient(mContext)
       /* val noneobj = JsonObject()
        try {
            noneobj.addProperty("disease_id", "11")
            noneobj.addProperty("disease_time", "2017-08-08")
            noneobj.addProperty("status", "0")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        var jarray = JsonArray()
        jarray.add(noneobj)
        val healthDetailsObj = JsonObject()
        //healthDetailsObj.remove("diseases")
        healthDetailsObj.add("diseases", jarray)*/
        retrofitInterface?.postJson(toString)?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.subscribe(
                        { result ->
                            //progressDialog?.dismiss()
                            //if(result.status)
                            DLog("Response Code : ",result.code().toString())
                            DLog("HealthPresenter :",result.errorBody()!!.string())
                           /* DLog("HealthPresenter :","success")
                            CustomToast().normalToast(mContext,"Uploaded Successfully")*/
                        },
                        { error ->
                            DLog("HealthPresenter :",error.message.toString())
                            //progressDialog?.dismiss()
                        }
                )
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