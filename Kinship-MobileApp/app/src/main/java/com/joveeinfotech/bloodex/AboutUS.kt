package com.joveeinfotech.bloodex

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
//import com.joveeinfotech.kinship.R
import com.joveeinfotech.bloodex.SelectLanguage
import kotlinx.android.synthetic.main.activity_select_language.*

class AboutUS : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us)
        activity_select_language_constraintLayout2_skip_textView.setOnClickListener {
            startActivity(Intent(this@AboutUS, SelectLanguage::class.java))
            finish()
        }
    }
}