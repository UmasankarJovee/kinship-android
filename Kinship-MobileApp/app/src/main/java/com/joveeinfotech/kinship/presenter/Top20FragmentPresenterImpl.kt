package com.joveeinfotech.kinship.presenter

import android.content.Context
import android.util.Log
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.R.id.fragment_top20_RecyclerView
import com.joveeinfotech.kinship.adapter.Top20ListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.GetTop20Result
import com.joveeinfotech.kinship.utils.SharedData
import java.util.HashMap

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class Top20FragmentPresenterImpl : APIListener, Top20FragmentPresenter {

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private var top20FragmentView : Top20FragmentView? = null

    var mContext: Context
    var networkCall : APICall? = null

    var phone_number : String? = null

    private var mGetTop20ArrayList: ArrayList<GetTop20Result>? = null

    var top20ListAdapter : Top20ListAdapter? = null

    constructor(view: Top20FragmentView, context: Context){
        top20FragmentView=view
        mContext=context
        initPresenter()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
        loadTop20()
    }

    override fun loadTop20() {
        val queryParams = HashMap<String, String>()
        queryParams.put("getTop20", "getTop20")
        networkCall?.APIRequest("api/v1/getTop20", queryParams, GetTop20Result::class.java, this, 1, "Sending your other details...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Send Additional Details
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val getTop20Result = response as List<GetTop20Result>
                mGetTop20ArrayList = ArrayList(getTop20Result)
                top20ListAdapter = Top20ListAdapter(mGetTop20ArrayList!!, mContext!!)
                top20FragmentView?.setAdapterOfTop20(top20ListAdapter)

            }
        }
    }
}