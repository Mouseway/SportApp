package com.example.sportscoreboard.others.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.sportscoreboard.R

@Composable
fun Searchbar(value: String, onTextChange: (String)->Unit, onSearchClick: ()->Unit){
    val placeholdersColor = MaterialTheme.colors.onPrimary.copy(alpha = ContentAlpha.medium)
    TextField(
        value = value,
        onValueChange = {
            onTextChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = placeholdersColor,
            backgroundColor = Color.Transparent
        ),
        placeholder = { Text("Zadejte hledaný text", color = placeholdersColor) },
        modifier = Modifier
            .fillMaxWidth(),
        trailingIcon = {
            if(value.isNotEmpty())
                IconButton({
                    onTextChange("")
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = "close",
                        tint = placeholdersColor,
                        modifier = Modifier.padding(17.dp)
                    )
                }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
            }
        )
    )
}