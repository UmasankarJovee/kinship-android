package com.joveeinfotech.kinship.contract

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
interface KinshipContract {
    interface HomeFragmentView{
        fun bloodDonatorInstructions()
        fun bloodRequestorInstructions()
        //fun updatedDetails()
        fun setViewData(count_of_hospitals:String,count_of_donors:String,count_of_users:String)
    }
    interface HomeFragmentPresenterPresenter{
        fun initPresenter()
        fun Click()
    }
}