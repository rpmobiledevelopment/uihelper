package onPermission

import android.Manifest.permission
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionsFragment : Fragment() {

    private val TAG = PermissionsFragment::class.java.simpleName
    private var callback: PermissionCallback? = null

    interface PermissionCallback {
        fun onPermissionsResult(allPermissionsGranted: Boolean)
    }

    private val multiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val allGranted = !permissions.containsValue(false)
            callback?.onPermissionsResult(allGranted)
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PermissionCallback) {
            callback = context
        }
    }

    fun requestPermissions(activity: Activity?, opt: String) {
        when (opt) {
            "DOWNLOAD_ACCESS_", "DOWNLOAD_ACCESS" -> {
                multiplePermissionLauncher.launch(arrayOf(permission.CAMERA))
            }

            "NOTIFICATION" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    multiplePermissionLauncher.launch(arrayOf(permission.POST_NOTIFICATIONS))
                }
            }

            "RECORD_AUDIO" -> {
                multiplePermissionLauncher.launch(arrayOf(permission.RECORD_AUDIO))
            }

            "LOCATION" -> {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permission.ACCESS_FINE_LOCATION)) {
                    buildAlertMessageNoGps(activity)
                } else {
                    multiplePermissionLauncher.launch(arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION))
                }
            }

            else -> {
                multiplePermissionLauncher.launch(arrayOf(permission.CAMERA))
            }
        }
    }

    fun requestPermissions(opt: String?) {
        when (opt) {
            "DOWNLOAD_ACCESS_", "DOWNLOAD_ACCESS" -> {
                multiplePermissionLauncher.launch(arrayOf(permission.CAMERA))
            }

            "NOTIFICATION" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    multiplePermissionLauncher.launch(arrayOf(permission.POST_NOTIFICATIONS))
                }
            }

            "RECORD_AUDIO" -> {
                multiplePermissionLauncher.launch(arrayOf(permission.RECORD_AUDIO))
            }

            else -> {
                multiplePermissionLauncher.launch(arrayOf(permission.CAMERA))
            }
        }
    }

    private fun buildAlertMessageNoGps(activity: Activity?) {
        AlertDialog.Builder(activity)
            .setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity?.packageName, null)
                }
                activity?.startActivity(intent)
            }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }
}

