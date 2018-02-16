package com.joveeinfotech.kinship.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.joveeinfotech.kinship.*
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.RegisterPresenterImpl
import kotlinx.android.synthetic.main.activity_user_registration.*

class UserRegistration : AppCompatActivity(), RegisterView {

    var blood_group: String? = null

    var registerPresenter : RegisterPresenterImpl? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        registerPresenter = RegisterPresenterImpl(this, this)

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

        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_blood_group.adapter = dataAdapter

        spinner_blood_group.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,blood_group,Toast.LENGTH_LONG).show()
            }
        }

        button_register.setOnClickListener {
            registerPresenter?.userPhoneNumberAndBloodGroup(editText_phone_number.text.toString(), blood_group!!)
        }
    }

    /*private fun userRegister1() {
        progressDialog = ProgressDialog(this@UserRegistration, R.style.MyAlertDialogStyle)
        progressDialog?.setMessage("Registering...")
        progressDialog?.show()
        mCompositeDisposable=mApiInterface?.UserRegister(editText_phone_number.text.toString(), this!!.blood_group!!)!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { result ->
                            progressDialog?.dismiss()
                            Toast.makeText(applicationContext,"${result.otp}",Toast.LENGTH_LONG).show()
                            //otp = result.otp
                            //user_id = result.user_id
                            if(result.status == true)
                            {
                                confirmotp()
                            }
                        },
                        { error ->
                            progressDialog?.dismiss()
                            //progressBar.visibility=View.GONE
                            Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
                            Toast.makeText(this, error.message, Toast.LENGTH_LONG).show()
                            //displayLog("error")
                        }
                )
    }
*/

    override fun closeActivity() { finish() }
}

