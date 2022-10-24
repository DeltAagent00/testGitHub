package com.example.testgithub.ui.listUsers.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun UserItemShimmer(
    brush: Brush
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
    ) {
        val shapeRound = RoundedCornerShape(8.dp)

        val (iconView, titleView, descriptionView) = createRefs()

        Spacer(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .background(brush = brush)
                .constrainAs(iconView) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 16.dp)
                    bottom.linkTo(parent.bottom)
                }
        )

        Spacer(modifier = Modifier
            .height(22.dp)
            .background(brush = brush, shape = shapeRound)
            .constrainAs(titleView) {
                width = Dimension.fillToConstraints

                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(iconView.end, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )

        Spacer(modifier = Modifier
            .height(22.dp)
            .background(brush = brush, shape = shapeRound)
            .constrainAs(descriptionView) {
                width = Dimension.fillToConstraints

                top.linkTo(titleView.bottom, margin = 8.dp)
                start.linkTo(titleView.start)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
            }
        )
    }
}