package com.example.sportscoreboard.screens.entitiesList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.filters.EntityFilter
import com.example.sportscoreboard.others.composable.Searchbar
import org.koin.androidx.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantsListScreen(navigateToDetail: (Entity) -> Unit) {
    val viewModel by viewModel<EntitiesViewModel>()
    val scoreRecords = viewModel.scoreRecords.observeAsState()
    val focusManager = LocalFocusManager.current

    val onClickOnSearch = {
        focusManager.clearFocus()
        viewModel.filterByText()
    }




    Scaffold(topBar = {
        Column() {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                        EntitiesListTopBar(
                            searchedText = viewModel.searchedText,
                            onTextChange = {viewModel.setSearchedText(it)} ,
                            onSearchClick = {onClickOnSearch()}
                        )
                    }
                    )

                    Row(Modifier.padding(horizontal = 5.dp)) {
                        EntityFilter.values().forEach {
                            ParticipantTypeChip(
                                text = it.title,
                                onClick = { viewModel.setParticipantType(it) },
                                selected = (viewModel.participantType == it))
                        }
                    }
                }

    }) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ){
            Column(
                Modifier
                    .padding(top = 10.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())) {
                scoreRecords.value?.let { resultState ->
                    when(resultState){
                        is ResultState.Error -> Log.i("Scoreboard", resultState.message ?: "Error")
                        is ResultState.Loading -> if(resultState.isLoading)  LoadingScreen()
                        is ResultState.Success -> resultState.data?.let { records ->
                            ScoreRecordsList(records = records){
                            navigateToDetail(it)
                        } }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(){
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier
            .fillMaxSize(0.25f)
            .align(Alignment.Center)
            .padding(top = 50.dp)
        )
    }
}


@Composable
fun ScoreRecordsList(records: List<Entity>, onParticipantClick: (Entity)->Unit){
    val bySport = records.groupBy { it.sport }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
        bySport.forEach{ (sport, list) ->
            SportHeader(sport = sport)
            Column(Modifier.padding(10.dp)) {
                list.forEach {
                    ParticipantPreview(entity = it){
                        onParticipantClick(it)
                    }
                }
            }
        }
    }
}

@Composable
fun SportHeader(sport: String){
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Text(
            text = sport,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

@Composable
fun ParticipantPreview(entity: Entity, onClick: ()->Unit){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ){
        var model:Any = entity.defaultImageSource
        entity.image?.let {
            model = entity.getImagePath()
        }

        AsyncImage(model = model,
            contentDescription = null,
            error = painterResource(id = entity.defaultImageSource),
            modifier = Modifier.height(30.dp))

        Text(text = entity.name, fontSize = 15.sp, modifier = Modifier.padding(horizontal = 20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipantTypeChip(text: String, onClick: ()->Unit, selected: Boolean){
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

@Composable
fun EntitiesListTopBar(
    searchedText: String,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit
){
    Box(Modifier.fillMaxSize()){
        Box(Modifier.align(Alignment.CenterStart)){
            Searchbar(searchedText,
                onSearchClick = {
                    onSearchClick ()
                },
                onTextChange = {onTextChange(it)},
            )
        }
        Button(
            onClick = {onSearchClick()},
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(text = "Search")
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                modifier = Modifier.padding(start = 5.dp)
            )
        }
    }
}
