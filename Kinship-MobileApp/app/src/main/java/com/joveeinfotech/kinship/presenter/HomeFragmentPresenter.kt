package com.joveeinfotech.kinship.presenter

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.APIListener
import com.joveeinfotech.kinship.contract.KinshipContract.HomeFragmentView
import com.joveeinfotech.kinship.contract.KinshipContract.HomeFragmentPresenterPresenter
import com.joveeinfotech.kinship.model.UpdateDetailsResult
import io.reactivex.disposables.Disposable

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class HomeFragmentPresenter: APIListener,HomeFragmentPresenterPresenter {

    override fun onFailure(from: Int, t: Throwable) {}

    override fun onNetworkFailure(from: Int) {}

    private lateinit var homeFragmentView: HomeFragmentView

    lateinit var mContext:Context
    var networkCall : APICall? = null

    constructor(view: HomeFragmentView,context: Context){
        this.mContext=context
        homeFragmentView=view
        initPresenter()
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
        Click()
    }

    override fun Click() {
        val queryParams = HashMap<String, String>()
        queryParams.put("nothing", "hello")
        //Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v0/updated_detail_of_home",queryParams, UpdateDetailsResult::class.java,this, 1, "Fetching...")
    }
    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // Home Page Contents
                val result = response as UpdateDetailsResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    homeFragmentView.setViewData(result.count_of_hospitals,result.count_of_donors,result.count_of_users)
                    //Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(this,)
                    //snackbar(this.findViewById(android.R.id.content), "Please wait some minutes")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }
}