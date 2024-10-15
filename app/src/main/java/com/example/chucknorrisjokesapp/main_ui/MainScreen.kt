package com.example.chucknorrisjokesapp.main_ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.chucknorrisjokesapp.R
import com.example.chucknorrisjokesapp.controllers.MainScreenController
import com.example.chucknorrisjokesapp.ui.theme.ColorBackGround
import com.example.chucknorrisjokesapp.ui.theme.ColorItemList
import kotlinx.coroutines.delay


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    controller: MainScreenController)
{
    Scaffold(Modifier.fillMaxSize().background(ColorBackGround)) {
        Screen(controller)
    }

    LaunchedEffect(Unit)
    {
        controller.loadJokesHistory()
    }

    LaunchedEffect(Unit) {
        while (true)
        {
            delay(5 * 60 * 1000L)
            controller.getData()
        }
    }
}


@Composable
//@Preview(name = "Landscape Mode" , showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 640, heightDp = 360)
//@Preview(name = "Portrait Mode" , showBackground = true, device = Devices.PIXEL_7)
private fun Screen(controller: MainScreenController) {

    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    val isLoading by controller.isLoading.observeAsState(false)
    val isConnectionError by controller.isConnectionError.observeAsState(false)
    val jokeLiveData by controller.jokeData.observeAsState()
    val jokeHistoryLiveData by controller.jokeHistory.observeAsState()
    val selectedJoke by controller.selectedJoke.observeAsState()


    Column(Modifier.fillMaxSize().background(ColorBackGround)
        .then(if (isPortrait) Modifier.padding(vertical = 24.dp)
        else Modifier.padding(start = 55.dp,end = 50.dp,top = 24.dp)))
    {

        upperText()

        JokeBox(
            modifier = if (isPortrait) Modifier.weight(1f) else Modifier.fillMaxHeight(0.5f),
            isPortrait = isPortrait,
            value = selectedJoke ?: jokeLiveData?.value,
            isLoading,
            isConnectionError,
        )

        BottomList(modifier = if (isPortrait) Modifier.wrapContentHeight().padding(12.dp)
        else Modifier.fillMaxHeight(0.6f),
            jokeHistoryLiveData,
            controller
            )

        JokeGenerateBtn(
            modifier = if (isPortrait) Modifier.wrapContentHeight().padding(bottom = 40.dp)
        else Modifier.fillMaxHeight(0.7f).padding(top = 6.dp).height(12.dp),
            controller
        )
    }
}

@Composable
private fun upperText()
{
    Row(Modifier.fillMaxWidth().padding(bottom = 12.dp,top = 12.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.main_text), fontSize = 18.sp,color = Color.White, fontWeight = FontWeight.Bold)
    }
}


@Composable
private fun JokeBox(
    modifier: Modifier = Modifier,
    isPortrait: Boolean,
    value: String?,
    isLoading: Boolean,
    isConnection: Boolean
) {
    Box(modifier = modifier.then(
        if (isPortrait) Modifier.height(100.dp) else Modifier.wrapContentHeight()
    )) {
        if(isPortrait) {
            Image(
                painter = painterResource(R.drawable.chuck),
                contentDescription = "",
                modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        if(isLoading)
        {
            ErrorsScreens.LoadingBadge()
        }
        if (isConnection)
        {
            ErrorsScreens.ConnectionError()
        }
        value?.let {
            Text(
                it,
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center).padding(12.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun BottomList(
    modifier: Modifier,
    liveData: List<String>?,
    controller: MainScreenController
) {
    Column(modifier) {
        Text(stringResource(R.string.previous_jokes_list), fontSize = 22.sp, color = Color.White, fontWeight = FontWeight.Bold)
        if (liveData.isNullOrEmpty()) {
            Text(stringResource(R.string.no_previoos_jokes_list), color = Color.White)
        } else {
            LazyColumn(Modifier.wrapContentSize()) {
                items(liveData.reversed()) { item ->
                    BottomListItem(item,controller)
                }
            }
        }

    }
}
@Composable
private fun BottomListItem(item: String, controller: MainScreenController)
{
    Spacer(Modifier.padding(12.dp))
    Row(Modifier.fillMaxWidth().wrapContentHeight(), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(ColorItemList),
            contentAlignment = Alignment.Center)
        {
            IconButton(onClick = {controller.selectJoke(item)}) {
                Icon(Icons.Filled.CheckCircleOutline, contentDescription = "", tint = Color.White)
            }
        }
        Spacer(Modifier.padding(6.dp))
        Text(item,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White)
    }
}

@Composable
private fun JokeGenerateBtn(modifier: Modifier, controller: MainScreenController)
{
    Button(onClick = {
        controller.getData()
        controller.deselectJoke() },
        modifier.fillMaxWidth(),
        colors = ButtonColors(
            containerColor = ColorItemList,
            contentColor = Color.White,
            disabledContentColor = Color.White,
            disabledContainerColor = ColorItemList
        )
    ) {
        Text(stringResource(R.string.btn_text), fontSize = 18.sp,color = Color.White)
    }

}






