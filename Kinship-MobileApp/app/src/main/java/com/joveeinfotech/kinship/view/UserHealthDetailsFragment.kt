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

        view.fragment_user_health_details_diabetic_checkBox1.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_asthmetic_checkBox2.isChecked = false
            view.fragment_user_health_details_heartPatient_checkBox3.isChecked = false
            view.fragment_user_health_details_tuberClosis_checkBox4.isChecked = false
            view.fragment_user_health_details_epilepsy_checkBox5.isChecked = false
            view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked = false
            view.fragment_user_health_details_joundice_checkBox7.isChecked = false
            view.fragment_user_health_details_majorSurgery_checkBox8.isChecked = false
            view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked = false
            view.fragment_user_health_details_toothExtraction_checkBox10.isChecked = false

            healthDetails?.append("Diabetic,")
        }
        view.fragment_user_health_details_asthmetic_checkBox2.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_diabetic_checkBox1.isChecked = false
            view.fragment_user_health_details_heartPatient_checkBox3.isChecked = false
            view.fragment_user_health_details_tuberClosis_checkBox4.isChecked = false
            view.fragment_user_health_details_epilepsy_checkBox5.isChecked = false
            view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked = false
            view.fragment_user_health_details_joundice_checkBox7.isChecked = false
            view.fragment_user_health_details_majorSurgery_checkBox8.isChecked = false
            view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked = false
            view.fragment_user_health_details_toothExtraction_checkBox10.isChecked = false

            healthDetails?.append("Asthmetic,")
        }
        view.fragment_user_health_details_heartPatient_checkBox3.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_diabetic_checkBox1.isChecked = false
            view.fragment_user_health_details_asthmetic_checkBox2.isChecked = false
            view.fragment_user_health_details_tuberClosis_checkBox4.isChecked = false
            view.fragment_user_health_details_epilepsy_checkBox5.isChecked = false
            view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked = false
            view.fragment_user_health_details_joundice_checkBox7.isChecked = false
            view.fragment_user_health_details_majorSurgery_checkBox8.isChecked = false
            view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked = false
            view.fragment_user_health_details_toothExtraction_checkBox10.isChecked = false

            healthDetails?.append("Heart_Patient,")
        }
        view.fragment_user_health_details_tuberClosis_checkBox4.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_diabetic_checkBox1.isChecked = false
            view.fragment_user_health_details_asthmetic_checkBox2.isChecked = false
            view.fragment_user_health_details_heartPatient_checkBox3.isChecked = false
            view.fragment_user_health_details_epilepsy_checkBox5.isChecked = false
            view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked = false
            view.fragment_user_health_details_joundice_checkBox7.isChecked = false
            view.fragment_user_health_details_majorSurgery_checkBox8.isChecked = false
            view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked = false
            view.fragment_user_health_details_toothExtraction_checkBox10.isChecked = false

            healthDetails?.append("Tuber_closis,")
        }
        view.fragment_user_health_details_epilepsy_checkBox5.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_diabetic_checkBox1.isChecked = false
            view.fragment_user_health_details_asthmetic_checkBox2.isChecked = false
            view.fragment_user_health_details_heartPatient_checkBox3.isChecked = false
            view.fragment_user_health_details_tuberClosis_checkBox4.isChecked = false
            view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked = false
            view.fragment_user_health_details_joundice_checkBox7.isChecked = false
            view.fragment_user_health_details_majorSurgery_checkBox8.isChecked = false
            view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked = false
            view.fragment_user_health_details_toothExtraction_checkBox10.isChecked = false

            healthDetails?.append("Epilesy,")
        }
        view.fragment_user_health_details_typoidOrMalaria_checkBox6.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_typoidOrMalaria_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_typoidOrMalaria_year_EditText.visibility = View.VISIBLE

            healthDetails?.append("Typoid / Malaria,")
        }
        view.fragment_user_health_details_joundice_checkBox7.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_joundice_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_joundice_year_EditText.visibility = View.VISIBLE

            healthDetails?.append("Joundice,")
        }
        view.fragment_user_health_details_majorSurgery_checkBox8.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_majorSurgery_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_majorSurgery_year_EditText.visibility = View.VISIBLE

            healthDetails?.append("Major_surgery,")
        }
        view.fragment_user_health_details_transfusionOrTatto_checkBox9.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_transfusionOrTatto_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_transfusionOrTatto_year_EditText.visibility = View.VISIBLE

            healthDetails?.append("Transfusion / Tatto,")
        }
        view.fragment_user_health_details_toothExtraction_checkBox10.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_toothExtraction_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_toothExtraction_year_EditText.visibility = View.VISIBLE

            healthDetails?.append("Tooth_extraction,")
        }
        view.fragment_user_health_details_none_checkBox11.setOnClickListener{
            healthDetails?.append("None")
            view.fragment_user_health_details_diabetic_checkBox1.isChecked = false
            view.fragment_user_health_details_asthmetic_checkBox2.isChecked = false
            view.fragment_user_health_details_heartPatient_checkBox3.isChecked = false
            view.fragment_user_health_details_tuberClosis_checkBox4.isChecked = false
            view.fragment_user_health_details_epilepsy_checkBox5.isChecked = false
            view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked = false
            view.fragment_user_health_details_joundice_checkBox7.isChecked = false
            view.fragment_user_health_details_majorSurgery_checkBox8.isChecked = false
            view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked = false
            view.fragment_user_health_details_toothExtraction_checkBox10.isChecked = false
        }
        view.fragment_user_health_details_submit_button.setOnClickListener{
            if(view.fragment_user_health_details_diabetic_checkBox1.isChecked
                    || view.fragment_user_health_details_asthmetic_checkBox2.isChecked
                    || view.fragment_user_health_details_heartPatient_checkBox3.isChecked
                    || view.fragment_user_health_details_tuberClosis_checkBox4.isChecked
                    || view.fragment_user_health_details_epilepsy_checkBox5.isChecked
                    || view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked
                    || view.fragment_user_health_details_joundice_checkBox7.isChecked
                    || view.fragment_user_health_details_majorSurgery_checkBox8.isChecked
                    || view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked
                    || view.fragment_user_health_details_toothExtraction_checkBox10.isChecked
                    || view.fragment_user_health_details_none_checkBox11.isChecked){

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