package com.joveeinfotech.bloodex

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.joveeinfotech.bloodex.adapter.RequestHistoryListAdapter
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.presenter.RequestHistoryPresenterImpl
import kotlinx.android.synthetic.main.activity_request_history.*

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
