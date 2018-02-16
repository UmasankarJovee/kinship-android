package com.joveeinfotech.kinship.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.UserAdditionalDetailsFragmentPresenterImpl
import com.joveeinfotech.kinship.utils.CustomToast
import kotlinx.android.synthetic.main.fragment_user_additional_details.*
import kotlinx.android.synthetic.main.fragment_user_additional_details.view.*

class UserAdditionalDetailsFragment : Fragment(), UserAdditionalDetailsFragmentView {

    var networkCall: APICall? = null

    lateinit var mContext: Context
    //var view1 : View? = null

    var category_of_person : String? = null

    var userAddtitionalDetailsFragmentPresenter : UserAdditionalDetailsFragmentPresenterImpl? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view : View = inflater.inflate(R.layout.fragment_user_additional_details, container, false)

        val trans = fragmentManager?.beginTransaction()
        userAddtitionalDetailsFragmentPresenter = UserAdditionalDetailsFragmentPresenterImpl(trans, this, mContext)

        view.radio_student.setOnClickListener{
            view.radio_others.isChecked=false
            view.radio_working.isChecked=false
            view.editText_others.visibility=View.GONE
            category_of_person="Student"
        }
        view.radio_working.setOnClickListener{
            view.radio_student.isChecked=false
            view.radio_others.isChecked=false
            view.editText_others.visibility=View.GONE
            category_of_person="Working"
        }
        view.radio_others.setOnClickListener{
            view.radio_student.isChecked=false
            view.radio_working.isChecked=false
            view.editText_others.visibility=View.VISIBLE
        }

        view.button_send_additional_details.setOnClickListener{

            if(view.editText_others.visibility == View.VISIBLE){
                category_of_person=editText_others.text.toString()
            }
            if(category_of_person != null) {
                userAddtitionalDetailsFragmentPresenter?.userAdditionalDetails(category_of_person!!, editText_additional_phone_number.text.toString(),
                        editText_additional_email.text.toString(),
                        editText_social_profile.text.toString())
            }
            else
            {
                CustomToast().alertToast(mContext,"You must select Occupation")
            }
        }
        view.floatingActionButton_skip.setOnClickListener{
            userAddtitionalDetailsFragmentPresenter?.moveSkiptoHome()
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //retainInstance = true;
        networkCall = APICall(mContext)
    }

    companion object {
        fun newInstance(): UserAdditionalDetailsFragment {
            return UserAdditionalDetailsFragment()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }
}