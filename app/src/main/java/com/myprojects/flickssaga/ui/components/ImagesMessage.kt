package com.myprojects.flickssaga.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily


@Composable
fun ImagesMessageContent(imageList: List<String>){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
                    .height(270.dp)
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .clickable {
                        // TODO
                    },
                colors = CardColors(
                    containerColor = Color(0xFFffffff),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
            ) {

                when(imageList.size) {
                    1 -> {
                        AsyncImage(
                            model = imageList[0],
                            contentDescription = "user image",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    2 -> {
                        Row(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            AsyncImage(
                                model = imageList[0],
                                contentDescription = "user image",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp, end = 2.dp, top = 4.dp, bottom = 4.dp)
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)),
                                contentScale = ContentScale.Crop
                            )
                            AsyncImage(
                                model = imageList[1],
                                contentDescription = "user image",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 2.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                                    .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    3 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            AsyncImage(
                                model = imageList[0],
                                contentDescription = "user image",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp, top = 4.dp)
                                    .clip(RoundedCornerShape(topStart = 24.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            AsyncImage(
                                model = imageList[1],
                                contentDescription = "user image",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 4.dp, top = 4.dp)
                                    .clip(RoundedCornerShape(topEnd = 24.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        AsyncImage(
                            model = imageList[2],
                            contentDescription = "user image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                                .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                    }
                    4 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                AsyncImage(
                                    model = imageList[0],
                                    contentDescription = "user image",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 4.dp, top = 4.dp)
                                        .clip(RoundedCornerShape(topStart = 24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                AsyncImage(
                                    model = imageList[1],
                                    contentDescription = "user image",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp, top = 4.dp)
                                        .clip(RoundedCornerShape(topEnd = 24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                AsyncImage(
                                    model = imageList[2],
                                    contentDescription = "user image",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 4.dp, bottom = 4.dp)
                                        .clip(RoundedCornerShape(bottomStart = 24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                AsyncImage(
                                    model = imageList[3],
                                    contentDescription = "user image",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp, bottom = 4.dp)
                                        .clip(RoundedCornerShape(bottomEnd = 24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                AsyncImage(
                                    model = imageList[0],
                                    contentDescription = "user image",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 4.dp, top = 4.dp)
                                        .clip(RoundedCornerShape(topStart = 24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                AsyncImage(
                                    model = imageList[1],
                                    contentDescription = "user image",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp, top = 4.dp)
                                        .clip(RoundedCornerShape(topEnd = 24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                AsyncImage(
                                    model = imageList[2],
                                    contentDescription = "user image",
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 4.dp, bottom = 4.dp)
                                        .clip(RoundedCornerShape(bottomStart = 24.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp, bottom = 4.dp)
                                        .clip(RoundedCornerShape(bottomEnd = 24.dp))
                                ) {
                                    AsyncImage(
                                        model = imageList[3],
                                        contentDescription = "user image",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(bottomEnd = 24.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Black.copy(alpha = 0.6f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "${imageList.size - 4}+",
                                            color = Color.White,
                                            fontSize = 24.sp,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val imagesList1: List<String> = listOf(
    "https://cdn.pixabay.com/photo/2024/07/16/10/34/london-8899018_1280.jpg"
)

val imagesList2: List<String> = listOf(
    "https://cdn.pixabay.com/photo/2024/07/16/10/34/london-8899018_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/16/06/58/ai-generated-8898487_1280.jpg"
)

val imagesList3: List<String> = listOf(
    "https://cdn.pixabay.com/photo/2024/07/16/10/34/london-8899018_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/16/06/58/ai-generated-8898487_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/14/08/22/leopard-8893942_1280.jpg"
)

val imagesList4: List<String> = listOf(
    "https://cdn.pixabay.com/photo/2024/07/16/10/34/london-8899018_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/16/06/58/ai-generated-8898487_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/14/08/22/leopard-8893942_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/15/04/42/forest-fire-8895640_1280.jpg"
)


val imagesList5: List<String> = listOf(
    "https://cdn.pixabay.com/photo/2024/07/16/10/34/london-8899018_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/16/06/58/ai-generated-8898487_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/14/08/22/leopard-8893942_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/15/04/42/forest-fire-8895640_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/11/10/10/mountains-8887736_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/14/08/22/leopard-8893942_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/15/04/42/forest-fire-8895640_1280.jpg",
    "https://cdn.pixabay.com/photo/2024/07/11/10/10/mountains-8887736_1280.jpg"
)
