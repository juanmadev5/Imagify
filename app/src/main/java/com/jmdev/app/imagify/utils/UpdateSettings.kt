package com.jmdev.app.imagify.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

fun <T> updateSetting(
    value: T,
    stateFlow: MutableStateFlow<T>,
    save: suspend (T) -> Unit,
    scope: CoroutineScope
) {
    stateFlow.value = value
    scope.launchIO { save(value) }
}