/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:10 PM
 * /
 */

package com.zaeem.routy.routing

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.zaeem.routy.models.TravelMode
import com.zaeem.routy.utils.DirectionURLBuilder
import com.zaeem.routy.utils.URLS
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

// TODO : Replace OkHttpClient with Retrofit
class DirectionApiApiImpl : IDirectionApi {

    private val okHttpClient = OkHttpClient()

    override suspend fun getDirections(
        start: LatLng,
        end: LatLng,
        mode: TravelMode,
        apiKey: String
    ): String {

        val directionBuilder =
            DirectionURLBuilder.Builder(
                origin = start,
                destination = end,
                key = apiKey,
                mode = mode
            )

        return getJSONDirection(directionBuilder)
    }

    override suspend fun getDirections(directionURLBuilder: DirectionURLBuilder.Builder): String {


        return getJSONDirection(directionURLBuilder)


    }


    private fun getJSONDirection(
        directionURLBuilder: DirectionURLBuilder.Builder
    ): String {

        val directionApiUrl = URLS.DIRECTION_API +
                directionURLBuilder.buildURL()

        val request = Request.Builder()
            .url(directionApiUrl)
            .build()

        val response: Response = okHttpClient.newCall(request).execute()


        return response.body().string()
    }
}