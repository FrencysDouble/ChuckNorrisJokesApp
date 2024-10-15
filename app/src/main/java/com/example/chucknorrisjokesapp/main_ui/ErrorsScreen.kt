package com.example.chucknorrisjokesapp.main_ui

import android.widget.Space
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chucknorrisjokesapp.R
import com.example.chucknorrisjokesapp.ui.theme.ColorItemList

object ErrorsScreens {

    @Composable
    fun LoadingBadge()
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                Modifier.size(150.dp),
                color = ColorItemList
            )
        }
    }

    @Composable
    fun ConnectionError()
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Filled.WifiOff , contentDescription ="",tint = Color.White,modifier = Modifier.size(24.dp))
                Spacer(Modifier.padding(6.dp))
                Text(stringResource(id = R.string.connection_error_text), fontSize = 18.sp, fontWeight = FontWeight.Bold,color = Color.White)

            }
        }
    }
}