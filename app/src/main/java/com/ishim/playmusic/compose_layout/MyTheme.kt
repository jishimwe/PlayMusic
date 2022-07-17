package com.ishim.playmusic.compose_layout

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import com.ishim.playmusic.SongCardInfo

// Defining Colors
val darkSkyBlue = Color(0xff98c1d9)
val blueVioletCrayola = Color(0xFF6969B3)
val purpleMountain = Color(0xff9b82c4)
val palatinatePurple = Color(0xff4b244a)
val gunMetal = Color(0xff223843)
val errorColor = Color(0xD3B00020)

private val DarkColors = darkColors(
    primary = blueVioletCrayola,
    secondary = purpleMountain,
    secondaryVariant = darkSkyBlue
)

private val LightColors = lightColors(
    primary = palatinatePurple,
    secondary = gunMetal,
    secondaryVariant = darkSkyBlue
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

@Composable
fun SongCard(song: SongCardInfo) {
//    Row (modifier = Modifier.padding(all = 8.dp)){
//        Image(
//            painter = painterResource(id = R.drawable.ic_album),
//            contentDescription = "Contact profile picture",
//            modifier = Modifier
//                .size(50.dp, 50.dp)
//                .clip(CircleShape)
//                .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
//        )
//    }
}

class MyTheme {
}