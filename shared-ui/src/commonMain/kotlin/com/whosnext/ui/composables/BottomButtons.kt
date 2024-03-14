@file:Suppress("unused", "SpellCheckingInspection")

package com.whosnext.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import whosnext.shared_ui.generated.resources.Res
import whosnext.shared_ui.generated.resources.bt_pause
import whosnext.shared_ui.generated.resources.bt_play
import whosnext.shared_ui.generated.resources.bt_reset

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun BottomButtons(
    modifier: Modifier,
    value: Int,
    isCountingDown: Boolean,
    isRestarting: Boolean,
    isStopped: Boolean,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onReset: () -> Unit,
    onStop: () -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        shape = CircleShape
                        clip = true
                        alpha = if (value > 0 && !isRestarting) 1f else 0.5f
                    }
                    .clickable(value > 0 && !isRestarting) {
                        if (isCountingDown) {
                            onPause.invoke()
                        } else {
                            onStart.invoke()
                        }
                    },
                painter = painterResource(if (isCountingDown) Res.drawable.bt_pause else Res.drawable.bt_play),
                contentDescription = null
            )
            Spacer(Modifier.size(40.dp))
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .graphicsLayer {
                        shape = CircleShape
                        clip = true
                        alpha = if (!isRestarting) 1f else 0.5f
                    }
                    .clickable(!isRestarting) {
                        if (isStopped) {
                            onReset.invoke()
                        } else {
                            onStop.invoke()
                        }
                    },
                painter = painterResource(Res.drawable.bt_reset),
                contentDescription = null
            )
        }
    }
}