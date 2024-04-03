package com.example.weather.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.ui.get_start_fragment.GetStartedFragment
import com.example.weather.util.PreferencesUtil
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val getStartedFragment = GetStartedFragment()
    private var isLocationEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        PreferencesUtil.initPrefUtil(this)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        addFragment(getStartedFragment)
        checkPermission()
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )

        } else {
            checkLocationEnabled()
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        val task = fusedLocationProviderClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)

        task.addOnCompleteListener {

            if (it.isSuccessful && it.result != null) {
                PreferencesUtil.latitude = it.result.latitude.toString()
                PreferencesUtil.longitude = it.result.longitude.toString()
                getStartedFragment.binding?.btnStart?.isEnabled = true
                Toast.makeText(
                    applicationContext,
                    "lat : ${it.result.latitude} , lon : ${it.result.longitude}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }


    private fun checkLocationEnabled() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isLocationEnabled) {
            // Prompt the user to enable location
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            val client: SettingsClient = LocationServices.getSettingsClient(this)
            val task: Task<LocationSettingsResponse> =
                client.checkLocationSettings(builder.build())

            task.addOnCompleteListener {
                // Location settings are satisfied, fetch the location
                try {

                    task.getResult(ApiException::class.java)
                    fetchLocation()
                } catch (exception: ApiException) {


                    if (exception is ResolvableApiException) {
                        // Location settings are not satisfied, show a dialog to enable location
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult()
                            exception.startResolutionForResult(this@MainActivity, 102)
                        } catch (sendEx: IntentSender.SendIntentException) {
                            // Ignore the error
                        }

                    }

                }

            }
        } else {
            fetchLocation()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkLocationEnabled()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                // Location is enabled, fetch the location
                fetchLocation()
            } else {
                // Location is not enabled, handle accordingly
                checkLocationEnabled()
            }
        }
    }

    private fun addFragment(fragment: Fragment) {
        if (!fragment.isAdded)
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
    }
}