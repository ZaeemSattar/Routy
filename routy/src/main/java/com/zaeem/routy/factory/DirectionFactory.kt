/*
 *
 *   Created Zaeem Sattar on 3/27/21 4:52 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/27/21 4:52 PM
 * /
 */

package com.zaeem.routy.factory

import com.zaeem.routy.routing.DirectionApiApiImpl
import com.zaeem.routy.routing.IDirectionApi

object DirectionFactory {

    fun provideDirectionApi(): IDirectionApi {

        return DirectionApiApiImpl()
    }
}