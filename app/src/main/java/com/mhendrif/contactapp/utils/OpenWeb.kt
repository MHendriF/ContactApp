package com.mhendrif.contactapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun goToUrl(
    context: Context,
    websiteUrl : String
) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
    context.startActivity(intent)
}
