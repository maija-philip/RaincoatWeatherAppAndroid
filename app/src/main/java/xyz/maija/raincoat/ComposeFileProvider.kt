package xyz.maija.raincoat

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import xyz.maija.raincoat.R
import java.io.File

// https://fvilarino.medium.com/using-activity-result-contracts-in-jetpack-compose-14b179fb87de

/*
    Get the URI of an image taken from the camera
 */
class ComposeFileProvider: FileProvider(
    R.xml.filepaths
) {
    companion object {
        fun getImageUri(context: Context): Uri {

            val directory = File(context.cacheDir, "images")
            directory.mkdirs()

            val file = File.createTempFile(
                "selected_image_",
                ".jpg",
                directory
            )

            val authority = context.packageName + ".fileprovider"

            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}