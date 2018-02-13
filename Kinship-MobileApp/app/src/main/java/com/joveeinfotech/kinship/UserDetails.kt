package com.joveeinfotech.kinship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class UserDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.user_details_frame_layout,UserAdditionalDetailsFragment.newInstance())
        trans.commit()

        /*val i= Intent(applicationContext,UserAddress::class.java)
        startActivity(i)*/
    }
}
