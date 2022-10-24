package com.example.testgithub.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testgithub.R
import com.example.testgithub.data.entity.Post
import com.example.testgithub.data.entity.User
import com.example.testgithub.ui.theme.Purple500
import com.example.testgithub.ui.theme.TypographyText

const val KEY_USER_ARG = "keyUserArg"

@Composable
fun DetailScreen(
    navigation: NavHostController,
    user: User?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            val imageShape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        shape = imageShape,
                        color = Color.White
                    )
                    .clip(shape = imageShape),
                model = user?.avatarUrl,
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                placeholder = painterResource(R.drawable.ic_image_24)
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        val posts = user?.posts ?: emptyList()
        items(posts) {
            PostItem(it)
        }
    }
}

@Composable
private fun PostItem(item: Post) {
    val shape = RoundedCornerShape(16.dp)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(shape = shape, color = Color.White)
            .border(width = 1.dp, color = Purple500, shape = shape)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = item.title,
            style = TypographyText.caption,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = item.title,
            style = TypographyText.body1,
            textAlign = TextAlign.Center
        )
    }
}