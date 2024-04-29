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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    Log.d("MEP", "AskForPermission: Here")

    if (permissionState.status.isGranted) {
        Log.d("MEP", "AskForPermission: Granted")
        Toast.makeText(context,"$permissionName Permission Granted", Toast.LENGTH_LONG).show()

    } else if (!permissionState.status.shouldShowRationale) {
        Log.d("MEP", "AskForPermission: asked first time or denied perm?")
        // if permission is asked for the first time or denied permanently
        context.isPermissionAskedForFirstTime(permission)
            .also { result ->
                // if it's the first time asked
                if (result) {
                    Log.d("MEP", "AskForPermission: should ask")
                    // will run everytime it gets recomposed
                    SideEffect {
                        permissionState.launchPermissionRequest()
                    }
                    context.permissionAskedForFirstTime(permission)
                } else {
                    Log.d("MEP", "AskForPermission: got here?")
                    SideEffect {
                        permissionState.launchPermissionRequest()
                    }
                }
            } // also
    } else if (permissionState.status.shouldShowRationale) {
        Log.d("MEP", "AskForPermission: why here")
        ShowRationaleContent(textToShow = reason) {
            permissionState.launchPermissionRequest()
        }
    } // else if should show rationale
} // askForPermission

@Composable
private fun ShowRationaleContent(textToShow: String, showPermissionDialog: () -> Unit) {
    val openDialog = remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = {
            // needed for when user taps outside dialog to close
            openDialog.value = false
        },
        title = {
            Text(text = "Permission Required")
        },
        text = {
            Text(text = textToShow)
        },
        confirmButton = {
            Button(onClick = {
                openDialog.value = false
                showPermissionDialog()
            }) {
                Text(text = "Ok")
            }
        } // ConfirmButton
    )// Alert Dialog

} // rationale dialog

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