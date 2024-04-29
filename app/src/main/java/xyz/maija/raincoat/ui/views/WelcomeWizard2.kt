package xyz.maija.raincoat.ui.views

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import xyz.maija.raincoat.R
import xyz.maija.raincoat.classes.AskForPermission
import xyz.maija.raincoat.data.entities.User
import xyz.maija.raincoat.navigation.Screen
import xyz.maija.raincoat.utils.rubikFont
import xyz.maija.raincoat.ui.theme.RaincoatTheme

/*
   Allows the user to choose a skin tone based on a taken or picked image of themselves.
 */

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WelcomeWizard2(
    navController: NavController,
    previousScreen: Screen,
    setSkinColor: (Color) -> Unit,
    setPreviousScreen: (Screen) -> Unit,
    updateCustomerInDB: () -> Unit,
    modifier: Modifier = Modifier
) {

    var skinColor by remember { mutableStateOf(User.DEFAULT_SKIN_COLOR) }
    val isDarkMode = isSystemInDarkTheme()
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    AskForPermission(
        permission = Manifest.permission.CAMERA,
        permissionName = "Camera",
        permissionState = cameraPermissionState,
        reason = "Raincoat needs your camera permission in order let you take a picture to get your average skin color. If you deny you will be prompted to pick a photo instead.",
        context = context
    )

    fun navigateToNextScreen() {
        // val tempPrevScreen = previousScreen don't think i need this bc i'm not getting a changed version of prevscreen
        setSkinColor(skinColor)
        setPreviousScreen(Screen.WelcomeWizard2)
        updateCustomerInDB()

        // check to see if we go on to the homepage or back to settings
        if (previousScreen == Screen.WelcomeWizard1 || previousScreen == Screen.LocationPage) {
            // navigate and clear the stack so the only thing on the stack is the homepage,
            // you can't go back through the welcome wizard
            navController.navigate(Screen.HomePage.route) {
                launchSingleTop = true
                popUpTo(Screen.WelcomeWizard1.route) {
                    // so we only have one main screen on the stack at a time
                    inclusive = true
                } // popUpTo
            } // navcontroller.navigate
        } else {
         // go back to settings
            navController.popBackStack()
        }
    } // navigateToNextScreen

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Customize your skin color",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = rubikFont,
                fontSize = 40.sp,
                lineHeight = 45.sp,
                textAlign = TextAlign.Center
            ) // Welcome

            Text(
                text = "Click below to take an image of a patch of skin for your character",
                fontFamily = rubikFont,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
        } // Header Text Column

        Image(
            painter = painterResource(id = if (isDarkMode) R.drawable.bald_dark else R.drawable.bald_light),
            contentDescription = "Bald headshot",
            modifier = Modifier
                .size(300.dp)
                .background(skinColor)
                .border(4.dp, MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Fit,
        )
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (skinColor != User.DEFAULT_SKIN_COLOR) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navigateToNextScreen() }
                ) {
                    Text("Continue", fontFamily = rubikFont)
                }
            } // if skin color has changed
            PickImageGetAvgColor(
                setAvgColor = { newColor ->
                  skinColor = newColor
                },
                usesCamera = cameraPermissionState.status.isGranted,
                isSecondary = skinColor != User.DEFAULT_SKIN_COLOR
            )
            if (skinColor == User.DEFAULT_SKIN_COLOR ) {
                TextButton(onClick = {
                    navigateToNextScreen()
                }) {
                    Text("Skip For Now", fontFamily = rubikFont)
                }
            } // if skin is not changed
        } // Buttons Column
    } // overarching column



} // WelcomeWizard2


@Preview(showBackground = true)
@Composable
fun WelcomeWizard2Preview() {
    val navController = rememberNavController()
    RaincoatTheme {
        WelcomeWizard2(
            navController,
            previousScreen = Screen.WelcomeWizard1,
            setSkinColor = { },
            setPreviousScreen = {},
            updateCustomerInDB = {}
            )
    }
}