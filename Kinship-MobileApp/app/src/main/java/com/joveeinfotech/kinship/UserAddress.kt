package kinship.joveeinfotech.kinship

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.prandex_and_05.userregistration.APICall
import com.example.prandex_and_05.userregistration.APIListener
import org.jetbrains.anko.design.snackbar
import java.util.HashMap

class UserAddress : AppCompatActivity(), APIListener {

    var networkCall : APICall? = null

    private var mCountryResult: ArrayList<CountryResult>? = null
    private var mStateResult: ArrayList<CountryResult>? = null
    private var mDistrictResult: ArrayList<CountryResult>? = null

    var country : String? = null
    var state : String? = null
    var district : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_address)

        networkCall = APICall(this)
        getCountryDetails()
    }

    private fun getCountryDetails() {
        val queryParams = HashMap<String, String>()
        queryParams.put("fieldName", "country")
        Log.e("MAIN ACTIVITY : ","inside button" )
        networkCall?.APIRequest("api/v6/address",queryParams, CountryResult::class.java,this, 1, "Authenticating...")
    }

    override fun onSuccess(from: Int, response: Any) {
        when(from) {
            1 -> { // Get Country
                /*val countryList = response as List<CountryResult>
                mCountryResult = ArrayList(countryList)
                Log.e("API CALL : ", "inside Main activity and onSuccess")

                val dataAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,mCountryResult)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_counry.adapter=dataAdapter

                spinner_counry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        country=mCountryResult?.get(position).toString()
                        Toast.makeText(applicationContext,country,Toast.LENGTH_LONG).show()
                    }
                }*/
                Log.e("API CALL : ", "inside Main activity and onSuccess")
                val registerResult = response as List<CountryResult>

                Toast.makeText(applicationContext,"${registerResult[3]}",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {

    }
    override fun onNetworkFailure(from: Int) {
    }

}