package com.example.weather.ui.get_start_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.databinding.FragmentGetStartedBinding
import com.example.weather.ui.Base.BaseFragment
import com.example.weather.ui.weather.WeatherFragment
import com.example.weather.util.PreferencesUtil

class GetStartedFragment : BaseFragment<FragmentGetStartedBinding>() {
    private val weatherFragment = WeatherFragment()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGetStartedBinding
        get() = FragmentGetStartedBinding::inflate
    override val logTag: String
        get() = this::class.java.simpleName

    override fun addCallbacks() {
    }

    override fun setup() {
        binding?.btnStart?.setOnClickListener {
            replaceToFragment(weatherFragment)
        }
        if (PreferencesUtil.latitude.toString()
                .isNotEmpty() && PreferencesUtil.longitude.toString().isNotEmpty()
        ) {
            binding?.btnStart?.isEnabled = true
            Toast.makeText(
                requireContext(),
                "lat : ${PreferencesUtil.latitude} , lon : ${PreferencesUtil.longitude}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun replaceToFragment(fragment: Fragment) {
        if (!(fragment.isAdded))
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }

}