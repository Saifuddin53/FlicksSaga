package com.myprojects.flickssaga.ui.screens.map.map_impl.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.LocationDetails
import com.myprojects.flickssaga.ui.screens.map.models.UserProfile
import com.myprojects.flickssaga.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDistanceCard(
    user: UserProfile,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        modifier = modifier
            .width(290.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UserDetailContent(
                name = user.name,
                username = user.username,
                profilePictureUrl = user.profilePictureUrl
            )
            IconWithText(
                icon = R.drawable.location,
                text = user.address
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconWithText(
                    icon = R.drawable.distance,
                    text = user.locationDetails.distance
                )
                ViewButton {
                    //show the bottom sheet
                    scope.launch {
                        sheetState.show()
                    }
                }
            }
        }
        
        if(sheetState.isVisible) {
            PersonDistanceBottomSheet(
                user = user,
                scope = scope,
                sheetState = sheetState
            )
        }
    }
}

@Composable
fun ViewButton(
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = Color(0xfff5f5f5),
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(start = 16.dp, end = 10.dp, top = 6.dp, bottom = 6.dp)
        ) {
            Text(
                text = "View",
                style = Typography.bodyMedium.copy(
                    color = Color(0xff00A3FF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight(400)
                ),
            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null,
                tint = Color(0xff00A3FF),
                modifier = Modifier
                    .size(18.dp)
            )
        }
    }
}

@Preview
@Composable
fun PersonDistanceCardPreview(modifier: Modifier = Modifier) {
    PersonDistanceCard(
        user = UserProfile(
            name = "Aniket",
            username = "@aniket",
            profilePictureUrl = "https://cdn.pixabay.com/photo/2013/07/13/10/44/man-157699_1280.png",
            address = "Near Sai compex, K.V pendarkar college ,Dombivali, Maharastra, 421201",
            locationDetails = LocationDetails(
                locationName = "Ji.Raya Yeh Gangga",
                distance = "200m",
                travelTime = "5 min"
            )
        ),
        modifier = modifier
    )
}