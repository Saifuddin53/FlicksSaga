//package com.myprojects.flickssaga.ui.screens
//
//import androidx.annotation.DrawableRes
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Divider
//import androidx.compose.material.Icon
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.MoreVert
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.imageResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.myprojects.flickssaga.viewmodels.FlickViewModel
//import com.myprojects.flickssaga.R
//import com.myprojects.flickssaga.data.Flick
//import com.myprojects.flickssaga.ui.components.VideoPlayer
//
//@Composable
//fun FlicksScreen(
//    viewModel: FlickViewModel,
//    modifier: Modifier = Modifier
//) {
//    val flicks by viewModel.flicks.collectAsState()
//    val currentFlick by viewModel.currentFlicks.collectAsState()
//
//    Box(Modifier.background(color = Color.Black)) {
//        ReelsList(flicks, viewModel)
//        ReelsHeader()
//    }
//}
//
//@Composable
//fun ReelsHeader() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(
//                horizontal = 10.dp,
//                vertical = 35.dp
//            ),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text("Reels", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 21.sp)
//        Icon(painter = painterResource(id = R.drawable.ic_outlined_camera),
//            tint = Color.White,
//            modifier = Modifier.size(24.dp),
//            contentDescription = null)
//    }
//}
//
//@Composable
//fun ReelsList(
//    flicks: List<Flick>,
//    viewModel: FlickViewModel
//) {
//    val flicks1 = flicks
//
//    LazyColumn {
//        items(flicks1.size) { index ->
//            Box(Modifier.fillParentMaxSize()) {
//                FlickItem(flicks = flicks1, index = index, viewModel = viewModel)
//                Column(Modifier.align(Alignment.BottomStart)) {
////                    ReelFooter(flicks1[index])
//                    Divider()
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun FlickItem(
//    flicks: List<Flick>,
//    index: Int,
//    viewModel: FlickViewModel
//) {
//    viewModel.setIsPlaying(index,isPlaying = (flicks[index].id-1) == index)
//    VideoPlayer(flicks[index])
//}
////
////@Composable
////fun ReelFooter(flick: Flick) {
////    Row(
////        Modifier
////            .fillMaxWidth()
////            .padding(start = 18.dp, bottom = 18.dp), verticalAlignment = Alignment.Bottom) {
////        FooterUserData(
////            flick = flick,
////            modifier = Modifier.weight(8f)
////        )
////
//////        FooterUserAction(
//////            flick = flick,
//////            modifier = Modifier.weight(2f)
//////        )
////    }
////}
////
////@Composable
////fun FooterUserAction(flick: Flick, modifier: Modifier) {
////    Column(
////        horizontalAlignment = Alignment.CenterHorizontally,
////        modifier = modifier
////    ) {
////        UserActionWithText(
////            drawableRes = R.drawable.ic_outlined_favorite,
////            text = flick.likesCount.toString()
////        )
////        Spacer(modifier = Modifier.height(28.dp))
////        UserActionWithText(
////            drawableRes = R.drawable.ic_outlined_comment,
////            text = flick.commentsCount.toString()
////        )
////        Spacer(modifier = Modifier.height(28.dp))
////        UserAction(drawableRes = R.drawable.ic_dm)
////        Spacer(modifier = Modifier.height(28.dp))
////        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
////        Spacer(modifier = Modifier.height(28.dp))
////        GlideImage(
////            imageModel = flick.userImage,
////            modifier = Modifier
////                .size(28.dp)
////                .background(color = Color.Gray, shape = RoundedCornerShape(6.dp),)
////                .clip(RoundedCornerShape(6.dp)),
////            contentDescription = null
////        )
////    }
////}
//
//@Composable
//fun UserAction(@DrawableRes drawableRes: Int) {
//    Icon(
//        bitmap = ImageBitmap.imageResource(id = drawableRes),
//        tint = Color.White,
//        modifier = Modifier.size(16.dp),
//        contentDescription = null
//    )
//}
//
//@Composable
//fun UserActionWithText(
//    @DrawableRes drawableRes: Int,
//    text: String
//) {
//    Icon(
//        bitmap = ImageBitmap.imageResource(id = drawableRes),
//        tint = Color.White,
//        modifier = Modifier.size(28.dp),
//        contentDescription = null
//    )
//    Spacer(modifier = Modifier.height(6.dp))
//    Text(
//        text = text,
//        color = Color.White,
//        fontSize = 13.sp,
//        fontWeight = FontWeight.SemiBold
//    )
//}
////
////@Composable
////fun FooterUserData(flick: Flick, modifier: Modifier) {
////    Column(
////        modifier = modifier,
////        verticalArrangement = Arrangement.Center
////    ) {
////
////        Row(
////            verticalAlignment = Alignment.CenterVertically
////        ) {
////            GlideImage(imageModel = flick.userImage,
////                contentScale = ContentScale.Crop,
////                modifier = Modifier
////                    .size(28.dp)
////                    .background(color = Color.Gray, shape = CircleShape)
////                    .clip(CircleShape),
////                contentDescription = null
////            )
////
////            Spacer(modifier = Modifier.width(horizontalPadding))
////            Text(
////                text = flick.userName,
////                color = Color.White,
////                style = MaterialTheme.typography.subtitle2
////            )
////
////            Spacer(modifier = Modifier.width(horizontalPadding))
////            Canvas(modifier = Modifier.size(5.dp), onDraw = {
////                drawCircle(
////                    color = Color.White,
////                    radius = 8f
////                )
////            })
////            Spacer(modifier = Modifier.width(horizontalPadding))
////            Text(
////                text= "Follow",
////                color = Color.White,
////                style = MaterialTheme.typography.subtitle2
////            )
////        }
////
////        Spacer(modifier = Modifier.height(horizontalPadding))
////        Text(text = flick.comment, color = Color.White)
////        Spacer(modifier = Modifier.height(horizontalPadding))
////
////
////        // Audio
////        Row(
////            verticalAlignment = Alignment.CenterVertically
////        ) {
////            Text(flick.userName, color = Color.White)
////            Spacer(modifier = Modifier.width(horizontalPadding))
////            Canvas(modifier = Modifier.size(5.dp), onDraw = {
////                drawCircle(
////                    color = Color.White,
////                    radius = 8f
////                )
////            })
////            Spacer(modifier = Modifier.width(horizontalPadding))
////            Text(
////                text = "Audio asli",
////                color = Color.White
////            )
////        }
////    }
//
