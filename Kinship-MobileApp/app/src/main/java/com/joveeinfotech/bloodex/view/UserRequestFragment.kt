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
import com.joveeinfotech.bloodex.presenter.UserRequestFragmentPresenterImpl
import com.joveeinfotech.bloodex.utils.CustomToast
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.joveeinfotech.bloodex.utils.SharedData
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

    var time_in_string : String? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        view1 = inflater.inflate(R.layout.fragment_user_request, container, false)

        session = SharedData(mContext)

        userRequestFragmentPresenter = UserRequestFragmentPresenterImpl(this,mContext)
        DLog("dgdgdg : ","dsfgdg")
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

        var timeCategories = ArrayList<String>()
        timeCategories.add("Select any one")
        timeCategories.add("Immediate")
        timeCategories.add("Hour")
        timeCategories.add("Day")
        timeCategories.add("Week")
        timeCategories.add("Month")

        //district_categories = fruits
       /* district_categories?.add("Virudhunagar")
        district_categories?.toArray()

        hospital_categories?.add("Apollo")
        hospital_categories?.add("Meenachi Mission")
        hospital_categories?.add("A.R. ")*/

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

        /*val district_dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, fruits)
        //district_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_user_request_constraintLayout_districts_spinner?.threshold = 1
        view1?.fragment_user_request_constraintLayout_districts_spinner?.setAdapter<ArrayAdapter<String>>(district_dataAdapter)*/

        /*view1?.fragment_user_request_constraintLayout_districts_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_district = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }*/

        /* val adapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, fruits)
        //Getting the instance of AutoCompleteTextView
        val actv = findViewById(R.id.autoCompleteTextView) as AutoCompleteTextView
        actv.threshold = 1//will start working from first character
        actv.setAdapter<ArrayAdapter<String>>(adapter)//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED)*/

        /* val hospital_dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, hospital_categories)
        hospital_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_user_request_constraintLayout_hospitals_spinner?.adapter = hospital_dataAdapter

        view1?.fragment_user_request_constraintLayout_hospitals_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }*/

        val time_dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, timeCategories)
        time_dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_user_request_constraintLayout_time_to_arrive_spinner?.adapter = time_dataAdapter
        view1?.fragment_user_request_constraintLayout_time_to_arrive_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                time_in_string =timeCategories.get(position).toString()
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

            if(time_in_string!! == "Immediate"){

            }

            if(search_blood_group!!.isNotEmpty()  &&  fragment_user_request_constraintLayout_units_editText.text.isNotEmpty()
                    && fragment_user_request_constraintLayout_districts_spinner.text.isNotEmpty()
                    && fragment_user_request_constraintLayout_hospitals_spinner.text.isNotEmpty()
                    && fragment_user_request_constraintLayout_time_to_arrive_editText.text.isNotEmpty()
                    && time_in_string!!.isNotEmpty()){

                userRequestFragmentPresenter?.sendUserRequestToServer(search_blood_group!!,
                        fragment_user_request_constraintLayout_units_editText.text.toString(),
                        fragment_user_request_constraintLayout_districts_spinner.text.toString(),
                        fragment_user_request_constraintLayout_hospitals_spinner.text.toString(),
                        fragment_user_request_constraintLayout_time_to_arrive_editText.text.toString(),
                        time_in_string!!)
            }else{
                CustomToast().alertToast(mContext,"Fill the all fields")
            }

            var fg = fragment_user_request_constraintLayout_districts_spinner.text
            //userRequestFragmentPresenter?.sendUserRequestToServer1()
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
        var districtsArray: Array<String> = districtResult.districts.toTypedArray()
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, districtsArray)
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view1?.fragment_user_request_constraintLayout_districts_spinner?.threshold = 1
        view1?.fragment_user_request_constraintLayout_districts_spinner?.setAdapter<ArrayAdapter<String>>(dataAdapter)
        userRequestFragmentPresenter?.loadHospitals()
    }

    override fun setHospitals(hospitalsList: SearchHospitalResult) {
        var hospitalsArray : Array<String> = hospitalsList.hospitals.toTypedArray()
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, hospitalsArray)
        view1?.fragment_user_request_constraintLayout_hospitals_spinner?.threshold = 1
        view1?.fragment_user_request_constraintLayout_hospitals_spinner?.setAdapter<ArrayAdapter<String>>(dataAdapter)
    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        Log.e("eeeeeeeee","Destroy View")
        session?.storeDetails(editText_user_search_units.text.toString().toInt())
    }*/

    override fun closeActivity() {
        activity?.finish()
    }


}
