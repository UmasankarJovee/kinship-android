package com.joveeinfotech.kinship

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserProfileEditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserProfileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileEditFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_edit, container, false)
    }

    companion object {
      fun newInstance():UserProfileEditFragment{
          return UserProfileEditFragment()
      }
    }
}// Required empty public constructor
