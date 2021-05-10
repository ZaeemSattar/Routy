/*
 *
 *   Created Zaeem Sattar on 3/23/21 9:10 PM
 *   Copyright Ⓒ 2021. All rights reserved Ⓒ Zaeem Sattar
 *   Last modified: 3/23/21 9:00 PM
 * /
 */

package com.zaeem.routyapp

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zaeem.routy.utils.URLS

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(RouteFragment())

    }


    private fun addFragment(
        fragment: Fragment,
        tag: String = fragment.javaClass.name,
        @IdRes containerViewId: Int = R.id.container
    ) {
        val ft = supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, tag)
        ft.commit()
    }
}
