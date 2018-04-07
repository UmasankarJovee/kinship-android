package com.joveeinfotech.bloodex

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import kotlinx.android.synthetic.main.activity_empty1.*

class EmptyActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty1)

        button_ip.setOnClickListener{
            var ip = editText_ip.text.toString()
            setStringPreference(this,"ip",ip)
            var i = Intent(applicationContext,SplashScreen::class.java)
            startActivity(i)
        }
    }
}
