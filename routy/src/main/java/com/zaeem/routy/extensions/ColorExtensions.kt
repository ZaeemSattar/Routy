/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:10 PM
 * /
 */

package com.zaeem.routy.extensions

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * An Extension to getColorCompat of view via context
 * @return void
 * @author Zaeem Sattar.
 */
fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)
