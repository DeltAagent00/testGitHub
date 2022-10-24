package com.example.testgithub.ui.listUsers.shimmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testgithub.ui.component.ShimmerBrush

@Composable
fun UserListShimmer(modifier: Modifier) {
    val brush = ShimmerBrush()
    val countItem = 8

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(countItem) {
            item {
                UserItemShimmer(brush = brush)
            }
        }
    }
}