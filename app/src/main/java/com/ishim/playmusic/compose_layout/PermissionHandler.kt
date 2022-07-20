package com.ishim.playmusic.compose_layout

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

private const val TAG = "PERMISSION HANDLER"

@Composable
fun PermissionsChecker() {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
        if (isGranted) {
            Log.d(TAG, "Permission Granted")
        } else {
            Log.d(TAG, "Permission Denied")
            System.exit(0)
        }
    }

    val context = LocalContext.current

    Button(onClick = {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_MEDIA) -> {
                Log.d(TAG, "You can use media files")
            }
            else -> {
                launcher.launch(Manifest.permission.MANAGE_MEDIA)
            }
        }
    }) {
        Text(text = "Check & Request Permissions")
    }
}