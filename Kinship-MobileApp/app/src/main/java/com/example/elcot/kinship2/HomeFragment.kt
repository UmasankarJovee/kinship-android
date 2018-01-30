package com.example.elcot.kinship2

import android.app.AlertDialog
import android.app.FragmentManager
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_home_fragment.*
import kotlinx.android.synthetic.main.alert_blood_donator_instructions.*

class HomeFragment : Fragment() {

    var mApiInterface : ApiInterface? = null
    private var mCompositeDisposable : Disposable? = null
    val progressBar : ProgressBar? = null
    var progressDialog: ProgressDialog? = null

    var activity_home_fragment_hospitals_count_TextView : TextView? = null
    var activity_home_fragment_users_count_TextView : TextView? = null
    var activity_home_fragment_donator_count_TextView : TextView? = null
    var alert_blood_donator_instructions_exit: ImageView? = null
    var alert_blood_requestor_instructions_exit: ImageView? = null
    val alert_blood_donator_instructions_ScrollView_instructions_TextView : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var view:View=inflater.inflate(R.layout.activity_home_fragment,container,false)
        mApiInterface=RetrofitClient.getClient()
        //updatedDetails()


        val blooddonatorinstructionsTextView=view.findViewById<TextView>(R.id.activity_home_fragment_bloodDonatorInstructions_textview)
        blooddonatorinstructionsTextView.setOnClickListener{
            /*val fragment = BloodDonatorInstructionsFragment()
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame_layout,fragment)
            fragmentTransaction?.commit()*/
            bloodDonatorInstructions()
        }
        val bloodRequestorInstructionsTextView=view.findViewById<TextView>(R.id.activity_home_fragment_bloodRequestInstructions_textview)
        bloodRequestorInstructionsTextView.setOnClickListener{
            bloodRequestorInstructions()
        }
        return view
    }
    private fun bloodDonatorInstructions(){
        val layoutinflater=LayoutInflater.from(activity)
        val conformDialog=layoutinflater.inflate(R.layout.alert_blood_donator_instructions,null)
        val alert=AlertDialog.Builder(activity)
        //val text : String=activity!!.getString(R.string.blood_donator_instructions_textview)
        //alert_blood_donator_instructions_ScrollView_instructions_TextView!!.setText(text)
        alert_blood_donator_instructions_exit=conformDialog.findViewById(R.id.alert_blood_donator_instructions_exit_ImageView)
        alert.setView(conformDialog)
        val alertDialog=alert.create()
        alertDialog.show()

        alert_blood_donator_instructions_exit!!.setOnClickListener{
            alertDialog.dismiss()
        }
    }

    private fun bloodRequestorInstructions(){
        val layoutinflater=LayoutInflater.from(activity)
        val conformDialog=layoutinflater.inflate(R.layout.alert_blood_requestor_instructions,null)
        val alert=AlertDialog.Builder(activity)
        //val text : String=activity!!.getString(R.string.blood_donator_instructions_textview)
        //alert_blood_donator_instructions_ScrollView_instructions_TextView!!.setText(text)
        alert_blood_requestor_instructions_exit=conformDialog.findViewById(R.id.alert_blood_requestor_instructions_exit_ImageView)
        alert.setView(conformDialog)
        val alertDialog=alert.create()
        alertDialog.show()

        alert_blood_requestor_instructions_exit!!.setOnClickListener{
            alertDialog.dismiss()
        }
    }

    private fun updatedDetails() {

        progressDialog = ProgressDialog(activity, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Please wait...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.update_details()!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            activity_home_fragment_hospitals_count_TextView?.setText(result.number_of_hospitals)
                            activity_home_fragment_users_count_TextView?.setText(result.number_of_users)
                            activity_home_fragment_donator_count_TextView?.setText(result.number_of_donated_persons)

                            //displayLog("response")
                        },
                        { error ->
                            progressDialog?.dismiss()
                            //progressBar.visibility=View.GONE
                            Toast.makeText(activity, error.localizedMessage, Toast.LENGTH_LONG).show()
                            //Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                            //displayLog("error")
                        }
                )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)





    }

    companion object {
        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }
}
