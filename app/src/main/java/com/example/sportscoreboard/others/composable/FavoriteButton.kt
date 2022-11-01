package com.example.sportscoreboard.others.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FavoriteButton(favorite: Boolean, onClick: () -> Unit){
    IconButton(onClick = {onClick()}) {
        val icon = if(favorite) Icons.Filled.Star else Icons.Outlined.StarBorder
        val tint =
            if(favorite)
                Color(0xffffcd00)
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)

        Icon(imageVector = icon, contentDescription = "Favorite", tint = tint)
    }
}