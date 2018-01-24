package com.example.elcot.kinship2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.AppCompatButton
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_user_registration.*

class UserRegistration : AppCompatActivity() {

    var buttonConfirmOTP : AppCompatButton? = null
    var buttonConfirmPassword : AppCompatButton? = null

    var editTextotp : EditText? = null
    var editTextpassword : EditText? = null
    var editTextConfirmPassword : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        register.setOnClickListener{
            //val i= Intent(applicationContext,UserProfile::class.java)
            //startActivity(i)
            confirmotp()
        }

        var categories=ArrayList<String>()
        categories.add("Select Blood Group")
        categories.add("Blood Group (AB+)")
        categories.add("Blood Group (AB-)")
        categories.add("Blood Group (A+)")
        categories.add("Blood Group (A-)")
        categories.add("Blood Group (B+)")
        categories.add("Blood Group (B-")
        categories.add("Blood Group (O+)")
        categories.add("Blood Group (O-)")

        val dataAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_blood_group.adapter=dataAdapter

        /*spinner_blood_group.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
        }*/

    }

    private fun confirmotp() {

        val li=LayoutInflater.from(this)
        val confirmDialog = li.inflate(R.layout.alert_otp_get,null)
        buttonConfirmOTP = confirmDialog.findViewById(R.id.buttonConfirmOTP) as AppCompatButton
        editTextotp = confirmDialog.findViewById(R.id.editTextOtp) as EditText

        val alert = AlertDialog.Builder(this)
        alert.setView(confirmDialog)

        val alertDialog = alert.create()
        alertDialog.show()

        buttonConfirmOTP!!.setOnClickListener{
            alertDialog.dismiss()
            confirmPassword()
            //val i= Intent(applicationContext,UserProfile::class.java)
            //startActivity(i)
        }
    }

    private fun confirmPassword() {

        val li1=LayoutInflater.from(this)
        val confimDialog1 = li1.inflate(R.layout.alert_password_get,null)
        buttonConfirmPassword = confimDialog1.findViewById(R.id.buttonConfirmPassword) as AppCompatButton
        editTextpassword = confimDialog1.findViewById(R.id.editText_password) as EditText
        editTextConfirmPassword = confimDialog1.findViewById(R.id.editText_confirm_password) as EditText

        val alert1 = AlertDialog.Builder(this)
        alert1.setView(confimDialog1)

        val alertDialog1 = alert1.create()
        alertDialog1.show()

        buttonConfirmPassword!!.setOnClickListener{
            alertDialog1.dismiss()
            val i=Intent(applicationContext,UserProfile::class.java)
            startActivity(i)
        }

    }


}
