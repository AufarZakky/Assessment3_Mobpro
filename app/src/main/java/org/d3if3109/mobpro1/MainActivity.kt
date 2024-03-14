package org.d3if3109.mobpro1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3109.mobpro1.ui.screen.MainScreen
import org.d3if3109.mobpro1.ui.theme.Mobpro1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Mobpro1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Aufar hadni Azzakky")
                    MainScreen()
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreen(content: @Composable (Modifier) -> Unit) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title =  {
//                    Text(text = stringResource(id = R.string.app_name))
//                },
//                colors = TopAppBarDefaults.mediumTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
//                )
//            )
//        }
//    ) { padding ->
//        content(Modifier.padding(padding))
//    }
//}
//fun Greeting(name: String, nim: String, kelas: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Halo nama saya $name\n dengan nim $nim\n kelas $kelas",
//        modifier = modifier
//    )
//}


//@Composable
//fun Greeting(name: String) {
//    MainScreen {
//        Text(
//            text = "Hello $name!",
//            modifier = it
//        )
//    }
//}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    Mobpro1Theme {
       MainScreen()
    }
}


//fun GreetingPreview() {
//    Mobpro1Theme {
//        Greeting(" Aufar Hadni Azzakky","6706223109", "D3IF-46-03" )
//    }
//}