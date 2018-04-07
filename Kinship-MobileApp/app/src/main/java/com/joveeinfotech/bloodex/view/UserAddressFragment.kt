package com.joveeinfotech.bloodex.view

import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.presenter.UserAddrssFragmentPresenterImpl
import kotlinx.android.synthetic.main.fragment_user_address.*
import kotlinx.android.synthetic.main.fragment_user_address.view.*

class UserAddressFragment : Fragment(), UserAddrssFragmentView {

    var networkCall: APICall? = null

    var country: String? = null
    var state: String? = null
    var district: String? = null

    var resolver: ContentResolver? = null
    lateinit var mContext: Context

    var userAddressFragmentPresenter : UserAddrssFragmentPresenterImpl? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        resolver = activity?.contentResolver
        val trans = fragmentManager?.beginTransaction()
        userAddressFragmentPresenter = UserAddrssFragmentPresenterImpl(trans,this,mContext)
        var view : View = inflater.inflate(R.layout.fragment_user_address, container, false)

        val collapsingToolbarLayout = view.findViewById<CollapsingToolbarLayout>(R.id.collapse_toolbar) as CollapsingToolbarLayout
        collapsingToolbarLayout.title = mContext.getString(R.string.user_address_home_address)
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#fcfbfb"))
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#fcfbfb"))
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#FF919297"))
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(mContext, R.color.toolBarColor))

        view.fragment_user_address_button_send_address.setOnClickListener {
            //userAddressFragmentPresenter?.sendAddress1()
            /*if (country?.trim()?.length == 0 && state?.trim()?.length == 0 && district?.trim()?.length == 0
                    && editText_city.text.trim().isNotEmpty() && editText_locality.text.trim().isNotEmpty()
                    && editText_street.text.trim().isNotEmpty()){
                userAddressFragmentPresenter?.userAddressDetails(country!!,state!!,district!!,editText_city.text.toString(),"gdgdg",editText_street.text.toString())
            } else {
                //showDialog(0) // Please fill the all the fields
                Toast.makeText(mContext,"Please fill the all the fields", Toast.LENGTH_LONG).show()
            }*/
            if(fragment_user_address_spinner_counry.text.isNotEmpty()
                    && fragment_user_address_spinner_state.text.isNotEmpty()
                    && fragment_user_address_spinner_district.text.isNotEmpty()
                    && fragment_user_address_editText_city.text.isNotEmpty()
                    && fragment_user_address_editText_locality.text.isNotEmpty()
                    && fragment_user_address_editText_street.text.isNotEmpty()){

                userAddressFragmentPresenter?.userAddressDetails(fragment_user_address_spinner_counry.text.toString(),
                        fragment_user_address_spinner_state.text.toString(),
                        fragment_user_address_spinner_district.text.toString(),
                        fragment_user_address_editText_city.text.toString(),
                        fragment_user_address_editText_locality.text.toString(),
                        fragment_user_address_editText_street.text.toString())
            }
        }
        return view
    }

    override fun setCountries(countryList: ArrayList<String>) {
        //var df = countryList as ArrayList<String>
        //countryList.add(0,mContext.getString(R.string.user_address_select_your_country))
        var country_array = countryList.toTypedArray()
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, country_array)
        fragment_user_address_spinner_counry?.threshold = 1
        fragment_user_address_spinner_counry?.setAdapter<ArrayAdapter<String>>(dataAdapter)
        userAddressFragmentPresenter?.loadStates()

        /*dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragment_user_address_spinner_counry.adapter=dataAdapter
        fragment_user_address_spinner_counry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                country = countryList[position].toString()
                userAddressFragmentPresenter?.sendCountryReceiveState(country!!)
                if(country != mContext.getString(R.string.user_address_select_your_country)) {
                    Toast.makeText(mContext, country, Toast.LENGTH_LONG).show()
                }
            }
        }*/
    }

    override fun setStates(stateList: ArrayList<String>) {

        var state_array = stateList.toTypedArray()
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, state_array)
        fragment_user_address_spinner_state?.threshold = 1
        fragment_user_address_spinner_state?.setAdapter<ArrayAdapter<String>>(dataAdapter)
        userAddressFragmentPresenter?.loadDistricts()

        /*stateList.add(0,mContext.getString(R.string.user_address_select_your_state))
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, stateList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragment_user_address_spinner_state.adapter = dataAdapter
        fragment_user_address_spinner_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                state = stateList[position].toString()
                userAddressFragmentPresenter?.sendStateReceiveDistrict(state!!)
                if(state != mContext.getString(R.string.user_address_select_your_state)) {
                    Toast.makeText(mContext, state, Toast.LENGTH_LONG).show()
                }
            }
        }*/
    }

    override fun setDistricts(districtList: ArrayList<String>) {

        var district_array = districtList.toTypedArray()
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.select_dialog_item, district_array)
        fragment_user_address_spinner_district?.threshold = 1
        fragment_user_address_spinner_district?.setAdapter<ArrayAdapter<String>>(dataAdapter)
        //userAddressFragmentPresenter?.loadHospitals()

        /*districtList.add(0,mContext.getString(R.string.user_address_select_your_district))
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, districtList)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragment_user_address_spinner_district.adapter = dataAdapter
        fragment_user_address_spinner_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                district = districtList[position].toString()
                //sendStateReceiveDistrict()
                if(district != mContext.getString(R.string.user_address_select_your_district)) {
                    Toast.makeText(mContext, district, Toast.LENGTH_LONG).show()
                }
            }
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
        //networkCall = APICall(mContext)
    }

    companion object {
        fun newInstance(): UserAddressFragment {
            return UserAddressFragment()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }


}