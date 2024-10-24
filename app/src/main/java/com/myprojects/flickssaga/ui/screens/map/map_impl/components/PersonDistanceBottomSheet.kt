package com.myprojects.flickssaga.ui.screens.map.map_impl.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Surface
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.models.LocationDetails
import com.myprojects.flickssaga.ui.screens.map.models.UserProfile
import com.myprojects.flickssaga.ui.theme.Typography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDistanceBottomSheet(
    user: UserProfile,
    sheetState: SheetState,
    scope: CoroutineScope,
    showBottomSheetState: MutableState<Boolean>
) {
    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = sheetState,
        dragHandle = {},
        containerColor = Color.White
    ) {
        ModalBottomSheetContent(
            user = user,
            onCancel = {
                showBottomSheetState.value = false
                scope.launch {
                    sheetState.hide()
                }
            }
        )
    }
}

@Composable
fun ModalBottomSheetContent(
    user: UserProfile,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 32.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            UserDetailContent(
                name = user.name,
                username = user.username,
                profilePictureUrl = user.profilePictureUrl
            )

            CancelButton {
                onCancel()
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xffCBCBCB),
            modifier = Modifier.padding(top = 8.dp)
        )

        DetailBar(
            distance = user.locationDetails.distance,
            location = user.locationDetails.locationName,
            time = user.locationDetails.travelTime,
            modifier = Modifier
                .padding(top = 4.dp)
        )

        UserDesc(
            text = user.sharedConnection ?: "",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomButton(
                text = "View Profile",
                contentColor = Color.Black,
                containerColor = Color(0xffF5F5F5),
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xffCBCBCB),
                        shape = RoundedCornerShape(6.dp)
                    )
            )

            CustomButton(
                text = "Follow",
                contentColor = Color.White,
                containerColor = Color(0xff0680D5),
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CancelButton(
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = Color(0xfff5f5f5),
        modifier = Modifier
            .clickable {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.close),
            contentDescription = "",
            tint = Color.Black,
            modifier = Modifier
                .size(30.dp)
                .padding(8.dp)
        )
    }
}

@Composable
fun DetailBar(
    distance: String,
    location: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconWithText(
            icon = R.drawable.distance,
            text = distance,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        VerticalDivider(
            modifier = Modifier.height(18.dp),
            thickness = 1.dp,
            color = Color(0xffCBCBCB)
        )

        IconWithText(
            icon = R.drawable.location,
            text = location,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        VerticalDivider(
            modifier = Modifier.height(18.dp),
            thickness = 1.dp,
            color = Color(0xffCBCBCB)
        )

        IconWithText(
            icon = R.drawable.walk_fill,
            text = time,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun UserDesc(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xff595959),
                    fontSize = Typography.bodySmall.fontSize,
                    fontWeight = Typography.bodyMedium.fontWeight,
                    letterSpacing = 0.75.sp
                )
            ) {
                append(text)
            }

            withStyle(
                style = SpanStyle(
                    color = Color(0xff595959),
                    fontSize = Typography.headlineLarge.fontSize,
                    fontWeight = Typography.headlineLarge.fontWeight,
                    letterSpacing = 0.75.sp
                )
            ) {
                append(
                    " college fare "
                )
            }

            withStyle(
                style = SpanStyle(
                    color = Color(0xff00A3FF),
                    fontSize = Typography.bodySmall.fontSize,
                    fontWeight = Typography.bodyMedium.fontWeight
                )
            ) {
                append("(view)")
            }
        },
        modifier = modifier
    )
}

@Composable
fun CustomButton(
    text: String,
    contentColor: Color,
    containerColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = containerColor,
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        Text(
            text = text,
            style = Typography.bodyMedium.copy(
                color = contentColor,
                fontSize = 12.sp,
                fontWeight = FontWeight(400)
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                vertical = 10.dp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PersonDistanceBottomSheetPreview(modifier: Modifier = Modifier) {
    ModalBottomSheetContent(
        user = UserProfile(
            name = "Aniket",
            username = "@aniket",
            profilePictureUrl = "https://cdn.pixabay.com/photo/2013/07/13/10/44/man-157699_1280.png",
            address = "Near Sai compex, K.V pendarkar college ,Dombivali, Maharastra, 421201",
            locationDetails = LocationDetails(
                locationName = "Ji.Raya Yeh Gangga",
                distance = "200m",
                travelTime = "5 min"
            ),
            sharedConnection = "Jay Rajput and yous is the same group"
        ),
        onCancel = {}
    )
}