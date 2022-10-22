package com.example.sportscoreboard.screens.entitiesList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sportscoreboard.domain.ResultState
import com.example.sportscoreboard.domain.Participant
import com.example.sportscoreboard.domain.filters.ParticipantFilter
import com.example.sportscoreboard.navigation.NavigationScreens
import com.example.sportscoreboard.others.composable.Searchbar
import com.squareup.moshi.JsonAdapter
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel

@Composable
fun ParticipantsListScreen(navController: NavController) {
    val viewModel by viewModel<EntitiesViewModel>()
    val scoreRecords = viewModel.scoreRecords.observeAsState()
    val focusManager = LocalFocusManager.current
    val entityAdapter: JsonAdapter<Participant> by inject()

    Scaffold(topBar = {
        Column() {
            TopAppBar() {
                Searchbar(viewModel.searchedText,
                    onSearchClick = {
                        focusManager.clearFocus()
                    },
                    onTextChange = {viewModel.setSearchedText(it)}
                )
            }
            Row(Modifier.padding(horizontal = 5.dp)) {
                ParticipantFilter.values().forEach {
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
                scoreRecords.value?.let {
                    when(it){
                        is ResultState.Error -> Log.i("Scoreboard", it.message ?: "Error")
                        is ResultState.Loading -> if(it.isLoading)  LoadingScreen()
                        is ResultState.Success -> it.data?.let { entity -> ScoreRecordsList(records = entity){ entity ->
                            val json = entityAdapter.toJson(entity)
                            navController.navigate(NavigationScreens.EntityDetailScreen.route + "/" + json)
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
fun ScoreRecordsList(records: List<Participant>, onParticipantClick: (Participant)->Unit){
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
                    ParticipantPreview(participant = it){
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
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Text(
            text = sport,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
fun ParticipantPreview(participant: Participant, onClick: ()->Unit){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ){
        var model:Any = participant.defaultImageSource
        participant.image?.let {
            model = participant.getImagePath()
        }

        AsyncImage(model = model,
            contentDescription = null,
            error = painterResource(id = participant.defaultImageSource),
            modifier = Modifier.height(30.dp))

        Text(text = participant.name, fontSize = 15.sp, modifier = Modifier.padding(horizontal = 20.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ParticipantTypeChip(text: String, onClick: ()->Unit, selected: Boolean){
    FilterChip(
        selected = selected,
        onClick = { onClick() },
        colors = ChipDefaults.filterChipColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            selectedBackgroundColor = MaterialTheme.colors.secondary
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {
        Text(
            text = text,
            color = if(selected) MaterialTheme.colors.onSecondary else MaterialTheme.colors.onSurface
        )
    }
}

