/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:04 PM
 * /
 */

package com.zaeem.routy.parser

import com.google.gson.GsonBuilder

class RouteGsonParser<T> : RouteParser<T> {
    override fun parse(data: String?, type: Class<T>?): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<Any>(data, type) as T
    }
}