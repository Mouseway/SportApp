package com.example.sportscoreboard.screens.entityDetail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sportscoreboard.domain.Participant

@Composable
fun EntityDetailScreen(entity: Participant){

    Scaffold(
        topBar = {
            TopAppBar() {

            }
        }
    )
    { padding ->
        Box(Modifier.padding(padding)) {
            Column(Modifier.padding(20.dp)) {

                Row() {
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

                    Box(
                        Modifier
                            .padding(start = 20.dp)
                            .height(100.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            Modifier.align(Alignment.CenterStart)
                        ) {
                            Row(){
                                Text(
                                    text = entity.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )

                                Text(
                                    text = "(${entity.filter.title})",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.8F),
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }
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