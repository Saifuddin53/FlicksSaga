package com.myprojects.flickssaga.ui.screens.map.trip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myprojects.flickssaga.R
import com.myprojects.flickssaga.ui.screens.map.trip.explore_components.CategoryContainer
import com.myprojects.flickssaga.ui.screens.map.trip.explore_components.CategoryItem
import com.myprojects.flickssaga.ui.screens.map.trip.explore_components.CategoryItemData
import com.myprojects.flickssaga.ui.screens.map.trip.explore_components.CategoryTypeItemData
import com.myprojects.flickssaga.ui.theme.Typography

@Composable
fun ExploreSection(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .height(1000.dp)
            .background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopSection(
            modifier = Modifier
                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
        )

        CategoryTypeContainer(
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        CategoryContainer(
            title = "Top searches",
            cardList = cardListTopSearch,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        CategoryContainer(
            title = "Picture Related to \"Mumbai\"",
            cardList = cardList,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun TopSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Categories",
            style = Typography.bodyMedium.copy(
                fontWeight = FontWeight.W500,
                fontSize = 15.sp
            )
        )
        Text(
            text = "See all",
            style = Typography.bodyMedium.copy(
                color = Color(0xff00A3FF)
            ),
            modifier = Modifier.clickable {
                // TODO: Handle click
            }
        )
    }
}

@Composable
fun CategoryTypeContainer(modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement
            .spacedBy(18.dp),
        contentPadding = PaddingValues(
            horizontal = 16.dp
        )
    ) {
        items(cardIconList) { item ->
            CategoryItem(
                iconResId = item.icon,
                title = item.type
            )
        }
    }
}

val cardIconList = listOf(
    CategoryTypeItemData(
        icon = R.drawable.shop,
        "Restaurants"
    ),
    CategoryTypeItemData(
        icon = R.drawable.shop,
        "Cafe"
    ),
    CategoryTypeItemData(
        icon = R.drawable.gallery,
        "Photo spots"
    ),
    CategoryTypeItemData(
        icon = R.drawable.shop,
        "Shopping"
    ),
    CategoryTypeItemData(
        icon = R.drawable.shop,
        "Restaurant"
    ),
    CategoryTypeItemData(
        icon = R.drawable.shop,
        "Restaurant"
    )
)

val cardList = listOf(
    CategoryItemData(
        image = "https://content.jdmagicbox.com/v2/comp/mumbai/28/022p690728/catalogue/wankhede-stadium-churchgate-mumbai-stadiums-xuu1151zxw.jpg",
        title = "Wankhede stadium"
    ),
    CategoryItemData(
        image = "https://media.istockphoto.com/id/1443804835/photo/a-beautiful-city-with-a-beach-and-some-tetrapods-as-well-as-a-seating-area.jpg?s=1024x1024&w=is&k=20&c=nirPbRITY4xrGwBU6bVOQtitUQ76SuYeAxguQozwBRM=",
        title = "Marine line"
    ),
    CategoryItemData(
        image = "https://media.istockphoto.com/id/1344548356/photo/juhu-beach-mumbai.jpg?s=1024x1024&w=is&k=20&c=ZrlED6VWG1n8QXztb_kTnuciCYodKXXsWhXewJhR-fc=",
        title = "Juhu beach"
    ),
    CategoryItemData(
        image = "https://content.jdmagicbox.com/v2/comp/mumbai/28/022p690728/catalogue/wankhede-stadium-churchgate-mumbai-stadiums-xuu1151zxw.jpg",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://content.jdmagicbox.com/v2/comp/mumbai/28/022p690728/catalogue/wankhede-stadium-churchgate-mumbai-stadiums-xuu1151zxw.jpg",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://content.jdmagicbox.com/v2/comp/mumbai/28/022p690728/catalogue/wankhede-stadium-churchgate-mumbai-stadiums-xuu1151zxw.jpg",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://content.jdmagicbox.com/v2/comp/mumbai/28/022p690728/catalogue/wankhede-stadium-churchgate-mumbai-stadiums-xuu1151zxw.jpg",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://content.jdmagicbox.com/v2/comp/mumbai/28/022p690728/catalogue/wankhede-stadium-churchgate-mumbai-stadiums-xuu1151zxw.jpg",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://content.jdmagicbox.com/v2/comp/mumbai/28/022p690728/catalogue/wankhede-stadium-churchgate-mumbai-stadiums-xuu1151zxw.jpg",
        title = "Cafe"
    )
)

val cardListTopSearch = listOf(
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Vadapav"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Burger"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Pav Bhaji"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Cafe"
    ),
    CategoryItemData(
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKzOKrpGhIDrArMBAmornbyeiUX6TEyALvtA&s",
        title = "Cafe"
    )
)