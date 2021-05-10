/*
 *
 *   Created Zaeem Sattar on 3/23/21 11:06 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 11:06 PM
 * /
 */

package com.zaeem.routy.utils

import com.google.android.gms.maps.model.LatLng
import com.zaeem.routy.enums.Boolean
import com.zaeem.routy.enums.Units
import com.zaeem.routy.models.TravelMode
import java.lang.StringBuilder
import java.util.*


class DirectionURLBuilder private constructor(
    val origin: LatLng?,
    val destination: LatLng?,
    val key: String?,
    val sensor: String?,
    val metric: String?,
    val mode: String?,
    private var waypoints: List<LatLng>?


) {


    class Builder(
        private var origin: LatLng?,
        private var destination: LatLng?,
        private var key: String?,
        private var mode: TravelMode

    ) {

        private var sensor: Boolean = Boolean.FALSE
        private var metric: Units = Units.METRIC
        private var waypoints: List<LatLng>? = null

        fun origin(origin: LatLng) =
            apply { this.origin = origin }

        fun destination(destination: LatLng) =
            apply { this.destination = destination }

        fun key(key: String) =
            apply { this.key = key }

        fun sensor(sensor: Boolean) =
            apply { this.sensor = sensor }

        fun metric(metric: Units) =
            apply { this.metric = metric }

        fun mode(mode: TravelMode) = apply {
            this.mode = mode
        }

        fun waypoints(waypoints: List<LatLng>) = apply {
            this.waypoints = waypoints;
        }

        private fun build() =
            DirectionURLBuilder(
                origin,
                destination,
                key,
                sensor.value,
                metric.value,
                mode.name,
                waypoints
            )

        fun buildURL(): String {

            val item = build()

            val urlBuilder = StringBuilder()

            item.origin?.let {
                urlBuilder.append("origin=${it.latitude},${it.longitude}")
            }

            item.destination?.let {
                urlBuilder.append("&destination=${it.latitude},${it.longitude}")
            }

            item.key?.let {
                urlBuilder.append("&key=$it")
            }

            item.sensor?.let {
                urlBuilder.append("&sensor=$it")
            }

            item.metric?.let {
                urlBuilder.append("&units=$it")

            }

            item.mode?.let {
                urlBuilder.append("&mode=" + it.toLowerCase(Locale.getDefault()))
            }

            item.waypoints?.let { list ->

                val LAT_LNG_SEP = "%2C"
                val WAYPOINT_SEP = "%7Cvia:"

                val wayPoints =
                    list.joinToString(separator = WAYPOINT_SEP) { "${it.latitude}$LAT_LNG_SEP${it.longitude}" }
                urlBuilder.append("&waypoints=via:$wayPoints")

            }


            return urlBuilder.toString()

        }

    }
}