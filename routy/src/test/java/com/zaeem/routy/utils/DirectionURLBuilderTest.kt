/*
 *
 *   Created Zaeem Sattar on 3/27/21 5:21 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/27/21 5:21 PM
 * /
 */

package com.zaeem.routy.utils

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.lang.StringBuilder

class DirectionURLBuilderTest {


    @Test
    fun createWayPointsTest() {

        val urlBuilder = StringBuilder()

        val waypoints: List<LatLng> = listOf(LatLng(10.11, 12.13), LatLng(10.11, 12.13))

        waypoints.let { list ->

            val LAT_LNG_SEP = "%2C"
            val WAYPOINT_SEP = "%7Cvia:"

            val wayPoints = list.map { "${it.latitude}$LAT_LNG_SEP${it.longitude}" }
                .joinToString(separator = WAYPOINT_SEP)
            urlBuilder.append("&waypoints=via:$wayPoints")

        }
        val way = urlBuilder.toString()

        assertTrue(true)
    }


}