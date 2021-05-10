/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:10 PM
 * /
 */
package com.zaeem.routy.routing

import android.graphics.Color
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.zaeem.routy.models.Routes


class RouteDrawer private constructor(builder: RouteDrawerBuilder) : DrawerApi {

    private val alpha: Float
    private val pathWidth: Int
    private val pathColor: Int
    private val bitmapDescriptor: BitmapDescriptor
    private val googleMap: GoogleMap

    companion object {
        private const val DEFAULT_MARKER_ALPHA = 1f
        private const val DEFAULT_PATH_WIDTH = 5
        private const val DEFAULT_PATH_COLOR = Color.RED
    }

    init {
        googleMap = builder.googleMap
        alpha = builder.alpha
        pathWidth = builder.pathWidth
        pathColor = builder.pathColor
        bitmapDescriptor = builder.bitmapDescriptor
    }

    override fun drawPath(routes: Routes) {
        var polylineOptions: PolylineOptions?

        for (route in routes.routes!!) {
            for (legs in route.legs!!) {
                polylineOptions = PolylineOptions()
                for (step in legs.steps!!) {
                    polylineOptions.add(
                        LatLng(
                            step.startLocation?.lat!!,
                            step.startLocation?.lng!!
                        )
                    )
                    polylineOptions.width(pathWidth.toFloat())
                    polylineOptions.color(pathColor)
                    googleMap.addPolyline(polylineOptions)
                }
            }
        }
    }

    override fun drawPath(routes: List<LatLng>) {


        val polylineOptions = PolylineOptions()

        for (route in routes) {
            polylineOptions.add(
                route
            )
            polylineOptions.width(pathWidth.toFloat())
            polylineOptions.color(pathColor)
            googleMap.addPolyline(polylineOptions)
        }

    }

    class RouteDrawerBuilder(internal val googleMap: GoogleMap) {
        internal var bitmapDescriptor: BitmapDescriptor
        internal var pathWidth: Int
        internal var pathColor: Int
        internal var alpha: Float

        fun withColor(pathColor: Int): RouteDrawerBuilder {
            this.pathColor = pathColor
            return this
        }

        fun withWidth(pathWidth: Int): RouteDrawerBuilder {
            this.pathWidth = pathWidth
            return this
        }

        fun withMarkerIcon(bitmapDescriptor: BitmapDescriptor): RouteDrawerBuilder {
            this.bitmapDescriptor = bitmapDescriptor
            return this
        }

        fun withAlpha(alpha: Float): RouteDrawerBuilder {
            this.alpha = alpha
            return this
        }

        fun build(): RouteDrawer {
            return RouteDrawer(this)
        }

        init {
            pathWidth = DEFAULT_PATH_WIDTH
            pathColor = DEFAULT_PATH_COLOR
            alpha = DEFAULT_MARKER_ALPHA
            bitmapDescriptor =
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
        }
    }
}