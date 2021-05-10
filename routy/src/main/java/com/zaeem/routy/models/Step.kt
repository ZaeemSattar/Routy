/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:04 PM
 * /
 */

package com.zaeem.routy.models

import com.google.gson.annotations.SerializedName

data class Step(
    var distance: Distance? = null,
    var duration: Duration? = null,

    @SerializedName("start_location")
    var startLocation: GeoPoint? = null,

    @SerializedName("end_location")
    var endLocation: GeoPoint? = null,

    @SerializedName("html_instructions")
    var htmlInstructions: String? = null,

    @SerializedName("travel_mode")
    var travelMode: String? = null,

    var polyline: OverviewPolyline? = null,

    /*
    * contains detailed directions for walking or driving steps in transit directions. Substeps are only available when travel_mode is set to "transit". The inner steps array is of the same type as steps
    * */
    var steps: List<Step>? = null,

    )