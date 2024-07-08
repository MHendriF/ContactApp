package com.mhendrif.contactapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mhendrif.contactapp.R
import com.mhendrif.contactapp.components.ContactTopAppBar
import com.mhendrif.contactapp.ui.theme.BluePrimary
import com.mhendrif.contactapp.utils.AppPreview
import com.mhendrif.contactapp.utils.goToUrl

@Composable
fun AboutScreen(
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            ContactTopAppBar(
                title = "About",
                canNavigateBack = true,
                canSearch = false,
                navigateUp = onNavigateBack,
                actions = {}
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 40.dp)

        ) {
            Image(modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.ic_author),
                contentDescription = "Image Author")
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Muhamad Hendri Febriansyah",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "hendrifebriansyah28@gmail.com",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { goToUrl(context = context, websiteUrl = "https://www.dicoding.com/users/leopard28zero/academies") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Icon(Icons.Filled.Info, contentDescription = "Icon Profile")
                Spacer(modifier = Modifier.width(8.dp))
                Text("My Profile")
            }
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() = AppPreview {
    AboutScreen({})
}