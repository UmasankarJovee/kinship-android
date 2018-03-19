package com.joveeinfotech.kinship.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.DistrictResult
import com.joveeinfotech.kinship.model.SearchHospitalResult
import com.joveeinfotech.kinship.presenter.UserRequestFragmentPresenterImpl
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.SharedData
import kotlinx.android.synthetic.main.fragment_user_request.*
import kotlinx.android.synthetic.main.fragment_user_request.view.*


class UserRequestFragment : Fragment(), UserRequestFragmentView {

    var view1 : View? = null
    var session: SharedData? = null
    lateinit var mContext: Context

    var userRequestFragmentPresenter : UserRequestFragmentPresenterImpl? = null

    var search_blood_group : String? = null
    var search_unit : String? = null
    var search_district : String? = null
    var search_hospital : String? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        view1 = inflater.inflate(R.layout.fragment_user_request, container, false)

        session = SharedData(mContext)

        userRequestFragmentPresenter = UserRequestFragmentPresenterImpl(this,mContext)

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

        var district_categories = ArrayList<String>()
        district_categories.add("Select District")
        district_categories.add("Madurai")
        district_categories.add("Virudhunagar")

        var hospital_categories = ArrayList<String>()
        hospital_categories.add("Select Hospital")
        hospital_categories.add("Apollo")
        hospital_categories.add("Meenachi Mission")
        hospital_categories.add("A.R. ")

        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_user_request_constraintLayout_bloodGroup_spinner?.adapter = dataAdapter

        view1?.fragment_user_request_constraintLayout_bloodGroup_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        val district_dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, district_categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_user_request_constraintLayout_districts_spinner?.adapter = district_dataAdapter

        view1?.fragment_user_request_constraintLayout_districts_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        val hospital_dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, hospital_categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_user_request_constraintLayout_hospitals_spinner?.adapter = hospital_dataAdapter

        view1?.fragment_user_request_constraintLayout_hospitals_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

       /*  Log.e("UserRequestFragment","${session?.retrieveDetails()}")
        if(session?.retrieveDetails() != 0) {
            view1?.editText_user_search_units?.setText("${session?.retrieveDetails()!!}")
        }
        else{
            view1?.editText_user_search_units?.setText("xdg")
        }*/

        view1?.fragment_user_request_constraintLayout_submit_button?.setOnClickListener{
            /*if(search_blood_group!!.isNotEmpty()  &&  editText_user_search_units.text.isNotEmpty()
                    && search_district!!.isNotEmpty() && search_hospital!!.isNotEmpty()){
                userRequestFragmentPresenter?.sendUserRequestDetails(search_blood_group!!,editText_user_search_units.text.toString(),
                        search_district!!,search_hospital!!)
            }else{
                CustomToast().alertToast(mContext,"Fill the all fields")
            }*/
            userRequestFragmentPresenter?.sendUserRequestToServer1()
        }

        return view1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun newInstance() : UserRequestFragment {
            return UserRequestFragment()
        }
    }

    override fun setDistricts(districtResult: DistrictResult) {
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, districtResult.districts)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragment_user_request_constraintLayout_districts_spinner.adapter=dataAdapter
        fragment_user_request_constraintLayout_districts_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_district= districtResult.districts[position].toString()
                userRequestFragmentPresenter?.sendDistrictsReceiveHospitals(search_district!!)
                Toast.makeText(mContext, search_district, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun setHospitals(hospitalsList: SearchHospitalResult) {
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, hospitalsList.hospitals)
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
        }
    }
    /*override fun onDestroyView() {
        super.onDestroyView()
        Log.e("eeeeeeeee","Destroy View")
        session?.storeDetails(editText_user_search_units.text.toString().toInt())
    }*/



}
