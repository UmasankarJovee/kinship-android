package kinship.joveeinfotech.bloodex

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.joveeinfotech.bloodex.CircleIndicator
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.getBooleanPreference
import com.joveeinfotech.bloodex.helper.SharedPreferenceHelper.setBooleanPreference
import com.joveeinfotech.bloodex.presenter.*
import com.joveeinfotech.bloodex.utils.Others.DLog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


/*
* HomeFragment It is the Home */
class HomeFragment : Fragment(), HomeFragmentView {

    private val LOCATION_PERMISSION_CONSTANT = 100
    //private val REQUEST_PERMISSION_SETTING = 101
    private val PERMISSION_CALLBACK_CONSTANT = 101
    private val REQUEST_PERMISSION_SETTING = 102
    private var sentToSettings = false
    //private var permissionStatus: SharedPreferences? = null

    private val imageArrayList = ArrayList<String>()
    private val nameArrayList = ArrayList<String>()
    private val bloodGroupArrayList = ArrayList<String>()
    private val countryArrayList = ArrayList<String>()
    private var mPager: ViewPager? = null
    var mindicator: CircleIndicator?=null
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

        DLog("PermissionsFragment : ", "call1")
        view1=inflater.inflate(R.layout.fragment_home,container,false)
        val blooddonatorinstructionsTextView=view1?.findViewById<TextView>(R.id.activity_home_fragment_bloodDonatorInstructions_textview)
        val bloodRequestorInstructionsTextView=view1?.findViewById<TextView>(R.id.activity_home_fragment_bloodRequestInstructions_textview)

        //permissionStatus = getSharedPreferences("permissionStatus", Context.MODE_PRIVATE)

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
        //permissions()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DLog("PermissionsFragment : ", "call2")
        var getPermission = getBooleanPreference(mContext,"locationPermission1",true)
        if(getPermission){
            setBooleanPreference(mContext,"locationPermission1",false)
            permissions()
        }
    }

    private fun permissions() {
       /* if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)){

                //Show Information about why you need the permission
                val builder = android.support.v7.app.AlertDialog.Builder(mContext)
                builder.setTitle("Need Location Permission")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CONSTANT)
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            //} else if (permissionStatus!!.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
            } else if (getBooleanPreference(mContext,Manifest.permission.ACCESS_FINE_LOCATION,false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                val builder = android.support.v7.app.AlertDialog.Builder(mContext)
                builder.setTitle("Need Location Permission")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    sentToSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", "com.joveeinfotech.bloodex", null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                    Toast.makeText(mContext, "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show()
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            } else {
                //just request the permission
                //ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CONSTANT)
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CONSTANT)
            }

            *//*val editor = permissionStatus!!.edit()
            editor.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true)
            editor.commit()*//*
            setBooleanPreference(mContext,Manifest.permission.ACCESS_FINE_LOCATION,true)

        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission()
        }*/
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show Information about why you need the permission
                val builder = android.support.v7.app.AlertDialog.Builder(activity!!)
                builder.setTitle("Need Location Permission")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    askPermission()
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            //} else if (permissionStatus.getBoolean(Manifest.permission.READ_PHONE_STATE, false)) {
            } else if (SharedPreferenceHelper.getBooleanPreference(mContext, Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                val builder = android.support.v7.app.AlertDialog.Builder(activity!!)
                builder.setTitle("Need Location Permission")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    sentToSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity!!.packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                    Toast.makeText(activity, "Go to Permissions to Grant Location", Toast.LENGTH_LONG).show()
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            } else {
                //just request the permission
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_CALLBACK_CONSTANT)
            }
            //txtPermissions.setText("Permissions Required")

            /*val editor = permissionStatus.edit()
            editor.putBoolean(Manifest.permission.READ_PHONE_STATE, true)
            editor.commit()*/
            SharedPreferenceHelper.setBooleanPreference(mContext, Manifest.permission.ACCESS_FINE_LOCATION, true)
        } else {
            //You already have the permission, just go ahead.
            proceedAfterPermission()
        }
    }

    private fun askPermission() {

        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_CALLBACK_CONSTANT)
    }

    private fun proceedAfterPermission() {
        DLog("PermissionsFragment : ", "call3")
        /*homeFragmentPresenter= HomeFragmentPresenterImpl(this,mContext)
        init()*/
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        DLog("PermissionsFragment : ", "call4")
        /*if (requestCode == LOCATION_PERMISSION_CONSTANT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                proceedAfterPermission()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)){
                    //Show Information about why you need the permission
                    val builder = android.support.v7.app.AlertDialog.Builder(mContext)
                    builder.setTitle("Need Storage Permission")
                    builder.setMessage("This app needs storage permission")
                    builder.setPositiveButton("Grant") { dialog, which ->
                        dialog.cancel()

                        //ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_CONSTANT)
                        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CONSTANT)
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                    builder.show()
                } else {
                    Toast.makeText(mContext, "Unable to get Permission", Toast.LENGTH_LONG).show()
                }
            }
        }*/

        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }

            if (allgranted) {
                proceedAfterPermission()
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.READ_PHONE_STATE)) {
                //txtPermissions.setText("Permissions Required")
                val builder = android.support.v7.app.AlertDialog.Builder(activity!!)
                builder.setTitle("Need Location Permission")
                builder.setMessage("This app needs Location permission.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE), PERMISSION_CALLBACK_CONSTANT)
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            } else {
                Toast.makeText(activity, "Unable to get Permission", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        DLog("PermissionsFragment : ", "call5")
        /*if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }*/
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        DLog("PermissionsFragment : ", "call6")
        /*if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //Got Permission
                proceedAfterPermission()
            }
        }*/
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission()
            }
        }
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
        mindicator?.setViewPager(mPager!!)

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

