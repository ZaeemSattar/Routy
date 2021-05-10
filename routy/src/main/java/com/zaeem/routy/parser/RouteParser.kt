/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:04 PM
 * /
 */

package com.zaeem.routy.parser

interface RouteParser<T> {
    fun parse(data: String?, type: Class<T>?): T
}