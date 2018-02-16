package kinship.joveeinfotech.kinship

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.HomeFragmentView
import com.joveeinfotech.kinship.presenter.HomeFragmentPresenterImpl

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/*
* HomeFragment It is the Home */
class HomeFragment : Fragment(), HomeFragmentView {

    private lateinit var homeFragmentPresenter: HomeFragmentPresenterImpl
    var imageArray : Array<String> = arrayOf(
    "https://www.um.edu.mt/__data/assets/image/0007/305296/varieties/banner.jpg",
            "https://cdn.arstechnica.net/wp-content/uploads/2013/05/donate_blood_rotator.jpg",
            "https://www.discoverwellingborough.co.uk/wp-content/uploads/2016/02/blood-logo.png")

    var alert_blood_donator_instructions_exit: ImageView? = null
    var alert_blood_requestor_instructions_exit: ImageView? = null
    lateinit var mContext:Context

    override fun onAttach(context: Context) {
        this.mContext=context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view:View=inflater.inflate(R.layout.fragment_home,container,false)
        val blooddonatorinstructionsTextView=view.findViewById<TextView>(R.id.activity_home_fragment_bloodDonatorInstructions_textview)
        val bloodRequestorInstructionsTextView=view.findViewById<TextView>(R.id.activity_home_fragment_bloodRequestInstructions_textview)

        homeFragmentPresenter= HomeFragmentPresenterImpl(this,mContext)
        view.activity_home_fragment_ImageSlider_ViewPager.adapter=ImageSliderAdapterClass()
        blooddonatorinstructionsTextView.setOnClickListener{
            bloodDonatorInstructions()
        }
        bloodRequestorInstructionsTextView.setOnClickListener{
            bloodRequestorInstructions()
        }
        return view
    }

    override fun bloodDonatorInstructions(){
        val layoutinflater=LayoutInflater.from(mContext)
        val conformDialog=layoutinflater.inflate(R.layout.alert_blood_donator_instructions,null)
        val alert=AlertDialog.Builder(mContext)
        alert_blood_donator_instructions_exit=conformDialog.findViewById(R.id.alert_blood_donator_instructions_exit_ImageView)
        alert.setView(conformDialog)
        val alertDialog=alert.create()
        alertDialog.show()

        alert_blood_donator_instructions_exit!!.setOnClickListener{
            alertDialog.dismiss()
        }
    }

    override fun bloodRequestorInstructions(){
        val layoutinflater=LayoutInflater.from(mContext)
        val conformDialog=layoutinflater.inflate(R.layout.alert_blood_requestor_instructions,null)
        val alert=AlertDialog.Builder(mContext)
        alert_blood_requestor_instructions_exit=conformDialog.findViewById(R.id.alert_blood_requestor_instructions_exit_ImageView)
        alert.setView(conformDialog)
        val alertDialog=alert.create()
        alertDialog.show()

        alert_blood_requestor_instructions_exit!!.setOnClickListener{
            alertDialog.dismiss()
        }
    }


    inner class ImageSliderAdapterClass : PagerAdapter(){
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
           return imageArray.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            //var imageUrl ="https://www.google.co.in/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png"

            val imageView : ImageView = ImageView(mContext)
            Picasso.with(mContext).load(imageArray[position]).into(imageView)
            container.addView(imageView,0)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as ImageView)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        //networkCall = APICall(mContext)
    }

    /*override fun updatedDetails(){
        homeFragmentPresenter.Click()
        *//*val queryParams = HashMap<String, String>()
        queryParams.put("nothing", "hello")
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v0/updated_detail_of_home",queryParams, UpdateDetailsResult::class.java,this, 1, "Fetching...")*//*
    }*/

    override fun setViewData(count_of_hospitals: String, count_of_donors: String, count_of_users: String) {
        activity_home_fragment_hospitals_count_TextView?.text=count_of_hospitals
        activity_home_fragment_donator_count_TextView?.text=count_of_donors
        activity_home_fragment_users_count_TextView?.text=count_of_users
    }
    companion object {
        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }

    /*override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // Home Page Contents
                val result = response as UpdateDetailsResult
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                if (result.status) {
                    activity_home_fragment_hospitals_count_TextView?.text=result.count_of_hospitals.toString()
                    activity_home_fragment_donator_count_TextView?.text=result.count_of_donors.toString()
                    activity_home_fragment_users_count_TextView?.text=result.count_of_users.toString()
                    Log.e("API CALL : ", "inside Main activity and onSucces and if condition")
                    //Toast.makeText(applicationContext, "You are Registered ${registerResult.status}", Toast.LENGTH_SHORT).show()
                } else {
                    //snackbar(this,)
                    //snackbar(this.findViewById(android.R.id.content), "Please wait some minutes")
                    //Log.e("API CALL : ","inside Main activity and onSuccess else condition")
                    //Log.d(TAG, "Something missing")
                }
            }
        }
    }*/
}

