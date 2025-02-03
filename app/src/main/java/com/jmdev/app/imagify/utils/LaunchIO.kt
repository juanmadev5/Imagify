package com.jmdev.app.imagify.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CoroutineScope.launchIO(block: suspend () -> Unit) {
    this.launch(Dispatchers.IO) { block() }
}