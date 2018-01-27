package com.example.elcot.kinship2

import android.app.FragmentManager
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_blood_donator_instructions.*

class HomeFragment : Fragment() {

    //var mApiInterface : ApiInterface? = null
    //private var mCompositeDisposable : Disposable? = null
   // val progressBar : ProgressBar? = null
    var activity_home_fragment_hospitals_count_TextView : TextView? = null
    var activity_home_fragment_users_count_TextView : TextView? = null
    var activity_home_fragment_donator_count_TextView : TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        var view:View=inflater.inflate(R.layout.activity_home_fragment,container,false)
       // mApiInterface=RetrofitClient.getClient()
        //updatedDetails()
        val blooddonatorinstructionsTextView=view.findViewById<TextView>(R.id.activity_home_fragment_bloodDonatorInstructions_textview)
        blooddonatorinstructionsTextView.setOnClickListener{
            val fragment = BloodDonatorInstructionsFragment()
            val fragmentManager = fragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.frame_layout,fragment)
            fragmentTransaction?.commit()
        }
        return view
    }


    private fun updatedDetails() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)




    }

    companion object {
        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }
}
