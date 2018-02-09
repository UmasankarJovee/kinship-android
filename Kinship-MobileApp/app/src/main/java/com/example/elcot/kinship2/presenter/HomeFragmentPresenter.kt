package com.example.elcot.kinship2.presenter

import android.app.ProgressDialog
import android.widget.ProgressBar
import com.example.elcot.kinship2.ApiInterface
import com.example.elcot.kinship2.contract.KinshipContract
import com.example.elcot.kinship2.contract.KinshipContract.HomeFragmentPresenterPresenter
import com.example.elcot.kinship2.contract.KinshipContract.HomeFragmentView
import io.reactivex.disposables.Disposable

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class HomeFragmentPresenter: HomeFragmentPresenterPresenter {


    private lateinit var homeFragmentView: HomeFragmentView
    //private lateinit var model:
    var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable : Disposable? = null
    val progressBar : ProgressBar? = null
    var progressDialog: ProgressDialog? = null

    constructor(view: HomeFragmentView){
        homeFragmentView=view
        initPresenter()
    }

    override fun initPresenter() {
        homeFragmentView.updatedDetails()
    }
}