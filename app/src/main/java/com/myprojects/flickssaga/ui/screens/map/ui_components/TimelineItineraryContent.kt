package com.myprojects.flickssaga.ui.screens.map.ui_components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.ItineraryEntity
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineItineraryContent(itineraryEntity: ItineraryEntity, contentHeight: Float, imgAlpha: Float) {
    // Animate the alpha value based on the contentHeight
    val iconAlpha by animateFloatAsState(
        targetValue = if (contentHeight > 205) 1f else 0.5f, // Adjust visibility based on contentHeight
        animationSpec = tween(durationMillis = 500) // Smooth transition
    )

    val animatedPaddingTop = animateDpAsState(
        targetValue = if (contentHeight > 205) 30.dp else 15.dp,  // Adjust based on your needs
        animationSpec = tween(durationMillis = 500)  // Duration of the animation
    )

    Box(modifier = Modifier.fillMaxWidth().height(contentHeight.dp), content = function(
        contentHeight,
        imgAlpha,
        itineraryEntity,
        animatedPaddingTop,
        iconAlpha
    )
    )
}

@Composable
private fun function(
    contentHeight: Float,
    imgAlpha: Float,
    itineraryEntity: ItineraryEntity,
    animatedPaddingTop: State<Dp>,
    iconAlpha: Float
): @Composable() (BoxScope.() -> Unit) =
    {
        Image(
            painter = painterResource(id = R.drawable.map_image),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(if (contentHeight > 205) 200.dp else contentHeight.dp)
                .graphicsLayer { alpha = imgAlpha },
            contentScale = ContentScale.FillWidth
        )

        ItineraryDetailItem(
            itinerary = itineraryEntity
        )

        Card(
            modifier = Modifier
                .padding(top = animatedPaddingTop.value, start = 20.dp)  // Static position
                .size(36.dp)
                .alpha(iconAlpha) // Apply the animated alpha value
                .align(Alignment.TopStart),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxSize()) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(15.dp),
                    tint = Color.Black
                )
            }
        }

        Card(
            modifier = Modifier
                .padding(top = animatedPaddingTop.value, end = 20.dp)  // Static position
                .size(36.dp)
                .alpha(iconAlpha) // Apply the animated alpha value
                .align(Alignment.TopEnd),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.map_outline_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(16.dp),
                    tint = Color.Black
                )
            }
        }
    }
