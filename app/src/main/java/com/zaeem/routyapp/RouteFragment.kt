/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:00 PM
 * /
 */

package com.zaeem.routyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import drawRouteOnMap
import kotlinx.coroutines.launch
import moveCameraOnMap


class RouteFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialized google maps
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        this.googleMap = p0


        /*doctor hospital to emporium*/
        val source = LatLng(31.478889, 74.281629) //starting point (LatLng)
        val destination = LatLng(31.468462, 74.266985) // ending point (LatLng)


        /*Hamid latif to jinnah hospital*/
        val sourceNew = LatLng(31.518957, 74.335228) //starting point (LatLng)
        val destinationNew = LatLng(31.486838, 74.293533) // ending point (LatLng)

        //if you only want the Estimates (Distance & Time of arrival)

/*
        GlobalScope.launch {
            getTravelEstimations(
                mapsApiKey = getString(R.string.google_maps_key),
                source = source,
                destination = destination,
            ) { estimates ->
                estimates?.let {
                    //Google Estimated time of arrival
                    Log.d(TAG, "ETA: ${it.duration?.text}, ${it.duration?.value}")
                    //Google suggested path distance
                    Log.d(TAG, "Distance: ${it.distance?.text}, ${it.distance?.text}")

                } ?: Log.e(TAG, "Nothing found")
            }
        }
*/


        googleMap?.run {
            //if you want to move the map on specific location
//            moveCameraOnMap(latLng = source)

            //if you want to drop a marker of maps, call it
            /* drawMarker(location = source, context = requireContext(), title = "test marker")*/

            //if you only want to draw a route on maps
            //Called the drawRouteOnMap extension to draw the polyline/route on google maps


            lifecycleScope.launch {
                drawRouteOnMap(
                    getString(R.string.google_maps_key),
                    source = sourceNew,
                    destination = destinationNew,
                    context = context!!,

                    )
            }

            //if you only want to draw a route on maps and also need the ETAs then implement the EstimationsCallBack and pass the ref like this
            //Called the drawRouteOnMap extension to draw the polyline/route on google maps

            /*   lifecycleScope.launch {
                   drawRouteOnMap(
                       getString(R.string.google_maps_key),
                       source = source,
                       destination = destination,
                       context = context!!,
                       routeEstimations = { estimations ->
                           {

                           }
                       }, polylinePoints = { points ->

                       }
                   )
               }
*/
        }
    }

    companion object {
        var TAG = "ROUTY_TAG"
    }
}
