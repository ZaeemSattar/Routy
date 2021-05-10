/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:10 PM
 * /
 */



import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.zaeem.routy.factory.DirectionFactory
import com.zaeem.routy.R
import com.zaeem.routy.extensions.getColorCompat
import com.zaeem.routy.models.Legs
import com.zaeem.routy.models.Route
import com.zaeem.routy.models.Routes
import com.zaeem.routy.models.TravelMode
import com.zaeem.routy.parser.RouteGsonParser
import com.zaeem.routy.routing.RouteDrawer
import com.zaeem.routy.routing.DirectionApiApiImpl
import com.zaeem.routy.utils.MapUtils
import com.zaeem.routy.utils.PolylineDecoder
import kotlinx.coroutines.*


/**
 * Extension function to draw a marker.
 * @param location latLng where the marker is required
 * @param resDrawable The image/drawable of marker
 * @param title optional title of marker
 * @author Zaeem Sattar.
 */
fun GoogleMap.drawMarker(
    location: LatLng?,
    context: Context,
    resDrawable: Int = R.drawable.ic_location,
    title: String? = null
) {
    val circleDrawable = ContextCompat.getDrawable(context, resDrawable)
    val markerIcon = MapUtils.getMarkerIconFromDrawable(circleDrawable)
    addMarker(
        MarkerOptions()
            .position(location!!)
            .title(title)
            .icon(markerIcon)
    )
}

/**
 * Extension function to zoom on map.
 * @param zoom zoom level, by default its 15.5f
 * @param animate if animation is required, by default it's true
 * @param latLng latLng where the zoom is required
 * @author Zaeem Sattar.
 */
fun GoogleMap.moveCameraOnMap(
    zoom: Float = 15.5f,
    animate: Boolean = true,
    latLng: LatLng
) {
    if (animate) {
        animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder().target(LatLng(latLng.latitude, latLng.longitude)).zoom(
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


/**
 * Extension function to draw path/route on map.
 * @param apiKey google maps API from GCP, make sure google directions are enabled
 * @param context Context
 * @param source source point from where the path is required
 * @param destination destination point till where the path is required
 * @param color not required as by default = #6200EE
 * @param markers not required as by default = true
 * @param boundMarkers not required as by default = true
 * @param polygonWidth not required as by default = 13
 * @param travelMode not required as by default = DRIVING, can be DRIVING, WALKING, BICYCLING, TRANSIT
 * @param @Deprecated("Use lambda instead of interface")
 * estimations: ((Legs?) -> Unit)? = null //To get the Estimations(Time of arrival and distance) please call the lambda to get the values
 * @author Zaeem Sattar.
 */
suspend fun GoogleMap.drawRouteOnMap(
    apiKey: String,
    context: Context,
    source: LatLng,
    destination: LatLng,
    color: Int = context.getColorCompat(R.color.pathColor),
    markers: Boolean = true,
    boundMarkers: Boolean = true,
    polygonWidth: Int = 7,
    travelMode: TravelMode = TravelMode.DRIVING,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.Default,
    routeEstimations: ((legs: Legs?) -> Unit)? = null,
    polylinePoints: ((List<LatLng>) -> Unit)? = null

) {

    // if user need the source and destination markers
    if (markers) {
        context.run {
            drawMarker(location = source, context = this)
            drawMarker(location = destination, context = this)
        }
    }

    //creation of polyline with attributes
    val routeDrawer = RouteDrawer.RouteDrawerBuilder(this)
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

            if (!routes.isNullOrEmpty()) {
                routes[0].legs?.get(0)?.let {
                    routeEstimations?.invoke(it)
                }
                routes[0].overview_polyline?.points?.let {
                    PolylineDecoder().decodePolyline(it).let { decodedPoints ->
                        if (decodedPoints.isNotEmpty()) {
                            polylinePoints?.invoke(decodedPoints)
                        }
                    }
                }

                routeDrawer.drawPath(r)
                // if user requires to bound the markers with padding
                if (boundMarkers)
                    boundMarkersOnMap(arrayListOf(source, destination))
            }
        }

    }
}


/**
 * Extension function to get ETA(Estimated time of arrival). Just call the method as simple getTravelEstimations(.......)
in your activity and get the ETA, don't forget to implement the Estimations interface in that view.
 * @param mapsApiKey google maps API from GCP, make sure google directions are enabled
 * @param context Context
 * @param source source point from where the path is required
 * @param destination destination point till where the path is required
 * @param travelMode not required as by default = DRIVING, can be DRIVING, WALKING, BICYCLING, TRANSIT
 * @param estimates lambda to get the estimates data from google
 * @author Zaeem Sattar.
 */
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

/**
 * Extension function to bound all the markers on map.
 * @param padding by default it's 425
 * @param latLng array of all the latlng that are required to bound.
 * @author Zaeem Sattar.
 */
fun GoogleMap.boundMarkersOnMap(
    latLng: ArrayList<LatLng>,
    padding: Int = 5
) {
    val builder = LatLngBounds.Builder()
    for (marker in latLng) {
        builder.include(marker)
    }
    val bounds = builder.build()
    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
    moveCamera(cameraUpdate)
}
