package com.kintaro.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kintaro.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeApp() {
    Column(
        Modifier.fillMaxSize()
    ) {
        LemonadeBanner(
            Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        LemonadeCard(
            Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        )
    }
}

@Composable
fun LemonadeBanner(modifier: Modifier = Modifier) {
    Spacer(
        modifier.statusBarsPadding()
    )
    Box(
        modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun LemonadeCard(modifier: Modifier = Modifier) {
    var stage by remember { mutableIntStateOf(1) }
    var squeezesNeeded by remember { mutableIntStateOf(0) }

    when(stage) {
        1 -> ImageAndText(
            modifier = modifier,
            imageResourceId = R.drawable.lemon_tree,
            imageDescriptionResourceId = R.string.lemon_tree,
            instructionsResourceId = R.string.pluck,
            onImageClick = {
                squeezesNeeded = (2..4).random()
                stage = 2
            }
        )
        2 -> ImageAndText(
            modifier = modifier,
            imageResourceId = R.drawable.lemon_squeeze,
            imageDescriptionResourceId = R.string.lemon,
            instructionsResourceId = R.string.squeeze,
            onImageClick = {
                squeezesNeeded--
                if(squeezesNeeded == 0) {
                    stage = 3
                }
            }
        )
        3 -> ImageAndText(
            modifier = modifier,
            imageResourceId = R.drawable.lemon_drink,
            imageDescriptionResourceId = R.string.glass_of_lemonade,
            instructionsResourceId = R.string.drink,
            onImageClick = {
                stage = 4
            }
        )
        4 -> ImageAndText(
            modifier = modifier,
            imageResourceId = R.drawable.lemon_restart,
            imageDescriptionResourceId = R.string.empty_glass,
            instructionsResourceId = R.string.restart,
            onImageClick = {
                stage = 1
            }
        )
    }
}

@Composable
fun ImageAndText(
    modifier: Modifier = Modifier,
    imageResourceId: Int,
    imageDescriptionResourceId: Int,
    instructionsResourceId: Int,
    onImageClick: () -> Unit
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onImageClick,
            shape = RoundedCornerShape(32.dp),
            colors = ButtonColors(
                Color(0xFFb0e8cc),
                Color.Unspecified,
                Color.Unspecified,
                Color.Unspecified
            )
        ) {
            Image(
                painter = painterResource(imageResourceId),
                contentDescription = stringResource(imageDescriptionResourceId)
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(instructionsResourceId)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeAppPreview() {
    LemonadeTheme {
        LemonadeApp()
    }
}