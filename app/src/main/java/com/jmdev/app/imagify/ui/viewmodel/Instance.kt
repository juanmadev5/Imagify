package com.jmdev.app.imagify.ui.viewmodel

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Composable
inline fun <reified VM : ViewModel> getVM(): VM {
    val context = LocalContext.current
    return remember {
        ViewModelProvider(context as ComponentActivity)[VM::class.java]
    }
}