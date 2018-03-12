package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.adapter.DonorsListAdapter
import com.joveeinfotech.kinship.adapter.RequestHistoryListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.*
import com.joveeinfotech.kinship.utils.Others.DLog
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/3/18.
 */
class RequestHistoryPresenterImpl(var requestHistoryPresnterView:RequestHistoryView,var rhContext: Context):APIListener,RequestHistoryPresenter {
    var networkCall : APICall? = null
    private var requestHistoryArrayList: ArrayList<requestorList>? = null

    var requestHistoryListAdapter: RequestHistoryListAdapter? = null
    init {
        initPresenter()
    }

    override fun initPresenter() {
        networkCall = APICall(rhContext)
        loadRequestHisoryList()
    }

    override fun loadRequestHisoryList() {
        val queryParams = HashMap<String, String>()
        queryParams.put("getRequestHistory", "getRequestHistory")
        networkCall?.APIRequest("api/v1/requestorList", queryParams, RequestHistoryResult::class.java, this, 1, "Sending your other details...")
    }

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    override fun onSuccess(from: Int, response: Any) {
        var mMap = mutableMapOf<String,MutableList<requestInnerDetails>>()
        //var mSet = mutableSetOf<String,MutableSet<donationInnerDetails>>()
        //var mList = listOf<String>()
        //var mList: MutableList<donationInnerDetails>? = null
        when (from) {
            1 -> { // Send Additional Details
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val getRequestHistoryresult = response as RequestHistoryResult
                var details: List<requestorList> = getRequestHistoryresult.requestorList
                requestHistoryArrayList = ArrayList(details)

                DLog("Shanmugam : ", "RequestHistory1")
                var len = requestHistoryArrayList!!.size
                Log.e("DonorsFragmentPresent: ","$len")
                var i=0
                for(i in requestHistoryArrayList!!){
                    if(i.date in mMap.keys){

                        var mList1: MutableList<requestInnerDetails>? = mMap[i.date]
                        //var df = donationInnerDetails(i.image_url,i.name,i.district)
                        //mList?.add(i.image_url,donorsResult.name,donorsResult.district)
                        Log.e("DonorsList : ","inside if")
                        //mList1?.add(donationInnerDetails(i.image_url,i.name,i.district))
                        mMap[i.date]?.add(requestInnerDetails(i.image_url,i.name,i.hospital_name,i.district))
                        Log.e("dgdgfs","${i.date} ${i.image_url} ${i.name} ${i.hospital_name} ${i.district}")
                        //mMap.put(i.date,mList1!!)
                        //mMap.replace(i.date,mList1)
                    }else{
                        Log.e("DonorsList : ","inside else")
                        var mList2 = mutableListOf<requestInnerDetails>()
                        mList2.add(requestInnerDetails(i.image_url,i.name,i.hospital_name,i.district))
                        Log.e("dgdgfs","${i.date} ${i.image_url} ${i.name} ${i.hospital_name} ${i.district}")
                        mMap.put(i.date,mList2)
                    }
                }
                DLog("Shanmugam : ", "RequestHistory2")
                requestHistoryListAdapter = RequestHistoryListAdapter(mMap,rhContext!!)
                DLog("Shanmugam : ", "RequestHistory3")
                //Log.e("")
                requestHistoryPresnterView?.setAdaperofRequestHistory(requestHistoryListAdapter)
                DLog("Shanmugam : ", "RequestHistory4")

                /* for(key in mMap){
                     donorsListAdapter
                 }*/

            }
        }
    }
}