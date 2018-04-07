package com.joveeinfotech.bloodex.view

import android.app.Activity
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
import android.widget.Button
import android.widget.TextView
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.adapter.RequestResponseListAdapter
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.bloodex.model.InnerRequestResponseResult
import com.joveeinfotech.bloodex.presenter.RequestResponsePresenterImpl
import kotlinx.android.synthetic.main.activity_request_response.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast


class RequestResponse : AppCompatActivity(), RequestResponseListener, RequestResponseView{

    var mContext : Context? = null

    var view1 : View? = null

    var requestResponsePresenterImpl : RequestResponsePresenterImpl? = null

    var textViewDonated : Button? = null
    var textViewNotDonated : Button? = null

    var buttonOk : Button? = null
    var buttonNone : Button? = null

    var textViewQuestion : TextView? = null
    var textViewFrom : TextView? = null
    var textViewHospital : TextView? = null
    var alertDialogGetConfirm : AlertDialog? = null
    var alertDialogNotDonatedConfirm : AlertDialog? = null


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
        val confimDialog1 = li1.inflate(com.joveeinfotech.bloodex.R.layout.alert_blood_donated_confirm, null)
        textViewDonated = confimDialog1.findViewById<Button>(com.joveeinfotech.bloodex.R.id.system_alert_blood_donation_day_get_ok_textView) as Button
        textViewNotDonated = confimDialog1.findViewById<Button>(com.joveeinfotech.bloodex.R.id.alert_blood_donated_confirm_not_donated_textView) as Button

        textViewQuestion = confimDialog1.findViewById<TextView>(com.joveeinfotech.bloodex.R.id.alert_blood_donated_confirm_question_textView) as TextView
        textViewFrom = confimDialog1.findViewById<TextView>(com.joveeinfotech.bloodex.R.id.alert_blood_donated_from_textView) as TextView
        textViewHospital = confimDialog1.findViewById<TextView>(com.joveeinfotech.bloodex.R.id.alert_blood_donated_hospital_textView) as TextView

        textViewQuestion!!.text = resources.getString(R.string.blood_donated_confirm, responseResult.name)
        textViewFrom!!.text = "${responseResult.district}"
        textViewHospital!!.text = responseResult.district

        /*textViewQuestion!!.text = "Is Rahu donated blood?"
        textViewFrom!!.text = "Madurai"
        textViewHospital!!.text = "AR Hospital, Madurai"
*/
        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        alertDialogGetConfirm = alert1.create()
        alertDialogGetConfirm?.show()
        alertDialogGetConfirm?.setCancelable(false)

        textViewDonated!!.setOnClickListener {
            toast("Donated")
            requestResponsePresenterImpl?.isDonated(responseResult.person_id)
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
                var isClickDonated = getBooleanPreference(this,"isClickDonated",false)
                if(isClickDonated){
                    var user_id = getStringPreference(this,"user_id","")
                    requestResponsePresenterImpl?.clearRequestResponse(user_id)
                    //finishAffinity()
                }else{
                    displayDialog()
                }
                return true
            }
        }
        return false
    }

    private fun displayDialog() {
        val li1 = LayoutInflater.from(this)
        val confimDialog1 = li1.inflate(com.joveeinfotech.bloodex.R.layout.alert_blood_donated_not_selected, null)
        buttonOk = confimDialog1.findViewById<Button>(com.joveeinfotech.bloodex.R.id.alert_blood_donated_not_selected_ok_textView) as Button
        buttonNone = confimDialog1.findViewById<Button>(com.joveeinfotech.bloodex.R.id.alert_blood_donated_not_selected_none_textView) as Button

        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        alertDialogNotDonatedConfirm = alert1.create()
        alertDialogNotDonatedConfirm?.show()
        alertDialogNotDonatedConfirm?.setCancelable(false)

        buttonOk!!.setOnClickListener {
            alertDialogNotDonatedConfirm?.dismiss()
        }
        buttonNone!!.setOnClickListener{
            finishAffinity()
            //requestResponsePresenterImpl?.SendNoOneDonated()
        }
    }

    override fun onBackPressed() {
        snackbar((this as Activity).findViewById(android.R.id.content), "Click DONE to Exit")
    }

    override fun closeConfirmDialog() {
        setBooleanPreference(this,"isClickDonated",true)
        alertDialogGetConfirm?.dismiss()
    }
    override fun closeNotDonatedConfirmDialog() {
        alertDialogNotDonatedConfirm?.dismiss()
    }

    override fun closeActivity() { finishAffinity() }
}
