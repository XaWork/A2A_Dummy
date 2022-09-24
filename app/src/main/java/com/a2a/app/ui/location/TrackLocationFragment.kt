package com.a2a.app.ui.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.HashMap
import javax.inject.Inject

@AndroidEntryPoint
class TrackLocationFragment : Fragment(R.layout.fragment_track_location), OnMapReadyCallback {

    private lateinit var viewBinding: FragmentTrackLocationBinding
    private var mCurrLocationMarker: Marker? = null
    private var mMap: GoogleMap? = null
    lateinit var order: OrderModel.Result
    private val viewModel by viewModels<CustomViewModel>()
    private lateinit var database: DatabaseReference

    @Inject
    lateinit var viewUtils: ViewUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentTrackLocationBinding.bind(view)
        getArgument()

        if (!this::order.isInitialized) {
            viewUtils.showError("Invalid order id")
            findNavController().popBackStack()
        }

        setUpMap()
        database = FirebaseDatabase.getInstance().reference
    }

    private fun getArgument() {
        val args: TrackLocationFragmentArgs by navArgs()
        order = Gson().fromJson(args.order, OrderModel.Result::class.java)
    }

    private fun setUpMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.trackMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {

        mMap = p0

        database.child("userlocation${order.id}").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                print(snapshot.toString())
                val latitude = (snapshot.value as HashMap<*, *>)["latitude"] as? Double
                val longitude = (snapshot.value as HashMap<*, *>)["longitude"] as? Double

                /*if(latitude!=null && longitude!=null) {
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
                }*/
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

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
    }
}