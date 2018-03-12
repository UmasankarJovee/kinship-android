package com.joveeinfotech.kinship

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.joveeinfotech.kinship.adapter.RequestHistoryListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.RequestHistoryPresenterImpl
import kotlinx.android.synthetic.main.activity_request_history.*
import kotlinx.android.synthetic.main.fragment_donars.view.*

class RequestHistory : AppCompatActivity(),RequestHistoryView {

    var rHContext:Context?=null
    var requestHistoryPresenterImpl:RequestHistoryPresenterImpl?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_history)
        requestHistoryPresenterImpl= RequestHistoryPresenterImpl(this,this)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(rHContext)
        activity_request_history_recyclerView.layoutManager = layoutManager

    }

    override fun setAdaperofRequestHistory(requestHistoryListAdapter:RequestHistoryListAdapter?) {
        activity_request_history_recyclerView.adapter=requestHistoryListAdapter
    }
}
