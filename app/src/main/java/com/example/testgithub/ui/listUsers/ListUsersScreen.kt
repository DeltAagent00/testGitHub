package com.example.testgithub.ui.listUsers

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.testgithub.R
import com.example.testgithub.data.entity.User
import com.example.testgithub.ui.listUsers.shimmer.UserListShimmer
import com.example.testgithub.ui.route.Route
import com.example.testgithub.ui.theme.Purple500
import com.example.testgithub.ui.theme.TypographyText
import com.google.gson.Gson

@Composable
fun ListUsersScreen(
    navigation: NavHostController,
    viewState: ListUsersScreenViewState,
    gson: Gson
) {
    if (viewState.isFistLoader) {
        UserListShimmer(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        )
    } else {
        UserList(
            users = viewState.users,
            onSelectedUser = {
                val arg = Uri.encode(gson.toJson(it))
                navigation.navigate(Route.DetailScreen.value + "/$arg")
            }
        )
    }
}

@Composable
private fun UserList(users: List<User>, onSelectedUser: (User) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(users) { item ->
            UserItem(
                user = item,
                onSelectedUser = onSelectedUser
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UserItem(user: User, onSelectedUser: (User) -> Unit) {
    val shape = RoundedCornerShape(16.dp)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = shape)
            .clip(shape = shape)
            .clickable(
                onClick = { onSelectedUser.invoke(user) },
                indication = rememberRipple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        val (iconView, titleView, descriptionView, iconRightView, tt) = createRefs()

        Box(
            modifier = Modifier
                .border(width = 1.dp, color = Purple500, shape = shape)
                .constrainAs(tt) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints

                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
        )

        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .constrainAs(iconView) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .border(width = 1.dp, color = Color.Black, shape = CircleShape),
            model = user.thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_account_circle_24)
        )

        Text(
            modifier = Modifier
                .constrainAs(titleView) {
                    width = Dimension.fillToConstraints

                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(iconView.end, margin = 16.dp)
                    end.linkTo(iconRightView.start, margin = 4.dp)
                },
            text = user.name,
            style = TypographyText.caption
        )

        Text(
            modifier = Modifier
                .constrainAs(descriptionView) {
                    width = Dimension.fillToConstraints

                    top.linkTo(titleView.bottom, margin = 8.dp)
                    start.linkTo(titleView.start)
                    end.linkTo(iconRightView.start, margin = 4.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
            text = pluralStringResource(id = R.plurals.count_posts, user.posts.size, user.posts.size),
            style = TypographyText.body1
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right_24),
            contentDescription = null,
            tint = Purple500,
            modifier = Modifier.constrainAs(iconRightView) {
                top.linkTo(parent.top)
                end.linkTo(parent.end, margin = 8.dp)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}