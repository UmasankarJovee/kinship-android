package com.joveeinfotech.bloodex

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.joveeinfotech.bloodex.helper.LocaleHelper
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setStringPreference
import com.joveeinfotech.bloodex.view.UserRegistration
import kotlinx.android.synthetic.main.activity_select_language.*
import java.util.ArrayList

class SelectLanguage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        call()
        val categories= ArrayList<String>()
        categories.add(this.getString(R.string.select_language))
        //categories.add("Tamil")
        categories.add("தமிழ்")
        categories.add("English")
        //categories.add("Hindi")
        categories.add("हिंदी")
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activity_select_language_constraintLayout1_selectLanguage_spinner?.adapter = dataAdapter

        activity_select_language_constraintLayout1_selectLanguage_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            @RequiresApi(Build.VERSION_CODES.ECLAIR)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //search_blood_group = categories.get(position).toString()
                //Toast.makeText(applicationContext,categories.get(position).toString(),Toast.LENGTH_LONG).show()

                //CustomToast().normalToast(this@SelectLanguage,categories.get(position).toString())
                var lan = categories.get(position).toString()
                if(lan == "தமிழ்"){
                    LocaleHelper.setLocale(this@SelectLanguage, "ta")
                    setStringPreference(this@SelectLanguage, "selectLanguageString", "Tamil")
                    //this@SelectLanguage.recreate()
                    call()
                    refreshPage()
                }
                else if(lan == "English"){
                    LocaleHelper.setLocale(this@SelectLanguage, "en")
                    setStringPreference(this@SelectLanguage, "selectLanguageString", "English")
                    call()
                    refreshPage()
                }
                else if(lan == "हिंदी"){
                    LocaleHelper.setLocale(this@SelectLanguage, "hi")
                    setStringPreference(this@SelectLanguage, "selectLanguageString", "Hindi")
                    call()
                    refreshPage()
                }

            }
        }

        activity_select_language_constraintLayout2_skip_textView.setOnClickListener {
            startActivity(Intent(this@SelectLanguage, UserRegistration::class.java))
        }
    }
    fun call(){
        val gifImage=findViewById<ImageView>(R.id.activity_select_language_constraintLayout1_globalGif_imageView)as ImageView
        Glide.with(this).load(R.drawable.global).asGif().crossFade().into(gifImage)
    }
    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    fun refreshPage(){
        finish();
        overridePendingTransition( 0, 0);
        startActivity(intent);
        overridePendingTransition( 0, 0);
    }
}