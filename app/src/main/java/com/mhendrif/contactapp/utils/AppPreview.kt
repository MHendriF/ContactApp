package com.mhendrif.contactapp.utils

import androidx.compose.runtime.Composable
import com.mhendrif.contactapp.ui.theme.ContactAppTheme

@Composable
fun AppPreview(content: @Composable () -> Unit) {
    ContactAppTheme(content = content)
}