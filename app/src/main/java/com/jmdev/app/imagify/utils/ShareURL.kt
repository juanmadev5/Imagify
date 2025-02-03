package com.jmdev.app.imagify.utils

import android.content.Context
import android.content.Intent
import com.jmdev.app.imagify.R

fun shareUrl(context: Context, url: String) {
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    context.startActivity(
        Intent.createChooser(
            shareIntent,
            context.getString(R.string.share_photo)
        )
    )
}