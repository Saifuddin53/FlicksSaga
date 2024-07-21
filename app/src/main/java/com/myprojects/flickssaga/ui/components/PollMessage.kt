package com.myprojects.flickssaga.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily


@Composable
fun PollDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colors.surface,
        ) {
            PollContent()
        }
    }
}


@Composable
fun PollContent(){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardColors(
            containerColor = Color(0xFFffffe5),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://previews.123rf.com/images/aquir/aquir1311/aquir131100316/23569861-sample-grunge-red-round-stamp.jpg",
                    contentDescription = "user image",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(30.dp)
                        .clip(CircleShape)
                )

                Text(
                    text = "@username",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp
                    ),
                    fontWeight = FontWeight(500),
                )
            }

            Text(
                text = "Description",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp
                ),
                fontWeight = FontWeight(50),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = CardColors(
                    containerColor = Color(0xFFcfb8ff),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 10.dp, start = 8.dp, end = 8.dp)
                ) {
                    Text(
                        text = "Who's gonna win this Las Vegas GP?",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontSize = 15.sp
                        ),
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(bottom = 16.dp, start = 8.dp)
                    )
                    PollOption(text = "Option 1")
                    PollOption(text = "Option 2")
                    PollOption(text = "Option 3")
                    PollOption(text = "Option 4")
                }
            }
        }
    }
}

@Composable
fun PollOption(
    text: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardColors(
            containerColor = Color(0xFFb397eb),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        )
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = poppinsFontFamily,
                fontSize = 12.sp
            ),
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview
@Composable
fun PollContentPreview(){
    PollContent()
}