package com.joveeinfotech.kinship.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.kinship.APICall
import com.joveeinfotech.kinship.R
import com.joveeinfotech.kinship.adapter.Top20ListAdapter
import com.joveeinfotech.kinship.contract.KinshipContract.*
import com.joveeinfotech.kinship.presenter.Top20FragmentPresenterImpl
import kotlinx.android.synthetic.main.fragment_top20.*
import kotlinx.android.synthetic.main.fragment_top20.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Top20Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Top20Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Top20Fragment : Fragment(), Top20FragmentView {

    var mContext : Context? = null

    var view1 : View? = null

    var top20FragmentPresenterImpl : Top20FragmentPresenterImpl? = null

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
        //return inflater.inflate(R.layout.alert_address_details, container, false)
        view1 = inflater.inflate(R.layout.fragment_top20, container, false)

        top20FragmentPresenterImpl = Top20FragmentPresenterImpl(this,mContext!!)

        //view1.fragment_top20_RecyclerView.adapter

        return view1
    }

    override fun setAdapterOfTop20(top20ListAdapter: Top20ListAdapter?) {
        view1?.fragment_top20_RecyclerView?.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(mContext)
        view1?.fragment_top20_RecyclerView?.layoutManager = layoutManager
        view1?.fragment_top20_RecyclerView?.adapter=top20ListAdapter
    }

    companion object {
        fun newInstance(): Top20Fragment {
            return Top20Fragment()
        }
    }
}
