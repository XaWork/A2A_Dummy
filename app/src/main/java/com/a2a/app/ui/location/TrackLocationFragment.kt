package com.a2a.app.ui.location

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.a2a.app.R
import com.a2a.app.data.model.Driver
import com.a2a.app.data.model.OrderModel
import com.a2a.app.data.viewmodel.CustomViewModel
import com.a2a.app.databinding.FragmentTrackLocationBinding
import com.a2a.app.utils.ViewUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

@AndroidEntryPoint
class TrackLocationFragment : Fragment(R.layout.fragment_track_location), OnMapReadyCallback {

    private lateinit var viewBinding: FragmentTrackLocationBinding
    private var mCurrLocationMarker: Marker? = null
    private lateinit var mMap: GoogleMap
    lateinit var order: OrderModel.Result
    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var database: DatabaseReference

    private var originLatitude: Double = 26.9124336
    private var originLongitude: Double = 75.7872709
    private var destinationLatitude: Double = 28.5151087
    private var destinationLongitude: Double = 77.3932163

    // Fetching API_KEY which we wrapped
    private val apiKey = "AIzaSyAj0qAw3RL4AhbO3ly22EFMqThPbm7dBT4"

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentTrackLocationBinding.bind(view)
        //getArgument()

        if (!this::order.isInitialized) {
            viewUtils.showError("Invalid order id")
            findNavController().popBackStack()
        }

        setUpMap()
        //database = FirebaseDatabase.getInstance().reference
    }

    private fun getArgument() {
        val args: TrackLocationFragmentArgs by navArgs()
        order = Gson().fromJson(args.order, OrderModel.Result::class.java)
    }

    private fun setUpMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.trackMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapFragment.getMapAsync {
            mMap = it
            val originLocation = LatLng(originLatitude, originLongitude)
            mMap.addMarker(MarkerOptions().position(originLocation))
            val destinationLocation = LatLng(destinationLatitude, destinationLongitude)
            mMap.addMarker(MarkerOptions().position(destinationLocation))
            val urll = getDirectionURL(originLocation, destinationLocation, apiKey)
            GetDirection(urll).execute()
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 14F))
        }
    }

    override fun onMapReady(p0: GoogleMap) {

        mMap = p0
        val originLocation = LatLng(originLatitude, originLongitude)
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(originLocation))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 18F))

/*
        mMap = p0

        database.child("userlocation${order.id}").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                print(snapshot.toString())
                val latitude = (snapshot.value as HashMap<*, *>)["latitude"] as? Double
                val longitude = (snapshot.value as HashMap<*, *>)["longitude"] as? Double

                *//*if(latitude!=null && longitude!=null) {
                    updateMap(Driver(lat = latitude.toString(), lng = longitude.toString()))
                    mMap!!.drawRouteOnMap(
                        "AIzaSyAj0qAw3RL4AhbO3ly22EFMqThPbm7dBT4", //your API key
                        source = LatLng(latitude, longitude),
                        markers = false,
                        destination = LatLng(0.0, 0.0
                            //order.pickupAddress!!.position.coordinates[0].toDouble(),
                            //order.address!!.position.coordinates[1].toDouble()
                        ),
                        context = context
                    )
                }*//*
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })*/
    }

    private fun getDirectionURL(origin: LatLng, dest: LatLng, secret: String): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val dd: Char = 'c'

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5), (lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }


    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path = ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size) {
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices) {
                lineoption.addAll(result[i])
                lineoption.width(30f)
                lineoption.color(Color.GREEN)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }
/*
    private fun updateMap(data: Driver?) {

        mMap?.let {
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker!!.remove()
            }
            val latLng = data!!.lat?.toDouble()?.let { it1 ->
                data.lng?.toDouble()?.let { it2 ->
                    LatLng(
                        it1,
                        it2
                    )
                }
            }
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng!!)
            markerOptions.title("Current Position")
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.track_icon))
            mCurrLocationMarker = it.addMarker(markerOptions)

            //move map camera
            it.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            it.animateCamera(CameraUpdateFactory.zoomTo(16f))
        }
    }*/
}


class MapData {
    var routes = ArrayList<Routes>()
}

class Routes {
    var legs = ArrayList<Legs>()
}

class Legs {
    var distance = Distance()
    var duration = Duration()
    var end_address = ""
    var start_address = ""
    var end_location = Location()
    var start_location = Location()
    var steps = ArrayList<Steps>()
}

class Steps {
    var distance = Distance()
    var duration = Duration()
    var end_address = ""
    var start_address = ""
    var end_location = Location()
    var start_location = Location()
    var polyline = PolyLine()
    var travel_mode = ""
    var maneuver = ""
}

class Duration {
    var text = ""
    var value = 0
}

class Distance {
    var text = ""
    var value = 0
}

class PolyLine {
    var points = ""
}

class Location {
    var lat = ""
    var lng = ""
}