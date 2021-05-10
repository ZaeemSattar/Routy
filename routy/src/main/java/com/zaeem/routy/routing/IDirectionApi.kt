/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:10 PM
 * /
 */
package com.zaeem.routy.routing

import com.google.android.gms.maps.model.LatLng
import com.zaeem.routy.models.TravelMode
import com.zaeem.routy.utils.DirectionURLBuilder


interface IDirectionApi {
    suspend fun getDirections(
        start: LatLng,
        end: LatLng,
        mode: TravelMode,
        apiKey: String,
    ): String

    suspend fun getDirections(
        directionURLBuilder: DirectionURLBuilder.Builder
    ): String

}