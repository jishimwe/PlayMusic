package com.ishim.playmusic.compose_layout

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ishim.playmusic.R
import com.ishim.playmusic.Song

@Composable
fun PlayerView() {

}

@Composable
fun AlbumArtAndInfo(song: Song) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .animateContentSize()
            .scale(.9f)
    ) {
        Column(modifier = Modifier
            .clickable {}
            .aspectRatio(.8f), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(
                    id = R.drawable.ic_album
                ),
                contentDescription = "Album default image",
                modifier = Modifier
                    .padding(2.dp)
                    .aspectRatio(1f)
                    .border(1.dp, MaterialTheme.colors.primary, RoundedCornerShape(24.dp))
                    .clip(RoundedCornerShape(24.dp))
                    .shadow(2.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = song.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(4.dp)
            )

            Text(
                text = song.artist,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = song.album,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

    }
}

@Composable
fun MediaButtonAndProgressBar(duration: Long) {
    val timeElapsed by remember {
        mutableStateOf(0)
    }
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .animateContentSize()
            .scale(.9f)
            .fillMaxHeight(.6f)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var sliderValue by remember { mutableStateOf(0f) }

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(2f)
                    .weight(.3f)
                    .padding(end = 8.dp, start = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$timeElapsed",
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    color = Color(R.color.purple_200),
                    modifier = Modifier.weight(.15f)
                )

                Slider(
                    value = sliderValue,
                    onValueChange = { newValue -> sliderValue = newValue },
                    colors = SliderDefaults.colors(),
                    modifier = Modifier.weight(.8f)
                )

                Text(
                    text = "$duration",
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    color = Color(R.color.purple_200),
                    modifier = Modifier.weight(.15f)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(.7f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.repeat),
                    contentDescription = "Repeat",
                    Modifier
                        .scale(.5f)
                        .padding(end = 32.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.skip_previous),
                    contentDescription = "Previous",
                    modifier = Modifier
                        .clip(RoundedCornerShape(.5.dp))
                        .padding(start = 8.dp, end = 8.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.play_arrow),
                    contentDescription = "Play",
                    modifier = Modifier
                        .scale(1.25f)
                        .clip(CircleShape)
                        .border(.5.dp, MaterialTheme.colors.primary, CircleShape)
                        .padding(start = 8.dp, end = 8.dp)
                        .background(MaterialTheme.colors.surface)
                )

                Image(
                    painter = painterResource(id = R.drawable.skip_next),
                    contentDescription = "Next",
                    modifier = Modifier
                        .clip(RoundedCornerShape(.5.dp))
                        .padding(start = 8.dp, end = 8.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.shuffle),
                    contentDescription = "Shuffle",
                    Modifier
                        .scale(.5f)
                        .padding(start = 32.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun AlbumArtAndInfoPreview() {
    val song = Song(
        Uri.EMPTY,
        "Scream?",
        "Dreamcatcher?",
        "1st Album?",
        "Dummy",
        180000,
        "Dummy data"
    )
    Column {
        AlbumArtAndInfo(song = song)
        MediaButtonAndProgressBar(duration = 180000)
    }
}