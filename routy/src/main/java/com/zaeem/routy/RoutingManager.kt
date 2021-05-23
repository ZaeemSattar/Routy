/*
 *
 *   Created Zaeem Sattar on 5/23/21 4:06 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 5/23/21 4:06 PM
 * /
 */

package com.zaeem.routy

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.zaeem.routy.extensions.getColorCompat
import com.zaeem.routy.factory.DirectionFactory
import com.zaeem.routy.models.Legs
import com.zaeem.routy.models.Routes
import com.zaeem.routy.models.RoutingMethod
import com.zaeem.routy.models.TravelMode
import com.zaeem.routy.parser.RouteGsonParser
import com.zaeem.routy.routing.RouteDrawer
import com.zaeem.routy.utils.MapUtils
import com.zaeem.routy.utils.PolylineDecoder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RoutingManager {


    private var currentMarker: Marker? = null

    fun drawMarker(
        googleMap: GoogleMap,
        location: LatLng?,
        context: Context,
        resDrawable: Int = R.drawable.ic_location,
        title: String? = null
    ) {

        currentMarker?.let {
            it.position = location
        } ?: run {
            val circleDrawable = ContextCompat.getDrawable(context, resDrawable)
            val markerIcon = MapUtils.getMarkerIconFromDrawable(circleDrawable)
            currentMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(location!!)
                    .title(title)
                    .icon(markerIcon)
            )
        }


    }


    fun moveCameraOnMap(
        googleMap: GoogleMap,
        zoom: Float = 15.5f,
        animate: Boolean = true,
        latLng: LatLng
    ) {
        googleMap.apply {
            if (animate) {
                animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.Builder().target(LatLng(latLng.latitude, latLng.longitude))
                            .zoom(
                                zoom
                            ).build()
                    )
                )
            } else {
                moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
                animateCamera(
                    CameraUpdateFactory.zoomTo(zoom)
                )
            }
        }

    }


    suspend fun drawRouteOnMap(
        googleMap: GoogleMap,
        apiKey: String,
        context: Context,
        source: LatLng,
        destination: LatLng,
        color: Int = context.getColorCompat(R.color.pathColor),
        markers: Boolean = true,
        boundMarkers: Boolean = true,
        polygonWidth: Int = 7,
        travelMode: TravelMode = TravelMode.DRIVING,
        routingMethod: RoutingMethod = RoutingMethod.POINTS,
        defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
        routeEstimations: ((legs: Legs?) -> Unit)? = null,
        polylinePoints: ((List<LatLng>) -> Unit)? = null

    ) {

        // if user need the source and destination markers
        if (markers) {
            context.run {
                drawMarker(googleMap = googleMap, location = source, context = this)
                drawMarker(googleMap = googleMap, location = destination, context = this)
            }
        }

        //creation of polyline with attributes
        val routeDrawer = RouteDrawer.RouteDrawerBuilder(googleMap = googleMap)
            .withColor(color)
            .withWidth(polygonWidth)
            .withAlpha(0.6f)
            .withMarkerIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            .build()

        DirectionFactory.provideDirectionApi().apply {


            withContext(defaultDispatcher) {
                val response = getDirections(
                    start = source, end = destination, //starting and ending point
                    mode = travelMode, //Travel mode
                    apiKey = apiKey
                )

                val r = RouteGsonParser<Routes>().parse(response, Routes::class.java)
                val routes = r.routes



                when (routingMethod) {
                    RoutingMethod.POINTS -> {

                        if (!routes.isNullOrEmpty()) {

                            routes[0].overview_polyline?.points?.let {
                                PolylineDecoder().decodePolyline(it).let { decodedPoints ->
                                    if (decodedPoints.isNotEmpty()) {
                                        polylinePoints?.invoke(decodedPoints)

                                        withContext(Dispatchers.Main)
                                        {
                                            routeDrawer.drawPath(decodedPoints)
                                        }
                                    }
                                }
                            }


                        }

                    }
                    RoutingMethod.Route -> {
                        if (!routes.isNullOrEmpty()) {
                            routes[0].legs?.get(0)?.let {
                                routeEstimations?.invoke(it)
                            }

                            withContext(Dispatchers.Main)
                            {
                                routeDrawer.drawPath(r)

                            }

                        }
                    }
                }


                if (boundMarkers)
                    boundMarkersOnMap(googleMap = googleMap, arrayListOf(source, destination))

            }

        }
    }


    suspend fun getTravelEstimations(
        mapsApiKey: String,
        source: LatLng,
        destination: LatLng,
        travelMode: TravelMode = TravelMode.DRIVING,
        defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
        routeEstimations: ((legs: Legs?) -> Unit)? = null
    ) {
        //API call to get the path points from google
        DirectionFactory.provideDirectionApi().apply {

            withContext(defaultDispatcher) {
                val response = getDirections(
                    source, destination, //starting and ending point
                    travelMode, //Travel mode
                    mapsApiKey //google maps API from GCP, make sure google directions are enabled
                )

                val r = RouteGsonParser<Routes>().parse(response, Routes::class.java)
                r.routes.apply {
                    if (isNullOrEmpty().not()) {
                        this!![0].legs?.get(0).let {
                            routeEstimations?.invoke(it)
                        }
                    }
                }
            }

        }

    }


    suspend fun boundMarkersOnMap(
        googleMap: GoogleMap,
        latLng: ArrayList<LatLng>,
        padding: Int = 5
    ) {
        withContext(Dispatchers.Main)
        {
            val builder = LatLngBounds.Builder()
            for (marker in latLng) {
                builder.include(marker)
            }
            val bounds = builder.build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
            googleMap.apply {
                moveCamera(cameraUpdate)

            }
        }

    }

}