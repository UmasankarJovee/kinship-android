package com.example.elcot.kinship2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnlogin.setOnClickListener{
            val i= Intent(applicationContext,Home::class.java)
            startActivity(i)
        }

        register.setOnClickListener{
            val i=Intent(applicationContext,UserRegistration::class.java)
            startActivity(i)
        }
        //shanmugaraj jovee infotech
    }
}
