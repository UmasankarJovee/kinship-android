package com.joveeinfotech.kinship.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.joveeinfotech.kinship.*
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.getStringPreference
import com.joveeinfotech.kinship.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.kinship.model.LoginResult
import org.jetbrains.anko.design.snackbar
import java.util.HashMap
import com.joveeinfotech.kinship.utils.CustomToast
import com.joveeinfotech.kinship.utils.SharedData
import com.joveeinfotech.kinship.utils.Validation
import com.joveeinfotech.kinship.view.UserDetails
import com.joveeinfotech.kinship.view.UserRegistration


/**
 * Created by shanmugarajjoveeinfo on 8/2/18.
 */
class LoginPresenterImpl : APIListener, LoginPresenter {

    override fun onFailure(from: Int, t: Throwable) {}
    override fun onNetworkFailure(from: Int) {}

    private var loginView : LoginView
    var session: SharedData? = null

    var mContext: Context
    var networkCall : APICall? = null

    var phone_number : String? = null
    var password : String? = null

    constructor(view: LoginView,context: Context){
        loginView=view
        mContext=context
        initPresenter()
        session = SharedData(mContext)
    }

    override fun initPresenter() {
        networkCall = APICall(mContext)
    }

    override fun navigateActivity() {
        if (session?.isFirstInstall()!!) {
            Log.e("qqqqqqqqqqqqqqqq","call inside if condition in isFirstinstall")
            session?.createFirstInstall()
            val i = Intent(mContext, UserRegistration::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
        if (session?.isLoggedIn()!!) {
            Log.e("qqqqqqqqqqqqqqqq","call inside if condition islogin")
            val i = Intent(mContext, Home::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(i)
            loginView.closeActivity()
        }
    }

    override fun callRegisterActivity() {
        val i=Intent(mContext, UserRegistration::class.java)
        mContext.startActivity(i)
    }
    override fun userPhoneNumberAndPassword(phone_number: String, password: String) {
          this.phone_number=phone_number
          this.password=password
          if(phone_number.trim().isNotEmpty() && password.trim().isNotEmpty())
          {
               if(Validation.isValidPhoneNumber(phone_number)){
                   userLogin(phone_number,password)
               }
               else {
                   CustomToast().alertToast(mContext,"Phone number is not Correct")
               }
          }
          else {
              CustomToast().alertToast(mContext,"Please fill two fields")
          }
    }

    private fun userLogin(phone_number: String, password: String) {
        val queryParams = HashMap<String, String>()
        queryParams.put("phone_number",phone_number)
        queryParams.put("password",password)
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v1/login",queryParams, LoginResult::class.java,this, 1, "Authenticating...")
    }
    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // User Login
                val loginResult = response as LoginResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (loginResult.status) {
                    setStringPreference(mContext,"user_id",loginResult.user_id)
                    session?.createLoginSession(phone_number!!, password!!)
                    CustomToast().normalToast(mContext,loginResult.message)
                    val i= Intent(mContext, UserDetails::class.java)
                    mContext.startActivity(i)
                    loginView.closeActivity()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                } else {
                    //snackbar(this,)
                    snackbar((mContext as Activity).findViewById(android.R.id.content), "Please wait some minute")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }
}