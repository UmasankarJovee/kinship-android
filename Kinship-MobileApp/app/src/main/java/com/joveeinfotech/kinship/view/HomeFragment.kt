package kinship.joveeinfotech.kinship

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import me.relex.circleindicator.CircleIndicator
import java.util.*
import android.R.attr.country



/*
* HomeFragment It is the Home */
class HomeFragment : Fragment(), HomeFragmentView {

    private val imageArrayList = ArrayList<String>()
    private val nameArrayList = ArrayList<String>()
    private val bloodGroupArrayList = ArrayList<String>()
    private val countryArrayList = ArrayList<String>()
    private var mPager: ViewPager? = null
    var mindicator:CircleIndicator?=null
    private var currentPage = 0
   /* private val imageArray = arrayOf<String>("https://www.um.edu.mt/__data/assets/image/0007/305296/varieties/banner.jpg",
            "https://cdn.arstechnica.net/wp-content/uploads/2013/05/donate_blood_rotator.jpg",
            "https://www.discoverwellingborough.co.uk/wp-content/uploads/2016/02/blood-logo.png")*/
    private val imageArray = arrayOf<String>("https://media.npr.org/assets/img/2015/06/14/james-harrison-blood_custom-11081c1f49b5e933ca2c2e2f148a73a4b9df78e3-s900-c85.jpg","https://amazingworldrecords.files.wordpress.com/2010/03/cessnocke28099sphilbaird.jpg")
    private val nameArray= arrayOf<String>("James Harrison","Cessnock’s Phil Baird")
    private val bloodGroupArray= arrayOf<String>("ABO","-")
    private val countryArray= arrayOf<String>("Australia","Australia")
    private lateinit var homeFragmentPresenter: HomeFragmentPresenterImpl
    var view1:View?=null
    /*var imageArray : Array<String> = arrayOf(
    "https://www.um.edu.mt/__data/assets/image/0007/305296/varieties/banner.jpg",
            "https://cdn.arstechnica.net/wp-content/uploads/2013/05/donate_blood_rotator.jpg",
            "https://www.discoverwellingborough.co.uk/wp-content/uploads/2016/02/blood-logo.png")*/

    var alert_blood_donator_instructions_exit: ImageView? = null
    var alert_blood_requestor_instructions_exit: ImageView? = null
    lateinit var mContext:Context

    override fun onAttach(context: Context) {
        this.mContext=context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view1=inflater.inflate(R.layout.fragment_home,container,false)
        val blooddonatorinstructionsTextView=view1?.findViewById<TextView>(R.id.activity_home_fragment_bloodDonatorInstructions_textview)
        val bloodRequestorInstructionsTextView=view1?.findViewById<TextView>(R.id.activity_home_fragment_bloodRequestInstructions_textview)

        /*setIntPreference(mContext,"languageRepeat3",0)
        var language = getIntPreference(mContext, "languageCode3", 0)
        var languageRepeat = getIntPreference(mContext,"languageRepeat3",0)
        if(language == 0 && languageRepeat == 0){
            LocaleHelper.setLocale(mContext,"ta")
            val trans = fragmentManager?.beginTransaction()
            //trans?.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            trans?.replace(R.id.activity_home_frame_layout, HomeFragment.newInstance())
            trans?.commit()
            //setIntPreference(mContext,"languageRepeat3",1)
        }*/
        homeFragmentPresenter= HomeFragmentPresenterImpl(this,mContext)

        //LocaleHelper.setLocale(mContext,"ta")

        //view.activity_home_fragment_ImageSlider_ViewPager.adapter=ImageSliderAdapterClass()
        init()
        blooddonatorinstructionsTextView?.setOnClickListener{
            bloodDonatorInstructions()
        }
        bloodRequestorInstructionsTextView?.setOnClickListener{
            bloodRequestorInstructions()
        }
        return view1
    }
    private fun init() {
        for (i in imageArray.indices)
            imageArrayList.add(imageArray[i])
        for(j in nameArray.indices)
            nameArrayList.add(nameArray[j])
        for(k in bloodGroupArray.indices)
            bloodGroupArrayList.add(bloodGroupArray[k])
        for (l in countryArray.indices)
            countryArrayList.add(countryArray[l])

        mPager = view1?.findViewById<ViewPager>(R.id.activity_home_fragment_ImageSlider_ViewPager) as ViewPager
        mPager!!.adapter = ImageSliderAdapterClass()
        mindicator =view1?.findViewById<CircleIndicator>(R.id.activity_home_fragment_ImageSlider_Indicator)as CircleIndicator
        mindicator?.setViewPager(mPager)

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == imageArray.size) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 2500, 2500)
    }

    /*companion object {

        private var mPager: ViewPager? = null
        private var currentPage = 0
        private val XMEN = arrayOf(R.drawable.index1, R.drawable.index4, R.drawable.index6, R.drawable.index7)
    }*/
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
        var inflater: LayoutInflater? = null
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
           return imageArrayList.size
        }

        override fun instantiateItem(container: ViewGroup, position: Int): View {

            //var imageUrl ="https://www.google.co.in/images/branding/googlelogo/2x/googlelogo_color_120x44dp.png"

            /*val imageView : ImageView = ImageView(mContext)
            Picasso.with(mContext).load(imageArrayList[position]).into(imageView)
            container.addView(imageView,0)
            return imageView*/


            inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val itemView = inflater?.inflate(R.layout.image_and_content_sliding, container,false)

            // Locate the ImageView in viewpager_item.xml
            val name=itemView?.findViewById<TextView>(R.id.image_and_sliding_content_userName_textView)as TextView
            val bloodGroup=itemView?.findViewById<TextView>(R.id.image_and_sliding_content_bloodGroup_textView)as TextView
            val country=itemView?.findViewById<TextView>(R.id.image_and_sliding_content_district_textView)as TextView
            val imgflag= itemView?.findViewById<ImageView>(R.id.flag)as ImageView

            name.setText(nameArrayList[position])
            bloodGroup.setText(bloodGroupArrayList[position])
            country.setText(countryArrayList[position])
            Picasso.with(mContext).load(imageArrayList[position]).into(imgflag)
            container.addView(itemView,0)
            // Capture position and set to the ImageView
            //imgflag.setImageResource(flag[position])

            // Add viewpager_item.xml to ViewPager

            return itemView
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

    /*override fun onDestroyView() {
        super.onDestroyView()
        setIntPreference(mContext,"languageRepeat1",0)
    }*/
}

