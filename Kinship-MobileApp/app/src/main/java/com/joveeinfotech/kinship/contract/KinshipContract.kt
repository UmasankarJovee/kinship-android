package com.joveeinfotech.kinship.contract

/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
interface KinshipContract {
    interface HomeFragmentView{
        fun bloodDonatorInstructions()
        fun bloodRequestorInstructions()
        fun updatedDetails()
    }
    interface HomeFragmentPresenterPresenter{
        fun initPresenter()
    }
}