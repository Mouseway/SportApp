package com.example.sportscoreboard.others.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.example.sportscoreboard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Searchbar(value: String, onTextChange: (String)->Unit, onSearchClick: ()->Unit){
    val placeholdersColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = ContentAlpha.medium)
    TextField(
        value = value,
        onValueChange = {
            onTextChange(it)
        },
        textStyle = TextStyle(fontSize = 18.sp),
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            focusedIndicatorColor = Color.Transparent,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.secondary,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
            ),
            focusedSupportingTextColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.enter_text), color = placeholdersColor) },
        trailingIcon = {
            if(value.isNotEmpty())
                IconButton({
                    onTextChange("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close",
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
        ),
        singleLine = true
    )
}