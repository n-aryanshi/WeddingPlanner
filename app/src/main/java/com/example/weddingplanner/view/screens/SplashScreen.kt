package com.example.weddingplanner.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weddingplanner.R
import com.example.weddingplanner.ui.theme.Ivor
import com.example.weddingplanner.ui.theme.RosyPink
import kotlinx.coroutines.delay

@Composable
@Preview
fun SplashScreen(
    modifier: Modifier = Modifier,
                 onNavigateNext: () -> Unit = {}
) {
    // 🕒 Launch effect to delay and navigate
    LaunchedEffect(Unit) {
        delay(2000) // wait 2 seconds
        onNavigateNext()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ){
        Image(
            painter = painterResource(R.drawable.splash),
            contentDescription = "splash",
            modifier = Modifier.height(221.dp).width(185.dp)
        )

        Text (
            text = "Welcome to Bauer Bodoni !!",
            fontFamily = FontFamily.Cursive,
            color = RosyPink,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold
        )


    }

}

