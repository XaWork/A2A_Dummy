package com.a2a.app.ui.address

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.a2a.app.PermissionUtils
import com.a2a.app.R
import com.a2a.app.databinding.LocationPickerBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.system.exitProcess


class LocationPickerActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private val AUTOCOMPLETE_REQUEST_CODE = 1000
    lateinit var googleMap: GoogleMap
    var addresses: List<Address>? = null
    private val MIN_TIME: Long = 400
    private var MIN_DISTANCE = 1000f
    private var lat:Double? = null
    private var long:Double? = null
    lateinit var binding: LocationPickerBinding
    lateinit var locationManager:LocationManager
    lateinit var geocoder:Geocoder
    private var permissionDoNotAllowed = false
    var gps_enabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LocationPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        /*TedPermission.with(this)
            .setPermissions(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {

                    gps_enabled = try {
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    } catch (ex: Exception) {
                        false
                    }

                    if (!gps_enabled) {
                        MaterialAlertDialogBuilder(this@LocationPickerActivity)
                            .setTitle("Location Disabled")
                            .setCancelable(false)
                            .setMessage("Location services are disabled")
                            .setPositiveButton("Start") { dialog, _ ->
                                finish()
                                startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                            .show()
                    }else{
                        setupMap()
                    }
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    finish()
                }
            }).check()*/

        if(checkLocationPermission()){
            Log.d("location", "Allowed location permission")
            if (!gps_enabled) {
                Log.d("location", "Gps is not enabled")
                MaterialAlertDialogBuilder(this@LocationPickerActivity)
                    .setTitle("Location Disabled")
                    .setCancelable(false)
                    .setMessage("Location services are disabled")
                    .setPositiveButton("Start") { dialog, _ ->
                        finish()
                        startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                    .show()
            }else{
                Log.d("location", "Gps enabled")
                setupMap()
            }
        }

        binding.save.setOnClickListener {
            if(lat!=null && long!=null){
                val resultIntent = Intent()
                resultIntent.putExtra("lat", lat!!)
                resultIntent.putExtra("long", long!!)
                setResult(RESULT_OK, resultIntent)
                finish()
            }else{
                Toast.makeText(this, "Please select a location", Toast.LENGTH_LONG).show()
            }
        }

        binding.searchContainer.setOnClickListener {
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS, Place.Field.NAME)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN")
                .build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }

    }

    private fun checkLocationPermission(): Boolean {
        val permissionLocation1 =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )

        val permissionLocation2 =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )

        val listPermissionsNeeded: ArrayList<String> = ArrayList()

        if (permissionLocation2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (permissionLocation1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            PermissionUtils.requestPermission(
                this@LocationPickerActivity, listPermissionsNeeded,
                1
            )
            return false;
        }
        return true;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val permissionReturn: Int = ContextCompat.checkSelfPermission(
            this@LocationPickerActivity,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.e("location", "onRequestPermissionsResult: $permissionReturn")

                    if (ContextCompat.checkSelfPermission(
                            this@LocationPickerActivity,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        gps_enabled = try {
                            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                        } catch (ex: Exception) {
                            false
                        }
                    }
                } else {
                    if(!permissionDoNotAllowed){
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                this@LocationPickerActivity,
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) {
                            checkLocationPermission()
                            /*Toast.makeText(
                                this,
                                "Please allow location permission then you can able to access app",
                                Toast.LENGTH_SHORT
                            ).show()*/
                        } else {
                            permissionDoNotAllowed = true
                            MaterialAlertDialogBuilder(this@LocationPickerActivity)
                                .setTitle("Location Disabled")
                                .setCancelable(false)
                                .setMessage("Location services are disabled")
                                .setPositiveButton("Start") { _, _ ->
                                    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                                    //check
                                    //checkLocationPermission()
                                }
                                //startActivity(Intent(this@SplashActivity, SplashActivity::class.java))
                                .setNegativeButton(getString(R.string.alert_cancel)) { _, _ ->
                                    exitProcess(
                                        0
                                    )
                                }
                                .show()
                        }
                    }

                }
            }
        }
    }

    private fun setupMap(){
        Log.d("map", "inside set up map function")
        geocoder = Geocoder(this, Locale.getDefault())
        val mapFragment = this@LocationPickerActivity.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            MIN_TIME,
            MIN_DISTANCE,
            this
        )
    }

    override fun onLocationChanged(location: Location) {
        binding.loader.visibility = View.GONE
        val latLng = LatLng(location.latitude, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 20f)
        googleMap.animateCamera(cameraUpdate)
        locationManager.removeUpdates(this)

        googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptorFromVector(this, R.drawable.pick_location))
                .draggable(true)
        )
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            lat = latLng.latitude
            long = latLng.longitude
            val address = addresses?.first()
            binding.address1.text = buildString {
                address?.run {
                    appendln(this.getAddressLine(0))
                    appendln(this.locality)
                    append(this.adminArea)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            finish()
            Toast.makeText(this@LocationPickerActivity, "Retry!", Toast.LENGTH_LONG).show()
        }

        googleMap.setOnMarkerDragListener(object : OnMarkerDragListener {
            override fun onMarkerDragStart(arg0: Marker) {

            }

            override fun onMarkerDragEnd(arg0: Marker) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.position))
                addresses = geocoder.getFromLocation(
                    arg0.position.latitude,
                    arg0.position.longitude,
                    1
                )
                val ad2 = addresses?.first()
                binding.address1.text = buildString {
                    ad2?.run {
                        appendln(this.getAddressLine(0))
                        appendln(this.locality)
                        append(this.adminArea)
                    }
                }
                lat = arg0.position.latitude
                long = arg0.position.longitude
            }

            override fun onMarkerDrag(arg0: Marker) {
            }
        })
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(
                intrinsicWidth,
                intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        try{
                            val place = Autocomplete.getPlaceFromIntent(data)
                            googleMap.clear()
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 20.0f));
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(place.latLng!!)
                                    .icon(bitmapDescriptorFromVector(this@LocationPickerActivity, R.drawable.pick_location))
                                    .draggable(true)
                            )
                            addresses = geocoder.getFromLocation(place.latLng!!.latitude, place.latLng!!.longitude, 1)
                            lat = place.latLng!!.latitude
                            long = place.latLng!!.longitude
                            val address = addresses?.first()
                            binding.address1.text = buildString {
                                address?.run {
                                    appendln(this.getAddressLine(0))
                                    appendln(this.locality)
                                    append(this.adminArea)
                                }
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                            Toast.makeText(this@LocationPickerActivity, "Retry later!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        status.statusMessage?.let { it1 -> Log.i("LocationPicker", it1) }
                    }
                }
                Activity.RESULT_CANCELED -> {
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
