package xyz.maija.raincoat.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import xyz.maija.raincoat.ComposeFileProvider
import xyz.maija.raincoat.utils.rubikFont
import java.io.FileDescriptor

// camera https://fvilarino.medium.com/using-activity-result-contracts-in-jetpack-compose-14b179fb87de

// some of the image picker from https://medium.com/@jpmtech/jetpack-compose-display-a-photo-picker-6bcb9b357a3a

// permission launcher https://medium.com/@dheerubhadoria/capturing-images-from-camera-in-android-with-jetpack-compose-a-step-by-step-guide-64cd7f52e5de

// convert to bit map
// https://stackoverflow.com/questions/77624700/how-to-get-drawable-to-bitmap-from-asyncimage-coil-jetpack-compose


@Composable
fun PickImageGetAvgColor(
    setAvgColor: (Color) -> Unit,
    usesCamera: Boolean,
    isSecondary: Boolean
) {

    val context = LocalContext.current

    var hasImage by remember {
        mutableStateOf(false)
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

   // select image from photo picker
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            hasImage = uri != null
            imageUri = uri
        } // onResult
    ) // imagePicker

    // take image from camera
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        } // onResult
    ) // cameraLauncher

    Box() {

        if (isSecondary) {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    hasImage = false

                    if (usesCamera) {
                        val uri = ComposeFileProvider.getImageUri(context)
                        imageUri = uri
                        cameraLauncher.launch(uri)
                    } else {
                        imagePicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    } // else use camera picker
                }, // onclick
            ) {
                Text(
                    text = if(usesCamera) "Re-take Image" else "Pick New Image",
                    fontFamily = rubikFont,
                )
            }
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    hasImage = false

                    if (usesCamera) {
                        val uri = ComposeFileProvider.getImageUri(context)
                        imageUri = uri
                        cameraLauncher.launch(uri)
                    } else {
                        imagePicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    } // else use camera picker
                }, // onclick
            ) {
                Text(
                    text = if(usesCamera) "Take Image" else "Pick Image",
                    fontFamily = rubikFont,
                )
            } // button
        } // else not secondary



        // when the image uri is available, get the average color
        if (hasImage && imageUri != null) {
            if (imageUri != null) {
                val bitmap = uriToBitmap(imageUri!!, context) // we checked to make sure not null
                if (bitmap != null) {
                    val avgColor = bitmapToAvgColor(bitmap)
                    setAvgColor(avgColor) // set the state
                } else {
                    Log.d("MEP", "ImagePicker: bitmap is null")
                } // else bitmap null
            } // if uri not null
        }

    } // box
} // Image picker

private fun bitmapToAvgColor(bitmap: Bitmap) : Color {
    var red = 0;
    var green = 0;
    var blue = 0;
    var numPixels = 0;

    for (y in 0..bitmap.height - 1) {
        for (x in 0..bitmap.width - 1) {
            val c = bitmap.getPixel(x, y)
            numPixels++;

            red += c.red
            green += c.green
            blue += c.blue
        }
    }

    val resultColor = Color(red / numPixels, green / numPixels, blue / numPixels)
    return resultColor
}

// https://hamzaasif-mobileml.medium.com/android-capturing-images-from-camera-or-gallery-as-bitmaps-kotlin-50e9dc3e7bd3
private fun uriToBitmap(selectedFileUri: Uri, context: Context): Bitmap? {

    try {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(selectedFileUri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}