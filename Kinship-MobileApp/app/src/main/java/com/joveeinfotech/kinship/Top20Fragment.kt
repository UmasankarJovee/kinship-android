package com.joveeinfotech.kinship

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.adapter.Top20ListAdapter
import com.joveeinfotech.kinship.model.GetTop20Result
import com.joveeinfotech.kinship.model.UserAdditionalDetailsResult
import com.joveeinfotech.kinship.model.detailsResult
import kotlinx.android.synthetic.main.fragment_top20.*
import java.util.HashMap


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Top20Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Top20Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Top20Fragment : Fragment(), APIListener {

    var networkCall : APICall? = null

    var mContext : Context? = null

    private var mGetTop20ArrayList: ArrayList<detailsResult>? = null

    var top20ListAdapter : Top20ListAdapter? = null

    override fun onAttach(context: Context) {
        this.mContext = context
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        networkCall = APICall(mContext!!)
        var view1 : View = inflater.inflate(R.layout.fragment_top20, container, false)

        val queryParams = HashMap<String, String>()
        queryParams.put("getTop20", "getTop20")
        networkCall?.APIRequest("api/v1/getTop20", queryParams, GetTop20Result::class.java, this, 1, "Sending your other details...")

        return view1
    }

    override fun onSuccess(from: Int, response: Any) {
        when (from) {
            1 -> { // Send Additional Details
                Log.e("API CALL : ", "inside Main activity and onSuccess when")
                val getTop20Result = response as GetTop20Result
                var df = getTop20Result.details
                mGetTop20ArrayList = ArrayList(df)
                top20ListAdapter = Top20ListAdapter(mGetTop20ArrayList!!, mContext!!)
                fragment_top20_RecyclerView.adapter = top20ListAdapter
            }
        }
    }

    override fun onFailure(from: Int, t: Throwable) {

    }
    override fun onNetworkFailure(from: Int) {
    }

    companion object {
        fun newInstance():Top20Fragment{
            return Top20Fragment()
        }
    }
}
