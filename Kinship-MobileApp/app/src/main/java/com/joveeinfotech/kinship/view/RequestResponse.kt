package com.joveeinfotech.kinship.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.adapter.RequestResponseListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.InnerRequestResponseResult
import com.joveeinfotech.kinship.presenter.RequestResponsePresenterImpl
import kotlinx.android.synthetic.main.activity_request_response.*
import org.jetbrains.anko.toast


class RequestResponse : AppCompatActivity(), RequestResponseListener, RequestResponseView{

    var mContext : Context? = null

    var view1 : View? = null

    var requestResponsePresenterImpl : RequestResponsePresenterImpl? = null

    var textViewDonated : TextView? = null
    var textViewNotDonated : TextView? = null

    var textViewQuestion : TextView? = null
    var textViewFrom : TextView? = null
    var textViewHospital : TextView? = null
    var alertDialogGetConfirm : AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_response)

        //view1?.fragment_top20_RecyclerView?.setHasFixedSize(true)
        requestResponsePresenterImpl = RequestResponsePresenterImpl(this,this,this)

        activity_request_response_fab.setOnClickListener{
            activity_request_response_progressBar_refresh_dynamic.visibility = View.VISIBLE
            activity_request_response_progressBar_refresh_static.visibility = View.INVISIBLE
            requestResponsePresenterImpl = RequestResponsePresenterImpl(this,this,this)
        }

    }

    override fun setRequestResponse(details: RequestResponseListAdapter?) {

        activity_request_response_progressBar_refresh_dynamic.visibility = View.INVISIBLE
        activity_request_response_progressBar_refresh_static.visibility = View.VISIBLE

        activity_request_response_RecyclerView.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        activity_request_response_RecyclerView.layoutManager = layoutManager
        activity_request_response_RecyclerView.adapter = details
    }

    override fun onItemClick(responseResult: InnerRequestResponseResult) {
        val li1 = LayoutInflater.from(this)
        val confimDialog1 = li1.inflate(com.joveeinfotech.kinship.R.layout.alert_blood_donated_confirm, null)
        textViewDonated = confimDialog1.findViewById<TextView>(com.joveeinfotech.kinship.R.id.alert_blood_donated_confirm_donated_textView) as TextView
        textViewNotDonated = confimDialog1.findViewById<TextView>(com.joveeinfotech.kinship.R.id.alert_blood_donated_confirm_not_donated_textView) as TextView

        textViewQuestion = confimDialog1.findViewById<TextView>(com.joveeinfotech.kinship.R.id.alert_blood_donated_confirm_question_textView) as TextView
        textViewFrom = confimDialog1.findViewById<TextView>(com.joveeinfotech.kinship.R.id.alert_blood_donated_from_textView) as TextView
        textViewHospital = confimDialog1.findViewById<TextView>(com.joveeinfotech.kinship.R.id.alert_blood_donated_hospital_textView) as TextView

        textViewQuestion!!.text = resources.getString(R.string.blood_donated_confirm, responseResult.name)
        textViewFrom!!.text = "${responseResult.district}"
        textViewHospital!!.text = responseResult.district

        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        alertDialogGetConfirm = alert1.create()
        alertDialogGetConfirm?.show()
        alertDialogGetConfirm?.setCancelable(false)

        textViewDonated!!.setOnClickListener {
            toast("Donated")
            requestResponsePresenterImpl?.isDonated(true)
        }
        textViewNotDonated!!.setOnClickListener{
            toast("Not Donated")
            alertDialogGetConfirm?.dismiss()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu)
        CreateMenu(menu)
        return true
        /*MenuInflater(this).inflate(R.menu.menu_frg_safetybox, menu)
        super.onCreateOptionsMenu(menu, inflater)
        */
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return MenuChoice(item)
    }

    private fun CreateMenu(menu: Menu) {
        val mnu1 = menu.add(0, 0, 0, "DONE")
        run {
            //mnu1.setIcon(R.drawable.icon_refresh)
            mnu1.setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM or
                    MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        }
    }

    private fun MenuChoice(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> {

                return true
            }
        }
        return false
    }

    override fun onBackPressed() {

    }

    override fun closeConfirmDialog() { alertDialogGetConfirm?.dismiss() }
}
