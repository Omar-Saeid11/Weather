package com.example.weather.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.R


class OnBoardingThirdFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding_third, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.findViewById<ImageView>(R.id.next_btn2).setOnClickListener {
            viewPager?.currentItem = 3
        }

        return view
    }
}