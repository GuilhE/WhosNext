package com.whosnext.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import com.whosnext.ui.theme.SplashTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import whosnext.`shared-ui`.generated.resources.Res
import whosnext.`shared-ui`.generated.resources.bg_texture
import whosnext.`shared-ui`.generated.resources.ic_logo
import whosnext.`shared-ui`.generated.resources.ic_zone

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    SplashTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.bg_texture),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(Res.drawable.ic_logo),
                    contentDescription = null
                )
                Image(
                    painter = painterResource(Res.drawable.ic_zone),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .rotate(180f),
                    contentDescription = null
                )
                Image(
                    painter = painterResource(Res.drawable.ic_zone),
                    modifier = Modifier.align(Alignment.BottomCenter),
                    contentDescription = null
                )
            }
        }
    }
}