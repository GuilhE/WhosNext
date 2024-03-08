package com.whosnext.ui.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.whosnext.ui.padTime
import com.whosnext.ui.theme.DigitsTheme
import com.whosnext.ui.theme.OverlayTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import whosnext.`shared-ui`.generated.resources.Res
import whosnext.`shared-ui`.generated.resources.ic_setter_center
import whosnext.`shared-ui`.generated.resources.ic_setter_gesture
import whosnext.`shared-ui`.generated.resources.lbl_minutes
import whosnext.`shared-ui`.generated.resources.lbl_seconds
import whosnext.`shared-ui`.generated.resources.lbl_sep

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TimerSetOverlay(
    modifier: Modifier,
    minutes: Int,
    seconds: Int,
    isDraggingLeft: Boolean
) {
    val alphaRight: Float by animateFloatAsState(if (isDraggingLeft) 0.5f else 1f)
    val alphaLeft: Float by animateFloatAsState(if (isDraggingLeft) 1f else 0.5f)

    OverlayTheme {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.85f))
                .padding(horizontal = 40.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 38.dp)
            ) {
                DigitsTheme {
                    Text(
                        modifier = Modifier.offset(x = 0.dp, y = (-0.5).dp),
                        text = padTime(minutes),
                        style = MaterialTheme.typography.displayLarge,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = alphaLeft)
                    )
                    Text(
                        modifier = Modifier.offset(x = 0.dp, y = -(0.5).dp),
                        text = stringResource(Res.string.lbl_sep).uppercase(),
                        style = MaterialTheme.typography.displayLarge,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        modifier = Modifier.offset(x = 0.dp, y = -(0.5).dp),
                        text = padTime(seconds),
                        style = MaterialTheme.typography.displayLarge,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = alphaRight)
                    )
                }
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 30.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.alpha(alphaLeft),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Image(
                            modifier = Modifier
                                .size(120.dp)
                                .graphicsLayer(rotationY = 180f),
                            painter = painterResource(Res.drawable.ic_setter_gesture),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(Res.string.lbl_minutes).uppercase(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Column(
                        modifier = Modifier.alpha(alphaRight),
                        horizontalAlignment = Alignment.End
                    ) {
                        Image(
                            modifier = Modifier.size(120.dp),
                            painter = painterResource(Res.drawable.ic_setter_gesture),
                            contentDescription = null
                        )
                        Text(
                            text = stringResource(Res.string.lbl_seconds).uppercase(),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 60.dp)
                        .size(120.dp),
                    painter = painterResource(Res.drawable.ic_setter_center),
                    contentDescription = null
                )
            }
        }
    }
}