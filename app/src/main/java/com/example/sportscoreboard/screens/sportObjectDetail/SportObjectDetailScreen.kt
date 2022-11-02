package com.example.sportscoreboard.screens.sportObjectDetail

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sportscoreboard.R
import com.example.sportscoreboard.domain.SportObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportObjectDetailScreen(sportObject: SportObject, navigateBack: () -> Unit ){

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

                Row(verticalAlignment = Alignment.CenterVertically) {

                    SportObjectImage(sportObject)

                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp )
                    ){
                        SportObjectTitle(sportObject = sportObject)

                        sportObject.gender?.let {
                            Text(text = stringResource(R.string.gender) + ": $it")
                        }
                        sportObject.country?.let {
                            Text(text = stringResource(R.string.country) + ": $it")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SportObjectImage(sportObject: SportObject){
    var model: Any = sportObject.defaultImageSource
    sportObject.image?.let {
        model = sportObject.getImagePath()
    }

    Box(
        Modifier
            .border(
                width = 2.dp,
                color = Color.Gray.copy(alpha = 0.5F),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        AsyncImage(model = model, contentDescription = sportObject.name + " image", modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .padding(5.dp)
        )
    }
}

@Composable
fun SportObjectTitle(sportObject: SportObject){
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)){
                append(sportObject.name)
            }
            withStyle(
                SpanStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8F)
                )
            ){
                append(" (${sportObject.filter.title})")
            }
        }
    )
}


