/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:04 PM
 * /
 */


package com.zaeem.routy.models

data class Route(
    var bounds: Bounds? = null,
    var legs: List<Legs>? = null,
    var summary: String? = null,
    var copyrights: String? = null,
    var overview_polyline: OverviewPolyline? = null,
    var waypoint_order: List<Int>? = null,
    var fare: List<Fare>? = null,

)