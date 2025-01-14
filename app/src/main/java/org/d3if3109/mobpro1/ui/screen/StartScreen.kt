package org.d3if3109.mobpro1.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3109.mobpro1.R
import org.d3if3109.mobpro1.network.UserDataStore

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartScreen(onFinished: () -> Unit) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            StartPage(page)
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = Color.Blue,
            inactiveColor = Color.Gray,
            indicatorWidth = 12.dp,
            indicatorHeight = 12.dp,
            spacing = 8.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pagerState.currentPage > 0) {
                Button(onClick = {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                }) {
                    Text("Back")
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
            Button(onClick = {
                if (pagerState.currentPage < 1) {
                    scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                } else {
                    onFinished()
                }
            }, colors =  ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary)) {
                Text(if (pagerState.currentPage == 1) "Finish" else "Next")
                if (pagerState.currentPage == 1) {
                    CoroutineScope(Dispatchers.IO).launch { signIn(context, dataStore) }
                }
            }
        }
    }
}

@Composable
fun StartPage(page: Int) {
    when (page) {
        0 -> PageContent(stringResource(id = R.string.startscreen), stringResource(id = R.string.startscreen1))
    }
}

@Composable
fun PageContent(title: String, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen(onFinished = {})
}
