package com.joveeinfotech.kinship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

class UserDetails : AppCompatActivity(), APIListener {

    var networkCall : APICall? = null

    var isCompleteProfile : Boolean? = null
    var isCompleteAddress : Boolean? = null
    var isCompleteAdditionalDetails : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        networkCall = APICall(this)
        val queryParams = HashMap<String, String>()
        queryParams.put("fgf", "jhuy")
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v10/userdetail",queryParams, UserDetailResult::class.java,this, 1, "Authenticating...")
        /*val i= Intent(applicationContext,UserAddress::class.java)
        startActivity(i)*/
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val userDetailResult = response as UserDetailResult
                Log.e("API CALL : ", "inside UserDetails activity and onSuccess")

                isCompleteProfile = userDetailResult.isRegisterUserProfile
                isCompleteAddress = userDetailResult.isRegisterHomeAddress
                isCompleteAdditionalDetails = userDetailResult.isRegisterAdditionalDetails

                if(!userDetailResult.isRegisterUserProfile){
                    val trans = supportFragmentManager.beginTransaction()
                    trans.replace(R.id.user_details_frame_layout,UserProfileFragment.newInstance())
                    trans.commit()
                }else if(!userDetailResult.isRegisterHomeAddress){
                    val trans = supportFragmentManager.beginTransaction()
                    trans.replace(R.id.user_details_frame_layout,UserAddressFragment.newInstance())
                    trans.commit()
                }else if(!userDetailResult.isRegisterAdditionalDetails){
                    val trans = supportFragmentManager.beginTransaction()
                    trans.replace(R.id.user_details_frame_layout,UserAdditionalDetailsFragment.newInstance())
                    trans.commit()
                }

            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onNetworkFailure(from: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getIsCompleteProfile() : Boolean{
        return isCompleteProfile!!
    }
    fun getIsCompleteAddress() : Boolean{
        return isCompleteAddress!!
    }
    fun getIsCompleteAdditionalDetails() : Boolean{
        return isCompleteAdditionalDetails!!
    }
}
