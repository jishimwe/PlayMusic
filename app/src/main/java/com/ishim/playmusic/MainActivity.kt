package com.ishim.playmusic

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ishim.playmusic.compose_layout.MyTheme
import com.ishim.playmusic.compose_layout.ScaffoldBarsGeneric

private const val TAG = "MAIN ACTIVITY"
const val MANAGE_MEDIA_REQUEST_CODE = 1
const val READ_EXTERNAL_STORAGE_CODE = 2
const val ACCESS_MEDIA_LOCATION_CODE = 3
const val REQUEST_PERMISSION_CODE = 11
private val REQUIRED_MEDIA_PERMISSIONS = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_MEDIA_LOCATION)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()

        val musicDB = MusicDB(this)

//        val currentlyPlaying = CurrentlyPlaying(this, MediaPlayer(), null)

        setContent {
            MyTheme {
//                ScaffoldBars(songs = MusicDB.getTracks(this))
                ScaffoldBarsGeneric(context = this, list = listOf(), musicDB, null)
            }
        }
    }

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(this, "This permission is required to access media files", Toast.LENGTH_SHORT)
        }
    }

    private fun checkPermissions() {
        Log.d(TAG, "Checking permissions")
        val missingPermissions = mutableListOf<String>()

        for (p in REQUIRED_MEDIA_PERMISSIONS) {
            val res = ContextCompat.checkSelfPermission(this, p)
            if (res != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, arrayOf(p), REQUEST_PERMISSION_CODE)
                missingPermissions += p
            }
        }

        if (missingPermissions.isNotEmpty()) {
            Log.d(TAG, "Missing permissions: $missingPermissions")
            val permissions = missingPermissions.toTypedArray()

//            val intent = Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA)
//            intent.data = Uri.parse("package"+this.packageName)
//            startActivity(intent)

            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE)
            /*for (p in missingPermissions) {
                val intent =
                when (p) {
                    Manifest.permission.MANAGE_MEDIA -> Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA)
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE -> Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    Manifest.permission.ACCESS_MEDIA_LOCATION -> Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA)
                    else -> {null}
                }
                if (intent != null) {
                    intent.data = Uri.parse("package"+this.packageName)
                    startActivity(intent)
                }
            }*/
/*            for (p in missingPermissions) {
                requestPermissionLauncher.launch(p)
                Log.d(TAG, "$p queried")
            }*/
        } else {
            val grantResults = IntArray(REQUIRED_MEDIA_PERMISSIONS.size)
            grantResults.fill(PackageManager.PERMISSION_GRANTED)
            onRequestPermissionsResult(REQUEST_PERMISSION_CODE, REQUIRED_MEDIA_PERMISSIONS, grantResults)
        }

    }

    private fun checkPermission(permission: String, requestCode: Int) {
        Log.d(TAG, "Check Permission [Code: $requestCode, $permission]")
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
//            val intent = Intent(Settings.ACTION_REQUEST_MANAGE_MEDIA)
//            intent.data = Uri.parse("package"+this.packageName)
//            startActivity(intent)
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }


    /* This function is called when the user accepts or decline the permission.
     Request Code is used to check which permission called this function.
     This request code is provided when the user is prompt for permission.*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "On Request Permissions Result $requestCode $permissions $grantResults")

        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                Log.d(TAG, "Request Code: ${REQUEST_PERMISSION_CODE == requestCode} ${permissions.size} ${permissions.indices}")
                for (i in permissions.indices) {
                    Log.d(TAG, "Permission required: ${permissions[i]}")
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "${permissions[i]} not granted")
                        permissionResultsToasts(false, permissions[i])
//                        finish()
                        return
                    } else {
                        Log.d(TAG, "${permissions[i]} granted")
                        permissionResultsToasts(true, permissions[i])
                    }
                }
            }
            else -> {
                Log.d(TAG, "WTF if happening?")
                finish()
            }
        }
    }

    private fun permissionResultsToasts(granted: Boolean, permission: String) {
        Log.d(TAG, "Permission Result Toast")
        if (granted) {
            Toast.makeText(this, "$permission Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "$permission Permission Required\n Exiting...", Toast.LENGTH_SHORT).show()
        }
    }

}