/*
 *
 *   Created Zaeem Sattar on 3/27/21 11:05 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/27/21 11:05 PM
 * /
 */

package com.zaeem.routy.utils

import org.junit.Assert.assertTrue
import org.junit.Test

class PolylineDecoderTest {


    @Test
    fun testDecodePolyline() {
        val decoder = PolylineDecoder()
        val points = decoder.decodePolyline("ygn~F|kgvOznCjsBo|BhR")

        assertTrue(points.isNotEmpty())
    }

}