package com.example.sportscoreboard.screens.entityDetail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sportscoreboard.domain.Entity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityDetailScreen(entity: Entity, navigateBack: () -> Unit ){

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            navigationIcon = {
                IconButton(onClick = {navigateBack()}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            title = {}
            )
        }
    )
    { padding ->
        Box(Modifier.padding(padding)) {
            Column(Modifier.padding(20.dp)) {

                Row {

                    EntityImage(entity)

                    Box(
                        Modifier
                            .padding(start = 20.dp)
                            .height(100.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            Modifier.align(Alignment.CenterStart)
                        ) {
                            EntityTitle(entity = entity)

                            entity.gender?.let {
                                Text(text = "Gender: $it")
                            }
                            entity.country?.let {
                                Text(text = "Country: $it")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EntityImage(entity: Entity){
    var model: Any = entity.defaultImageSource
    entity.image?.let {
        model = entity.getImagePath()
    }

    Box(
        Modifier
            .border(
                width = 2.dp,
                color = Color.Gray.copy(alpha = 0.5F),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        AsyncImage(model = model, contentDescription = entity.name + " image", modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .padding(5.dp)
        )
    }
}

@Composable
fun EntityTitle(entity: Entity){
    Row(){
        Text(
            text = entity.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = "(${entity.filter.title})",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

