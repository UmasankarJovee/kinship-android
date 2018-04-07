package com.joveeinfotech.bloodex.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.bloodex.APICall
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.KinshipContract.*
import com.joveeinfotech.bloodex.presenter.UserAdditionalDetailsFragmentPresenterImpl
import com.joveeinfotech.bloodex.utils.CustomToast
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

        view.fragment_user_additional_details_student_radioButton.setOnClickListener{
            view.fragment_user_additional_details_others_radioButton.isChecked=false
            view.fragment_user_additional_details_working_radioButton.isChecked=false
            view.fragment_user_additional_details_othersMentionHere_editText.visibility=View.GONE
            category_of_person="Student"
        }
        view.fragment_user_additional_details_working_radioButton.setOnClickListener{
            view.fragment_user_additional_details_student_radioButton.isChecked=false
            view.fragment_user_additional_details_others_radioButton.isChecked=false
            view.fragment_user_additional_details_othersMentionHere_editText.visibility=View.GONE
            category_of_person="Working"
        }
        view.fragment_user_additional_details_others_radioButton.setOnClickListener{
            view.fragment_user_additional_details_student_radioButton.isChecked=false
            view.fragment_user_additional_details_working_radioButton.isChecked=false
            view.fragment_user_additional_details_othersMentionHere_editText.visibility=View.VISIBLE
        }

        view.fragment_user_additional_details_submit_button.setOnClickListener{

            if(view.fragment_user_additional_details_othersMentionHere_editText.visibility == View.VISIBLE){
                category_of_person=fragment_user_additional_details_othersMentionHere_editText.text.toString()
            }
            if(category_of_person != null) {
                userAddtitionalDetailsFragmentPresenter?.userAdditionalDetails(category_of_person!!, fragment_user_additional_details_phoneNumberOptional_editText.text.toString(),
                        fragment_user_additional_details_emailOptional_editText.text.toString(),
                        fragment_user_additional_details_socialProfileOptional_editText.text.toString())
            }
            else
            {
                CustomToast().alertToast(mContext,"You must select Occupation")
            }
        }
        view.fragment_user_additional_details_skip_textView.setOnClickListener{
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