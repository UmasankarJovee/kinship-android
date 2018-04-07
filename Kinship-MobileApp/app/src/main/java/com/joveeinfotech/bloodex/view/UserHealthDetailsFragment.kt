package com.joveeinfotech.bloodex.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.presenter.UserHealthDetailsFragmentPresenterImpl
import com.joveeinfotech.bloodex.utils.CustomToast
import kotlinx.android.synthetic.main.fragment_user_health_details.view.*

class UserHealthDetailsFragment : Fragment(), UserHealthDetailsFragmentView {

    lateinit var mContext: Context

    var userHealthDetailsFragmentPresenter : UserHealthDetailsFragmentPresenterImpl? = null

    //var healthDetails : StringBuffer? = null
    var diabetic:Array<String>?=null
    var asthmetic:Array<String>?=null
    var heart_patient:Array<String>?=null
    var tuber_closis:Array<String>?=null
    var epilepsy:Array<String>?=null
    var typoid_malaria:Array<String>?=null
    var joundice:Array<String>?=null
    var major_surgery:Array<String>?=null
    var transfusion_tatto:Array<String>?=null
    var tooth_extraction:Array<String>?=null
    var none:Array<String>?=null
    var healthDetails:Array<Array<String>?>?=null
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

            //healthDetails?.append("Diabetic,")
            diabetic= arrayOf("Diabetic")
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

            //healthDetails?.append("Asthmetic,")
            asthmetic= arrayOf("Asthmetic")
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

            //healthDetails?.append("Heart_Patient,")
            heart_patient= arrayOf("Heart_Patient")
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

            //healthDetails?.append("Tuber_closis,")
            tuber_closis= arrayOf("Tuber_closis")
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

            //healthDetails?.append("Epilepsy,")
            epilepsy= arrayOf("Epilepsy")
        }
        view.fragment_user_health_details_typoidOrMalaria_checkBox6.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_typoidOrMalaria_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_typoidOrMalaria_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Typoid / Malaria,")
            typoid_malaria= arrayOf("Typoid/Malaria","${view.fragment_user_health_details_typoidOrMalaria_year_EditText.text.toString()}-${ view.fragment_user_health_details_typoidOrMalaria_year_EditText.text.toString()}-01")
        }
        view.fragment_user_health_details_joundice_checkBox7.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_joundice_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_joundice_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Joundice,")
            joundice= arrayOf("Joundice","${ view.fragment_user_health_details_joundice_year_EditText.text.toString()}-${ view.fragment_user_health_details_joundice_month_EditText.text.toString()}-01")
        }
        view.fragment_user_health_details_majorSurgery_checkBox8.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_majorSurgery_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_majorSurgery_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Major_surgery,")
            major_surgery= arrayOf("Major_surgery","${view.fragment_user_health_details_majorSurgery_year_EditText.text.toString()}-${view.fragment_user_health_details_majorSurgery_month_EditText.text.toString()}-01")
        }
        view.fragment_user_health_details_transfusionOrTatto_checkBox9.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_transfusionOrTatto_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_transfusionOrTatto_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Transfusion / Tatto,")
            transfusion_tatto= arrayOf("Transfusion/Tatto","${view.fragment_user_health_details_transfusionOrTatto_year_EditText.text.toString()}-${view.fragment_user_health_details_transfusionOrTatto_month_EditText.text.toString()}-01")
        }
        view.fragment_user_health_details_toothExtraction_checkBox10.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_toothExtraction_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_toothExtraction_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Tooth_extraction,")
            tooth_extraction= arrayOf("Tooth_extraction","${view.fragment_user_health_details_toothExtraction_year_EditText.text.toString()}-${view.fragment_user_health_details_toothExtraction_month_EditText.text.toString()}-01")
        }
        view.fragment_user_health_details_none_checkBox11.setOnClickListener{
            //healthDetails?.append("None")
            none= arrayOf("None")
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
                        healthDetails= arrayOf(diabetic,asthmetic,heart_patient,tuber_closis,epilepsy,typoid_malaria,joundice,major_surgery,transfusion_tatto,tooth_extraction,none)
                userHealthDetailsFragmentPresenter?.sendHealthDetails(healthDetails!!)

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