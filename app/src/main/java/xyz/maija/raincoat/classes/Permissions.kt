package xyz.maija.raincoat.classes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

/*
    Has all of the functionality of asking for permissions in one place with a call to AskForPermission() function so it's easy to access anywhere you need to ask for a permission.
 */

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun AskForPermission(
    permission: String,
    permissionName: String,
    permissionState: PermissionState,
    reason: String, context: Context
) {

    var toastAlreadyDisplayed by remember { mutableStateOf(false) }

    if (permissionState.status.isGranted) {
        if (!toastAlreadyDisplayed) {
            toastAlreadyDisplayed = true
            Toast.makeText(context,"$permissionName Permission Granted", Toast.LENGTH_LONG).show()
        } // if not already displayed
    } else {
        // if permission is asked for the first time or denied permanently
        SideEffect {
            permissionState.launchPermissionRequest()
        }
    }
} // askForPermission

/*
    Additions to Context for Permissions
 */

// add this property to context
fun Context.isPermissionAskedForFirstTime(permission: String): Boolean {
    return getSharedPreferences(packageName, Context.MODE_PRIVATE).getBoolean(permission, true) // default to true
} // is permission first time asked for

// setting that it was asked for for the first time
fun Context.permissionAskedForFirstTime(permission: String) {
    getSharedPreferences(
        packageName, Context.MODE_PRIVATE
    ).edit().putBoolean(permission,false).apply()
} // permission asked for for the first time

// open application settings dialog
fun Context.openApplicationSettings() {
    startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.parse("package:${packageName}")
    })
} // openApplicationSettings