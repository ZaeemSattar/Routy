/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:04 PM
 * /
 */

package com.zaeem.routy.models

import com.google.gson.annotations.SerializedName

data class Legs(
    @SerializedName("start_address")
    var startAddress: String? = null,

    @SerializedName("end_address")
    var endAddress: String? = null,

    @SerializedName("start_location")
    var startLocation: GeoPoint? = null,

    @SerializedName("end_location")
    var endLocation: GeoPoint? = null,

    var steps: List<Step>? = null,
    var distance: Distance? = null,
    var duration: Duration? = null
)