package com.joveeinfotech.kinship.view

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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.model.CountryResult
import com.joveeinfotech.kinship.model.*
import com.joveeinfotech.kinship.presenter.UserAddrssFragmentPresenterImpl
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
        collapsingToolbarLayout.title = "Home Address"
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#fcfbfb"))
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#fcfbfb"))
        collapsingToolbarLayout.setStatusBarScrimColor(Color.parseColor("#FF919297"))

        val context = this
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(mContext, R.color.toolBarColor))

        view.button_send_address.setOnClickListener {
            userAddressFragmentPresenter?.sendAddress1()
           /* if (country?.trim()?.length == 0 && state?.trim()?.length == 0 && district?.trim()?.length == 0
                    && editText_city.text.trim().isNotEmpty() && editText_locality.text.trim().isNotEmpty()
                    && editText_street.text.trim().isNotEmpty()){
                userAddressFragmentPresenter?.userAddressDetails(country!!,state!!,district!!,editText_city.text.toString(),"gdgdg",editText_street.text.toString())
            } else {
                //showDialog(0) // Please fill the all the fields
                Toast.makeText(mContext,"Please fill the all the fields", Toast.LENGTH_LONG).show()
            }*/
        }
        return view
    }

    override fun setCountries(countryList: CountryResult) {
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, countryList.country)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_counry.adapter=dataAdapter
        spinner_counry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                country = countryList.country[position].toString()
                userAddressFragmentPresenter?.sendCountryReceiveState(country!!)
                Toast.makeText(mContext, country, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun setStates(stateList: StateResult) {
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, stateList.state)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_state.adapter = dataAdapter
        spinner_state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                state = stateList.state[position].toString()
                userAddressFragmentPresenter?.sendStateReceiveDistrict(state!!)
                Toast.makeText(mContext, state, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun setDistricts(districtList: DistrictResult) {
        val dataAdapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, districtList.district)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_district.adapter = dataAdapter
        spinner_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                district = districtList.district[position].toString()
                //sendStateReceiveDistrict()
                Toast.makeText(mContext, district, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
        networkCall = APICall(mContext)
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