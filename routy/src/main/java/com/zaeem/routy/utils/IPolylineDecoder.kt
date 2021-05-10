/*
 *
 *   Created Zaeem Sattar on 3/27/21 7:07 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/27/21 7:07 PM
 * /
 */

package com.zaeem.routy.utils

import com.google.android.gms.maps.model.LatLng

interface IPolylineDecoder {

    fun decodePolyline(encoded: String): List<LatLng>
}