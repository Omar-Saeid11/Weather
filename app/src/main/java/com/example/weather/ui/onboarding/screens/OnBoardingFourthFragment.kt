package com.example.weather.ui.onboarding.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.ui.get_start_fragment.GetStartedFragment
import com.example.weather.util.PreferencesUtil

class OnBoardingFourthFragment : Fragment() {
    private val getStartedFragment = GetStartedFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding_fourth, container, false)

        view.findViewById<ImageView>(R.id.next_btn3).setOnClickListener {
            replaceToFragment(getStartedFragment)
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    private fun replaceToFragment(fragment: Fragment) {
        if (!(fragment.isAdded))
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

}