package com.joveeinfotech.kinship.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.UserHealthDetailsFragmentPresenterImpl
import com.joveeinfotech.kinship.utils.CustomToast
import kotlinx.android.synthetic.main.fragment_user_health_details.view.*

class UserHealthDetailsFragment : Fragment(), UserHealthDetailsFragmentView {

    lateinit var mContext: Context

    var userHealthDetailsFragmentPresenter : UserHealthDetailsFragmentPresenterImpl? = null

    var healthDetails : StringBuffer? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val trans = fragmentManager?.beginTransaction()
        userHealthDetailsFragmentPresenter = UserHealthDetailsFragmentPresenterImpl(trans, this, mContext)
        var view : View = inflater.inflate(R.layout.fragment_user_health_details, container, false)

        view.fragment_user_health_details_checkbox1_diabetic.setOnClickListener{
            healthDetails?.append("Diabetic,")
        }
        view.fragment_user_health_details_checkbox2_Asthmetic.setOnClickListener{
            healthDetails?.append("Asthmetic,")
        }
        view.fragment_user_health_details_checkbox3_heart_patient.setOnClickListener{
            healthDetails?.append("Heart_Patient,")
        }
        view.fragment_user_health_details_checkbox4_tuber_closis.setOnClickListener{
            healthDetails?.append("Tuber_closis,")
        }
        view.fragment_user_health_details_checkbox5_epilepsy.setOnClickListener{
            healthDetails?.append("Epilesy,")
        }
        view.fragment_user_health_details_checkbox6_typoid_or_malaria.setOnClickListener{
            healthDetails?.append("Typoid / Malaria,")
        }
        view.fragment_user_health_details_checkbox7_joundice.setOnClickListener{
            healthDetails?.append("Joundice,")
        }
        view.fragment_user_health_details_checkbox8_major_surgery.setOnClickListener{
            healthDetails?.append("Major_surgery,")
        }
        view.fragment_user_health_details_checkbox9_transfusion_tatto.setOnClickListener{
            healthDetails?.append("Transfusion / Tatto,")
        }
        view.fragment_user_health_details_checkbox10_tooth_extraction.setOnClickListener{
            healthDetails?.append("Tooth_extraction,")
        }
        view.fragment_user_health_details_checkbox11_none.setOnClickListener{
            healthDetails?.append("None")
            view.fragment_user_health_details_checkbox2_Asthmetic.isChecked = false
            view.fragment_user_health_details_checkbox3_heart_patient.isChecked = false
            view.fragment_user_health_details_checkbox4_tuber_closis.isChecked = false
            view.fragment_user_health_details_checkbox5_epilepsy.isChecked = false
            view.fragment_user_health_details_checkbox6_typoid_or_malaria.isChecked = false
            view.fragment_user_health_details_checkbox7_joundice.isChecked = false
            view.fragment_user_health_details_checkbox8_major_surgery.isChecked = false
            view.fragment_user_health_details_checkbox9_transfusion_tatto.isChecked = false
            view.fragment_user_health_details_checkbox10_tooth_extraction.isChecked = false
            view.fragment_user_health_details_checkbox11_none.isChecked
        }
        view.fragment_user_health_details_button_submit.setOnClickListener{
            if(view.fragment_user_health_details_checkbox1_diabetic.isChecked
                    || view.fragment_user_health_details_checkbox2_Asthmetic.isChecked
                    || view.fragment_user_health_details_checkbox3_heart_patient.isChecked
                    || view.fragment_user_health_details_checkbox4_tuber_closis.isChecked
                    || view.fragment_user_health_details_checkbox5_epilepsy.isChecked
                    || view.fragment_user_health_details_checkbox6_typoid_or_malaria.isChecked
                    || view.fragment_user_health_details_checkbox7_joundice.isChecked
                    || view.fragment_user_health_details_checkbox8_major_surgery.isChecked
                    || view.fragment_user_health_details_checkbox9_transfusion_tatto.isChecked
                    || view.fragment_user_health_details_checkbox10_tooth_extraction.isChecked
                    || view.fragment_user_health_details_checkbox11_none.isChecked){

                userHealthDetailsFragmentPresenter?.sendHealthDetails(healthDetails)

            }else{
                CustomToast().alertToast(mContext,"Must select atleast one")
            }

        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance(): UserHealthDetailsFragment {
            return UserHealthDetailsFragment()
        }
    }

    /*  override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("checking image array",ba.toString())

        outState.putByteArray("myarray", ba)
        outState.putString("first_name",editText_first_name.text.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(bitmap != null) {
            bitmap = BitmapFactory.decodeByteArray(ba, 0, ba!!.size)
        }
        Log.e("inside onActivityCre : ",ba.toString())
        //if(savedInstanceState!=null){
            //ba = savedInstanceState?.getByteArray("myarray")

            view1?.imageView?.setImageBitmap(bitmap)
    }*/

    /*override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        ba = savedInstanceState?.getByteArray("myarray")
        if(bitmap != null) {
            bitmap = BitmapFactory.decodeByteArray(ba, 0, ba!!.size)
        }
        imageView?.setImageBitmap(bitmap)
    }*/

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }
}