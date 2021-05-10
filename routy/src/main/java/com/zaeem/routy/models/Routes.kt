/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:04 PM
 * /
 */
package com.zaeem.routy.models

data class Routes(
    var status: String? = null,
    var routes: List<Route>? = null,
    var geocoded_waypoints: List<GeocodedWaypoint>? = null


)