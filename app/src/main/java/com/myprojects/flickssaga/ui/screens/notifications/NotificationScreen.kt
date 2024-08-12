package com.myprojects.flickssaga.ui.screens.notifications

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.data.CATEGORY
import com.myprojects.flickssaga.data.Notification
import com.myprojects.flickssaga.data.toReadableString
import com.myprojects.flickssaga.ui.screens.notifications.components.NotificationItem
import com.myprojects.flickssaga.ui.theme.poppinsFontFamily

@Composable
fun NotificationScreen(

) {
    val filterDialog = remember { mutableStateOf(false) }
    var selectedCategories by remember { mutableStateOf(emptyList<CATEGORY>()) }

    val groupedNotifications = groupNotificationsByDate(
        notifications = notificationList,
        selectedCategories = selectedCategories.ifEmpty { CATEGORY.entries }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Notifications",
                    style = TextStyle(
                        fontFamily = poppinsFontFamily,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(500),
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        filterDialog.value = true
                    }) {
                        Icon(painter = painterResource(id = R.drawable.filter),
                            contentDescription = "Search")
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier
                    .padding(top = 24.dp)
                )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Show Violation notifications first
            groupedNotifications["Violation"]?.let { violationNotifications ->
                if (violationNotifications.isNotEmpty()) {
                    item {
                        Text(
                            text = "Violations",
                            style = TextStyle(
                                fontFamily = poppinsFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(violationNotifications) { notification ->
                        NotificationItem(notification = notification)
                    }
                }
            }

            // Show the rest of the notifications grouped by date
            groupedNotifications.forEach { (header, notifications) ->
                if (header != "Violation" && notifications.isNotEmpty()) {
                    item {
                        Text(
                            text = header,
                            style = TextStyle(
                                fontFamily = poppinsFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                    items(notifications) { notification ->
                        NotificationItem(notification = notification)
                    }
                }
            }
        }


        if (filterDialog.value) {
            FilterModal(
                showBottomSheet = filterDialog,
                selectedCategories = selectedCategories,
                onCategoriesSelected = { selectedCategories = it }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterModal(
    showBottomSheet: MutableState<Boolean>,
    selectedCategories: List<CATEGORY>,
    onCategoriesSelected: (List<CATEGORY>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val categories = CATEGORY.entries.toTypedArray()

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
        },
        sheetState = sheetState,
        modifier = Modifier
            .fillMaxHeight(0.55f)
            .fillMaxWidth(),
        containerColor = Color(0XFFF1F1F1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Filter",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600)
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Categories",
                style = TextStyle(
                    fontFamily = poppinsFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600)
                ),
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            categories.forEach { category ->
                val isSelected = selectedCategories.contains(category)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val newSelection = if (isSelected) {
                                selectedCategories - category
                            } else {
                                selectedCategories + category
                            }
                            onCategoriesSelected(newSelection)
                        }
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        text = category.toReadableString(),
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    CustomCheckbox(
                        checked = isSelected,
                        )
                }
            }
        }
    }
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .border(2.dp, if (checked) Color.Transparent else color, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Green)
            )
            Icon(
                painter = painterResource(id = R.drawable.tick_checkbox),
                contentDescription = ""
            )
        }
    }
}



@Composable
fun groupNotificationsByDate(
    notifications: List<Notification>,
    selectedCategories: List<CATEGORY>
): Map<String, List<Notification>> {
    val violation = mutableListOf<Notification>()
    val today = mutableListOf<Notification>()
    val yesterday = mutableListOf<Notification>()
    val earlier = mutableListOf<Notification>()

    val currentTime = System.currentTimeMillis()
    val oneDayInMillis = 24 * 60 * 60 * 1000

    notifications.filter { it.category in selectedCategories }.forEach { notification ->
        val timeDifference = currentTime - (notification.timestamp ?: 0)
        when (notification.category) {
            CATEGORY.VIOLATION -> violation.add(notification)
            else -> when {
                timeDifference < oneDayInMillis -> today.add(notification)
                timeDifference < 2 * oneDayInMillis -> yesterday.add(notification)
                else -> earlier.add(notification)
            }
        }
    }

    return mapOf(
        "Violation" to violation,
        "Today" to today,
        "Yesterday" to yesterday,
        "Earlier" to earlier
    )
}

val notificationList: List<Notification> = listOf(
    Notification(1, "abc", "liked your post and this is a long text. ", 1, CATEGORY.LIKE),
    Notification(2, "abc", "liked your post. ", 1723258618000, CATEGORY.LIKE),
    Notification(3, "abc", "liked your post long text. ", 1, CATEGORY.LIKE),
    Notification(4, "abc", "liked your post. ", 1723345018000, CATEGORY.LIKE),
    Notification(5, "abc", "liked your post and this is a long text. ", 1, CATEGORY.LIKE),
    Notification(4, "abc", "commented in this post ", 1723440371000, CATEGORY.COMMENT),
    Notification(4, "abc", "liked your post. ", 1723440371000, CATEGORY.LIKE),
    Notification(4, "abc", "liked your post. ", 1723345018000, CATEGORY.LIKE),
    Notification(4, "abc", "liked your post. ", 1723345011000, CATEGORY.LIKE),
    Notification(4, "", "Your account is not secure. ", 1723345018000, CATEGORY.VIOLATION),
)

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun FilterModalPreview() {
//    FilterModal(mutableStateOf(true))
}