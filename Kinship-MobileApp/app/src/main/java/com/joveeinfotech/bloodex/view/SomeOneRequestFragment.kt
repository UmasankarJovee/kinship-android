package com.joveeinfotech.bloodex.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.model.DistrictResult
import com.joveeinfotech.bloodex.model.SearchHospitalResult
import com.joveeinfotech.bloodex.presenter.SomeOneRequestFragmentPresenterImpl
import com.joveeinfotech.bloodex.utils.CustomToast
import kotlinx.android.synthetic.main.fragment_some_one_request.*
import kotlinx.android.synthetic.main.fragment_some_one_request.view.*

class SomeOneRequestFragment : Fragment(), SomeOneRequestFragmentView {

    var mContext : Context? = null
    var view1 : View? = null

    var search_blood_group : String? = null
    var relationship : String? = null
    var search_unit : String? = null
    var search_district : String? = null
    var search_hospital : String? = null

    var time_in_number : String? = null
    var time_in_string : String? = null


    var someOneRequestFragmentPresenter : SomeOneRequestFragmentPresenterImpl? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        view1 = inflater.inflate(R.layout.fragment_some_one_request, container, false)

        someOneRequestFragmentPresenter = SomeOneRequestFragmentPresenterImpl(this,mContext)

        var categories = ArrayList<String>()
        categories.add("Select Blood Group")
        categories.add("AB+")
        categories.add("AB-")
        categories.add("A+")
        categories.add("A-")
        categories.add("B+")
        categories.add("B-")
        categories.add("O+")
        categories.add("O-")

        var categories_of_relation = ArrayList<String>()
        categories.add("Select Relation")
        categories.add("Relation")
        categories.add("Friend")
        categories.add("Guardian")
        categories.add("Unknown")

       /* var district_categories = ArrayList<String>()
        district_categories.add("Select District")
        district_categories.add("Madurai")
        district_categories.add("Virudhunagar")

        var hospital_categories = ArrayList<String>()
        hospital_categories.add("Select Hospital")
        hospital_categories.add("Apollo")
        hospital_categories.add("Meenachi Mission")
        hospital_categories.add("A.R. ")*/

        var timeCategories = ArrayList<String>()
        timeCategories.add("Select any one")
        timeCategories.add("Immediate")
        timeCategories.add("Hour")
        timeCategories.add("Day")
        timeCategories.add("Week")
        timeCategories.add("Month")

        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.spinner_some_one_search_blood_group?.adapter = dataAdapter
        view1?.spinner_some_one_search_blood_group?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        val dataAdapter_of_relation = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, categories_of_relation)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.spinner_some_one_relationship?.adapter = dataAdapter_of_relation
        view1?.spinner_some_one_relationship?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                relationship = categories_of_relation.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

       /* val district_dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, district_categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.spinner_some_one_search_district?.adapter = district_dataAdapter

        view1?.spinner_some_one_search_district?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        val hospital_dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, hospital_categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.spinner_some_one_search_hospitals?.adapter = hospital_dataAdapter

        view1?.spinner_some_one_search_hospitals?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }*/

        val time_dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, timeCategories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_some_one_request_constraintLayout_time_to_arrive_spinner?.adapter = time_dataAdapter
        view1?.fragment_some_one_request_constraintLayout_time_to_arrive_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                time_in_string = timeCategories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        view1?.fragment_some_one_request_submit_button?.setOnClickListener{
            if(fragment_some_one_request_name_editText.text.isNotEmpty()
                    && fragment_some_one_request_phoneNumber_editText.text.isNotEmpty()
                    && search_blood_group!!.isNotEmpty()
                    && fragment_some_one_request_units_editText.text.isNotEmpty()
                    && spinner_some_one_search_district.text.isNotEmpty()
                    && spinner_some_one_search_hospitals.text.isNotEmpty()
                    && relationship!!.isNotEmpty()
                    && fragment_some_one_request_constraintLayout_time_to_arrive_editText.text.isNotEmpty()
                    && time_in_string!!.isNotEmpty()) {

                someOneRequestFragmentPresenter?.sendUserRequestToServer(fragment_some_one_request_name_editText.text.toString(),
                        fragment_some_one_request_phoneNumber_editText.text.toString(),
                        search_blood_group!!,
                        fragment_some_one_request_units_editText.text.toString(),
                        spinner_some_one_search_district.text.toString(),
                        spinner_some_one_search_hospitals.text.toString(),
                        relationship!!,
                        fragment_some_one_request_constraintLayout_time_to_arrive_editText.text.toString(),
                        time_in_string!!)
            }else{
                CustomToast().alertToast(mContext!!,"Fill the all fields")
            }
            //someOneRequestFragmentPresenter?.sendUserRequestToServer1()
        }

        return view1
    }

    override fun setDistricts(districtResult: DistrictResult) {
        var districtsArray: Array<String> = districtResult.districts.toTypedArray()
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, districtsArray)
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.spinner_some_one_search_district?.threshold = 1
        view1?.spinner_some_one_search_district?.setAdapter<ArrayAdapter<String>>(dataAdapter)
        someOneRequestFragmentPresenter?.loadHospitals()
    }

    override fun setHospitals(hospitalsList: SearchHospitalResult) {
        /*val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, hospitalsList.hospitals)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragment_user_request_constraintLayout_hospitals_spinner.adapter=dataAdapter
        fragment_user_request_constraintLayout_hospitals_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_hospital= hospitalsList.hospitals[position].toString()
                //userRequestFragmentPresenter?.sendDistrictsReceiveHospitals(district!!)
                Toast.makeText(mContext, search_hospital, Toast.LENGTH_LONG).show()
            }
        }*/
        var hospitalsArray : Array<String> = hospitalsList.hospitals.toTypedArray()
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, hospitalsArray)
        view1?.spinner_some_one_search_hospitals?.threshold = 1
        view1?.spinner_some_one_search_hospitals?.setAdapter<ArrayAdapter<String>>(dataAdapter)
    }

    companion object {
        fun newInstance() : SomeOneRequestFragment {
            return SomeOneRequestFragment()
        }
    }

    override fun closeActivity() {
        activity?.finish()
    }

}