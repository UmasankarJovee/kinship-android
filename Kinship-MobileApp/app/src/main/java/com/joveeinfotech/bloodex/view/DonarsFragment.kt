package com.joveeinfotech.bloodex.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joveeinfotech.bloodex.R
import com.joveeinfotech.bloodex.adapter.DonorsListAdapter
import com.joveeinfotech.bloodex.contract.BloodExContract.*
import com.joveeinfotech.bloodex.presenter.DonorsFragmentPresenterImpl
import kotlinx.android.synthetic.main.fragment_donars.view.*


class DonarsFragment : Fragment(), DonorsFragmentView {

    var mContext : Context? = null

    var view1 : View? = null

    var donorsFragmentPresenterImpl : DonorsFragmentPresenterImpl? = null

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
        view1 =  inflater.inflate(R.layout.fragment_donars, container, false)

        donorsFragmentPresenterImpl = DonorsFragmentPresenterImpl(this,mContext!!)

        return view1
    }

    override fun setAdapterOfDonors(donorsListAdapter: DonorsListAdapter?) {
        view1?.fragment_donars_RecyclerView?.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(mContext)
        view1?.fragment_donars_RecyclerView?.layoutManager = layoutManager
        /*view1?.fragment_donars_RecyclerView?.addOnItemTouchListener(object :RecyclerItemClickListener?.OnItemTouchListener(){
            fun onItemClick(view: View, position: Int) {
                // ...
            }

            fun onItemLongClick(view: View, position: Int) {
                // ...
            }


        })

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(activity, recyclerView, object : RecyclerItemClickListener.OnItemClickListener() {
            fun onItemClick(view: View, position: Int) {
                // ...
            }

            fun onItemLongClick(view: View, position: Int) {
                // ...
            }
        }))*/
        //val listener = { view, position -> Toast.makeText(context, "Position " + position, Toast.LENGTH_SHORT).show() }
        view1?.fragment_donars_RecyclerView?.adapter=donorsListAdapter
    }

    companion object {
        fun newInstance() : DonarsFragment {
            return DonarsFragment()
        }
    }
}
