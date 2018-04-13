package com.joveeinfotech.bloodex.view

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.bloodex.Home
import android.widget.Toast
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.presenter.UserHealthDetailsFragmentPresenterImpl
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others.DLog
import kotlinx.android.synthetic.main.fragment_user_health_details.view.*
import java.util.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONArray


class UserHealthDetailsFragment : Fragment(), UserHealthDetailsFragmentView {

    lateinit var mContext: Context

    var userHealthDetailsFragmentPresenter : UserHealthDetailsFragmentPresenterImpl? = null

    //var healthDetails : StringBuffer? = null
    /*var diabetic:Array<String>?=null
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

    var healthDetails:Array<Array<String>?>?=null*/
    var trans : FragmentTransaction? = null
    var userDetailActivity : UserDetails? = null
    var diabetic:Boolean=false
    var asthmatic:Boolean=false
    var heart_patient:Boolean=false
    var tuberculosis:Boolean=false
    var epilepsy:Boolean=false
    var typhoid_malaria:Boolean=false
    var jaundice:Boolean=false
    var major_surgery:Boolean=false
    var transfusion_tattoo:Boolean=false
    var tooth_extraction:Boolean=false
    var none:Boolean=false
    val jsonArray = JsonArray()
    val diabeticobj = JSONObject()
    val asthmaticobj = JSONObject()
    val heart_patientobj = JSONObject()
    val tuberculosisobj = JSONObject()
    val epilepsyobj = JSONObject()
    val typhoid_malariaobj = JSONObject()
    val jaundiceobj = JSONObject()
    val major_surgeryobj = JSONObject()
    val tooth_extractionobj = JSONObject()
    val transfusion_tattooobj = JSONObject()
    val noneobj = JSONObject()
    var healthDetailsObj = JSONObject()

    var df:ArrayList<String>?=null
    var health_Details:ArrayList<ArrayList<String>> = ArrayList<ArrayList<String>>()
    //var health_Details:ArrayList<ArrayList<String>>? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        trans = fragmentManager?.beginTransaction()
        userDetailActivity = activity as UserDetails

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
            //diabetic= arrayOf("Diabetic")
            //diabetic = view.fragment_user_health_details_diabetic_checkBox1.isChecked == true

            /*none=false
            asthmatic=false
            heart_patient=false
            tuberculosis=false
            epilepsy=false
            typhoid_malaria=false
            jaundice=false*/

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
            //asthmetic= arrayOf("Asthmetic")
            //asthmatic= view.fragment_user_health_details_asthmetic_checkBox2.isChecked == true
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
            //heart_patient= arrayOf("Heart_Patient")
            //heart_patient= view.fragment_user_health_details_heartPatient_checkBox3.isChecked == true
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
            //tuber_closis= arrayOf("Tuber_closis")
            //tuberculosis=view.fragment_user_health_details_tuberClosis_checkBox4.isChecked == true
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
            //epilepsy= arrayOf("Epilepsy")
            //epilepsy=view.fragment_user_health_details_epilepsy_checkBox5.isChecked == true
        }
        view.fragment_user_health_details_typoidOrMalaria_checkBox6.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_typoidOrMalaria_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_typoidOrMalaria_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Typoid / Malaria,")
            //typoid_malaria= arrayOf("Typoid/Malaria","${view.fragment_user_health_details_typoidOrMalaria_year_EditText.text.toString()}-${ view.fragment_user_health_details_typoidOrMalaria_year_EditText.text.toString()}-01")
            //typhoid_malaria=view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked == true
        }
        view.fragment_user_health_details_joundice_checkBox7.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_joundice_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_joundice_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Joundice,")
            //joundice= arrayOf("Joundice","${ view.fragment_user_health_details_joundice_year_EditText.text.toString()}-${ view.fragment_user_health_details_joundice_month_EditText.text.toString()}-01")
            //jaundice=view.fragment_user_health_details_joundice_checkBox7.isChecked == true
        }
        view.fragment_user_health_details_majorSurgery_checkBox8.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_majorSurgery_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_majorSurgery_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Major_surgery,")
            //major_surgery= arrayOf("Major_surgery","${view.fragment_user_health_details_majorSurgery_year_EditText.text.toString()}-${view.fragment_user_health_details_majorSurgery_month_EditText.text.toString()}-01")
            //major_surgery=view.fragment_user_health_details_majorSurgery_checkBox8.isChecked == true
        }
        view.fragment_user_health_details_transfusionOrTatto_checkBox9.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_transfusionOrTatto_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_transfusionOrTatto_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Transfusion / Tatto,")
            //transfusion_tatto= arrayOf("Transfusion/Tatto","${view.fragment_user_health_details_transfusionOrTatto_year_EditText.text.toString()}-${view.fragment_user_health_details_transfusionOrTatto_month_EditText.text.toString()}-01")
            //transfusion_tattoo= view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked == true
        }
        view.fragment_user_health_details_toothExtraction_checkBox10.setOnClickListener{
            view.fragment_user_health_details_none_checkBox11.isChecked = false

            view.fragment_user_health_details_toothExtraction_month_EditText.visibility = View.VISIBLE
            view.fragment_user_health_details_toothExtraction_year_EditText.visibility = View.VISIBLE

            //healthDetails?.append("Tooth_extraction,")
            //tooth_extraction= arrayOf("Tooth_extraction","${view.fragment_user_health_details_toothExtraction_year_EditText.text.toString()}-${view.fragment_user_health_details_toothExtraction_month_EditText.text.toString()}-01")
            //tooth_extraction=view.fragment_user_health_details_toothExtraction_checkBox10.isChecked == true
        }
        view.fragment_user_health_details_none_checkBox11.setOnClickListener{
            //healthDetails?.append("None")
            //none= arrayOf("None")
            //none=view.fragment_user_health_details_none_checkBox11.isChecked == true
            /*diabetic=false
            asthmatic=false
            heart_patient=false
            tuberculosis=false
            epilepsy=false
            typhoid_malaria=false
            jaundice=false
            major_surgery=false
            transfusion_tattoo=false
            tooth_extraction=false*/
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


                /*diabeticobj.remove("disease_id")
                diabeticobj.remove("disease_time")
                diabeticobj.remove("status")*/
                //jsonArray.remove(0)
                /*var l = jsonArray.length()
                DLog("Health : ",l.toString())
                for(i in 0..l){
                    DLog("Health inner : ",i.toString())
                    jsonArray.remove(i)
                }*/
                /*var i = 0
                var l = jsonArray.length()
                DLog("Health : ",l.toString())
                while (i < l) {
                    i++
                    DLog("Health inner : ",i.toString())
                    jsonArray.remove(i)
                }*/
                val jsonArray = JsonArray()
                /*jsonArray.remove(0)
                jsonArray.remove(1)
                jsonArray.remove(2)
                jsonArray.remove(3)
                jsonArray.remove(4)
                jsonArray.remove(5)
                jsonArray.remove(6)
                jsonArray.remove(7)
                jsonArray.remove(8)
                jsonArray.remove(9)
                jsonArray.remove(10)
                jsonArray.remove(11)*/

                if (view.fragment_user_health_details_diabetic_checkBox1.isChecked){
                    /*df= arrayListOf<String>("1","0000-00-00","0")
                    //health_Details= arrayListOf(df!!)
                    health_Details.add(df!!)
                    DLog("di","${health_Details.toString()}")*/
                    val diabeticobj = JsonObject()
                    try {
                        diabeticobj.addProperty("disease_id", "1")
                        diabeticobj.addProperty("disease_time", "2017-08-08")
                        diabeticobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(diabeticobj)
                    //jsonArray.remove(0)

                }
                if(view.fragment_user_health_details_asthmetic_checkBox2.isChecked){
                   /* df= arrayListOf<String>("2","0000-00-00","0")
                    health_Details.add(df!!)
                    DLog("as","${df!!}")
*/
                    val asthmaticobj = JsonObject()
                    try {
                        asthmaticobj.addProperty("disease_id", "2")
                        asthmaticobj.addProperty("disease_time", "0000-00-00")
                        asthmaticobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(asthmaticobj)
                }
                if (view.fragment_user_health_details_heartPatient_checkBox3.isChecked){
                   /* df= arrayListOf<String>("3","0000-00-00","0")
                    health_Details.add(df!!)
                    DLog("he","${df!!}")*/
                    val heart_patientobj = JsonObject()
                    try {
                        heart_patientobj.addProperty("disease_id", "3")
                        heart_patientobj.addProperty("disease_time", "0000-00-00")
                        heart_patientobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(heart_patientobj)
                }
                if (view.fragment_user_health_details_tuberClosis_checkBox4.isChecked){
                   /* df= arrayListOf<String>("4","0000-00-00","0")
                    health_Details.add(df!!)
                    DLog("tu","${df!!}")*/
                    val tuberculosisobj = JsonObject()
                    try {
                        tuberculosisobj.addProperty("disease_id", "4")
                        tuberculosisobj.addProperty("disease_time", "0000-00-00")
                        tuberculosisobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(tuberculosisobj)
                }
                if (view.fragment_user_health_details_epilepsy_checkBox5.isChecked){
                    /*df= arrayListOf<String>("5","0000-00-00","0")
                    health_Details.add(df!!)
                    DLog("ep","${df!!}")*/
                    val epilepsyobj = JsonObject()
                    try {
                        epilepsyobj.addProperty("disease_id", "5")
                        epilepsyobj.addProperty("disease_time", "0000-00-00")
                        epilepsyobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(epilepsyobj)
                }
                if (view.fragment_user_health_details_typoidOrMalaria_checkBox6.isChecked){
                    /*df= arrayListOf<String>("6","${view.fragment_user_health_details_typoidOrMalaria_year_EditText.text.toString()}-${ view.fragment_user_health_details_typoidOrMalaria_month_EditText.text.toString()}-01","0")
                    health_Details.add(df!!)
                    DLog("ty","${health_Details.toString()}")*/
                    val typhoid_malariaobj = JsonObject()
                    try {
                        typhoid_malariaobj.addProperty("disease_id", "6")
                        typhoid_malariaobj.addProperty("disease_time", "${view.fragment_user_health_details_typoidOrMalaria_year_EditText.text.toString()}-${ view.fragment_user_health_details_typoidOrMalaria_month_EditText.text.toString()}-01")
                        typhoid_malariaobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(typhoid_malariaobj)
                }
                if (view.fragment_user_health_details_joundice_checkBox7.isChecked){
                   /* df= arrayListOf<String>("7","${ view.fragment_user_health_details_joundice_year_EditText.text.toString()}-${ view.fragment_user_health_details_joundice_month_EditText.text.toString()}-01","0")
                    health_Details.add(df!!)
                    DLog("ja","${df!!}")*/
                    val jaundiceobj = JsonObject()
                    try {
                        jaundiceobj.addProperty("disease_id", "7")
                        jaundiceobj.addProperty("disease_time", "${ view.fragment_user_health_details_joundice_year_EditText.text.toString()}-${ view.fragment_user_health_details_joundice_month_EditText.text.toString()}-01")
                        jaundiceobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(jaundiceobj)
                }
                if ( view.fragment_user_health_details_majorSurgery_checkBox8.isChecked){
                    /*df= arrayListOf<String>("8","${view.fragment_user_health_details_majorSurgery_year_EditText.text.toString()}-${view.fragment_user_health_details_majorSurgery_month_EditText.text.toString()}-01","0")
                    health_Details.add(df!!)
                    DLog("ma","${df!!}")*/
                    val major_surgeryobj = JsonObject()
                    try {
                        major_surgeryobj.addProperty("disease_id", "8")
                        major_surgeryobj.addProperty("disease_time", "${view.fragment_user_health_details_majorSurgery_year_EditText.text.toString()}-${view.fragment_user_health_details_majorSurgery_month_EditText.text.toString()}-01")
                        major_surgeryobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(major_surgeryobj)
                }
                if (view.fragment_user_health_details_transfusionOrTatto_checkBox9.isChecked){
                    /*df= arrayListOf<String>("9","${view.fragment_user_health_details_transfusionOrTatto_year_EditText.text.toString()}-${view.fragment_user_health_details_transfusionOrTatto_month_EditText.text.toString()}-01","0")
                    health_Details.add(df!!)
                    DLog("tr","${df!!}")*/
                    val transfusion_tattooobj = JsonObject()
                    try {
                        transfusion_tattooobj.addProperty("disease_id", "9")
                        transfusion_tattooobj.addProperty("disease_time", "${view.fragment_user_health_details_transfusionOrTatto_year_EditText.text.toString()}-${view.fragment_user_health_details_transfusionOrTatto_month_EditText.text.toString()}-01")
                        transfusion_tattooobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(transfusion_tattooobj)
                }
                if (view.fragment_user_health_details_toothExtraction_checkBox10.isChecked){
                   /* df= arrayListOf<String>("10","${view.fragment_user_health_details_toothExtraction_year_EditText.text.toString()}-${view.fragment_user_health_details_toothExtraction_month_EditText.text.toString()}-01","0")
                    health_Details.add(df!!)
                    DLog("to","${df!!}")*/
                    val tooth_extractionobj = JsonObject()
                    try {
                        tooth_extractionobj.addProperty("disease_id", "10")
                        tooth_extractionobj.addProperty("disease_time", "${view.fragment_user_health_details_toothExtraction_year_EditText.text.toString()}-${view.fragment_user_health_details_toothExtraction_month_EditText.text.toString()}-01")
                        tooth_extractionobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(tooth_extractionobj)
                }
                if (view.fragment_user_health_details_none_checkBox11.isChecked){
                   /* df= arrayListOf<String>("11","0000-00-00","0")
                    health_Details.add(df!!)
                    DLog("no","${health_Details.toString()}")*/
                    val noneobj = JsonObject()
                    try {
                        noneobj.addProperty("disease_id", "11")
                        noneobj.addProperty("disease_time", "0000-00-00")
                        noneobj.addProperty("status", "0")

                    } catch (e: JSONException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    jsonArray.add(noneobj)
                }
                /*if(diabetic==false||asthmatic==false||heart_patient==false||tuberculosis==false||epilepsy==false||typhoid_malaria==false||jaundice==false||major_surgery==false||transfusion_tattoo==false||tooth_extraction==false||none==false){
                    Toast.makeText(mContext,"No Diseases please select the None Option",Toast.LENGTH_SHORT).show()
                }*/
               /* else{}*/
                                    //healthDetails= arrayOf(diabetic,asthmetic,heart_patient,tuber_closis,epilepsy,typoid_malaria,joundice,major_surgery,transfusion_tatto,tooth_extraction,none)
                //CustomToast().normalToast(mContext,"${health_Details.toString()}")
               /* val array = arrayOfNulls<String>(health_Details!!.size)
                health_Details!!.toArray(array)*/
                //val list=Arrays.asList(*health_Details!!)
                //for(i in Arrays.asList(health_Details)){
                   // DLog("Health_Detailss","${}")
                //}

                /*val builder = StringBuilder()
                for (i in health_Details.orEmpty()) {
                    builder.append("" + i + " ")

                }*/

                /*for(value in health_Details!!.iterator()){
                    DLog("Response",value.toString())
                }*/

               /* var list=health_Details?.iterator()
                DLog("error","${list.toString()}")
                while (list!!.hasNext()){
                    DLog("Response",list.next().toString())
                }*/



                val healthDetailsObj = JsonObject()
                //healthDetailsObj.remove("diseases")
                healthDetailsObj.add("diseases", jsonArray)
                userHealthDetailsFragmentPresenter?.sendHealthDetails(healthDetailsObj)

            }else{
                CustomToast().alertToast(mContext,mContext.getString(R.string.must_select_atleast_one))
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

    override fun navigateFragment() {
        if(!userDetailActivity?.getIsCompleteProfile()!!){
            trans?.replace(R.id.activity_user_details_frame_layout, UserHealthDetailsFragment.newInstance())
            trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
            trans?.commit()
        }else if(!userDetailActivity?.getIsCompleteAddress()!!){
            trans?.replace(R.id.activity_user_details_frame_layout, UserAddressFragment.newInstance())
            trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
            trans?.commit()
        }else if(!userDetailActivity?.getIsCompleteAdditionalDetails()!!){
            trans?.replace(R.id.activity_user_details_frame_layout, UserAdditionalDetailsFragment.newInstance())
            trans?.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
            trans?.commit()
        }else{
            mContext.startActivity(Intent(mContext, Home::class.java))
            userDetailActivity!!.closeActivity()
        }
    }
}