package com.jhostinlh.topeliculas.core.platform

import android.content.Context
import com.jhostinlh.topeliculas.core.extensions.checkNetworkState


class NetworkHandler
(private val context: Context) {
    val isConnected get() = context.checkNetworkState()
}