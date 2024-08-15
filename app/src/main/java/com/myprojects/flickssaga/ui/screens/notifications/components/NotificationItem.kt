package com.myprojects.flickssaga.ui.screens.notifications.components

import android.text.format.DateUtils.getRelativeTimeSpanString
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.myprojects.flickssaga.data.CATEGORY
import com.myprojects.flickssaga.data.Notification
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily

@Composable
fun NotificationItem(
    notification: Notification
) {
    var isConnected by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false, 
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(vertical = 4.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-sample-grunge-red-round-stamp.jpg",
                contentDescription = "user image",
                modifier = Modifier
                    .padding(8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterVertically)
            )

            notification.message?.let {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontFamily = poppinsFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold, // Change to desired font weight
                            color = Color.Black
                        )
                        ) {
                            append("@${notification.username}")
                        }
                        withStyle(style = SpanStyle(
                            fontFamily = poppinsFontFamily,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal, // Different font weight for the rest of the text
                            color = Color.Black, // Different color for the rest of the text
                            letterSpacing = 0.sp
                        )) {
                            append(" $it")
                        }
                        if(notification.category != CATEGORY.VIOLATION) {
                            withStyle(style = SpanStyle(
                                fontFamily = poppinsFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal, // Different font weight for the rest of the text
                                color = Color.Gray, // Different color for the rest of the text
                                letterSpacing = 0.sp
                            )) {
                                append("${notification.timestamp?.let {
                                    getRelativeTimeSpanString(it)
                                } ?: "Unknown time"}")
                            }
                        }else {
                            withStyle(style = SpanStyle(
                                fontFamily = poppinsFontFamily,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal, // Different font weight for the rest of the text
                                color = Color.Blue, // Different color for the rest of the text
                                letterSpacing = 0.sp
                            )) {
                                append("Review it")
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            if(notification.category != CATEGORY.FOLLOW) {
                AsyncImage(
                    model = "https://cdn.pixabay.com/photo/2024/08/04/09/02/cat-8943928_1280.png",
                    contentDescription = "user image",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(50.dp)
                )
            } else {
                Button(onClick = {
                    isConnected = !isConnected
                },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if(isConnected) Color.Black else Color.White
                    ),
                    elevation = ButtonDefaults.elevation(0.dp),
                    modifier = Modifier.padding(6.dp)
                ) {
                    Text(
                        text = if(isConnected) "Connected" else "Connect",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold, // Different font weight for the rest of the text
                            color = if(isConnected) Color.Green else Color.Black,
                            letterSpacing = 0.sp
                        )
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun NotificationItemPreview() {
    NotificationItem(Notification(1, "abc", "liked your post and this is a long text", 1, CATEGORY.LIKE))
}

