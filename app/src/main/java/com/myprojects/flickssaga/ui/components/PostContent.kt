package com.myprojects.flickssaga.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material.Text
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.FlickState
import com.myprojects.flickssaga.data.Post
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily
import com.myprojects.flickssaga.viewmodels.VideoPostViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun PostContent(post: Post, isCurrentlyVisible: Boolean) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFffffff),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.White
        ),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmarksimple),
                            contentDescription = "bookmark",
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.dotsthreevertical),
                            contentDescription = "more",
                            modifier = Modifier
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                    .clickable { /* TODO */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFffffff),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
                shape = RoundedCornerShape(24.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            model = post.imageUrl,
                            contentDescription = "video thumbnail",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    if (isCurrentlyVisible) {
                        PostVideoPlayer(
                            videoUrl = post.videoUrl!!,
                            imageUrl = post.imageUrl!!,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.paperplaneright),
                        contentDescription = "bookmark",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.trophy),
                        contentDescription = "more",
                        modifier = Modifier
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.chatdots),
                        contentDescription = "bookmark",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.fire),
                        contentDescription = "more",
                        modifier = Modifier
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Text(
                    text = post.title ?: "",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 19.sp
                    ),
                    fontWeight = FontWeight(600),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = post.description ?: "",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 12.sp
                    ),
                    fontWeight = FontWeight(300),
                    modifier = Modifier
                )
                Text(
                    text = "California . ${post.timestamp?.let {
                        android.text.format.DateUtils.getRelativeTimeSpanString(it)
                    } ?: "Unknown time"}",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 10.sp
                    ),
                    fontWeight = FontWeight(510),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}


//@Preview
//@Composable
//fun PostPreview() {
//    PostContent()
//}