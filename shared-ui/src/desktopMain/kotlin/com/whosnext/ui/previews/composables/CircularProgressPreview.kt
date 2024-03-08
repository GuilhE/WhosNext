package com.whosnext.ui.previews.composables

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whosnext.ui.composables.CircularProgress
import com.whosnext.ui.theme.AppTheme

@Composable
@Preview
private fun CircularProgressPreview() {
    AppTheme(true) {
        val rows = listOf(
            Pair(0f, MaterialTheme.colorScheme.primary),
            Pair(5f, MaterialTheme.colorScheme.primary),
            Pair(10f, MaterialTheme.colorScheme.primary),
            Pair(15f, MaterialTheme.colorScheme.primary),
            Pair(20f, MaterialTheme.colorScheme.primary),
            Pair(25f, MaterialTheme.colorScheme.primary),
            Pair(30f, MaterialTheme.colorScheme.primary),
            Pair(35f, MaterialTheme.colorScheme.primary),
            Pair(40f, MaterialTheme.colorScheme.primary),
            Pair(45f, MaterialTheme.colorScheme.primary),
            Pair(50f, MaterialTheme.colorScheme.primary),
            Pair(55f, MaterialTheme.colorScheme.primary),
            Pair(60f, MaterialTheme.colorScheme.primary),
            Pair(65f, MaterialTheme.colorScheme.primary),
            Pair(70f, MaterialTheme.colorScheme.primary),
            Pair(75f, MaterialTheme.colorScheme.primary),
            Pair(80f, MaterialTheme.colorScheme.primary),
            Pair(85f, MaterialTheme.colorScheme.primary),
            Pair(90f, MaterialTheme.colorScheme.primary),
            Pair(95f, MaterialTheme.colorScheme.primary),
            Pair(100f, MaterialTheme.colorScheme.primary)
        )
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Adaptive(minSize = 130.dp)) {
            items(rows) {
                Box(Modifier.padding(20.dp), contentAlignment = Alignment.Center) {
                    CircularProgress(
                        Modifier
                            .width(80.dp)
                            .height(80.dp),
                        progress = it.first,
                        color = it.second
                    )
                }
            }
        }
    }
}