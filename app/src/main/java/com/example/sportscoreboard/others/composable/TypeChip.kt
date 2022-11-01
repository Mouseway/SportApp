package com.example.sportscoreboard.others.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeChip(text: String, onClick: ()->Unit, selected: Boolean){
    FilterChip(
        selected = selected,
        onClick = { onClick() },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurface,
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            selectedLabelColor = MaterialTheme.colorScheme.onSecondary,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 5.dp),
        label = {
            Text(text = text)
        },
        border = FilterChipDefaults.filterChipBorder(
            borderWidth = 0.dp,
            borderColor = Color.Transparent,
            selectedBorderColor = Color.Transparent
        )
    )
}