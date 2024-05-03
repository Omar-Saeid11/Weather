package com.example.weather.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.R
import com.example.weather.ui.onboarding.screens.OnBoardingFirstFragment
import com.example.weather.ui.onboarding.screens.OnBoardingFourthFragment
import com.example.weather.ui.onboarding.screens.OnBoardingSecondFragment
import com.example.weather.ui.onboarding.screens.OnBoardingThirdFragment

class ViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf(
            OnBoardingFirstFragment(),
            OnBoardingSecondFragment(),
            OnBoardingThirdFragment(),
            OnBoardingFourthFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        return view
    }

    private inner class ViewPagerAdapter(
        private val fragments: ArrayList<Fragment>,
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
    }
}
