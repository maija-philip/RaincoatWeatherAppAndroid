package xyz.maija.raincoat.ui.views

import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.maija.raincoat.R
import xyz.maija.raincoat.ui.theme.RaincoatTheme
import xyz.maija.raincoat.utils.rubikFont

@Composable
fun LogoScreen() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Log.d("MEP", "LogoScreen: height $screenHeight, width $screenWidth")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF014299)), // dark blue
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.scorching),
            contentDescription = "Bald tan skinned person wearing a crop top and short shorts",
            contentScale = ContentScale.Crop,
             modifier = Modifier.offset(x = 50.dp, y = screenHeight/5)
        )

    } // Box
} // Logo Screen

@Preview(showBackground = true)
@Composable
fun LogoScreenPreview() {
    RaincoatTheme {
        LogoScreen()
    }
}