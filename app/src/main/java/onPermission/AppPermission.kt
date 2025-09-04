package onPermission

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager

class AppPermission {
    private val TAG: String = OnPermission::class.java.simpleName

    constructor()

    constructor(mActivity: Activity?, supportFragmentManager: FragmentManager, permission: String) {
        // Add PermissionsFragment dynamically if it's not already added
        if (supportFragmentManager.findFragmentByTag("PermissionsFragment") == null) {
            supportFragmentManager.beginTransaction()
                .add(PermissionsFragment(), "PermissionsFragment")
                .commitNow()
        }

        // Request permissions using the fragment
        val fragment =
            supportFragmentManager.findFragmentByTag("PermissionsFragment") as PermissionsFragment?
        fragment?.requestPermissions(mActivity, permission)
    }

    constructor(supportFragmentManager: FragmentManager, permission: String) {
        // Add PermissionsFragment dynamically if it's not already added
        if (supportFragmentManager.findFragmentByTag("PermissionsFragment") == null) {
            supportFragmentManager.beginTransaction()
                .add(PermissionsFragment(), "PermissionsFragment")
                .commitNow()
        }

        // Request permissions using the fragment
        val fragment =
            supportFragmentManager.findFragmentByTag("PermissionsFragment") as PermissionsFragment?
        if (fragment != null) {
            fragment.requestPermissions(permission)
        }
    }


    fun checkBool(mActivity: Context, opt: String): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT

        when (opt) {
            "DOWNLOAD_ACCESS_", "DOWNLOAD_ACCESS" -> return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                ((ContextCompat.checkSelfPermission(
                    mActivity,
                    permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED))
            else
                ((ContextCompat.checkSelfPermission(
                    mActivity,
                    permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED))

            "NOTIFICATION" -> return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                    ((ContextCompat.checkSelfPermission(
                        mActivity,
                        permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED))

            "RECORD_AUDIO" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ((ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED))
            } else {
                return ((ContextCompat.checkSelfPermission(
                    mActivity,
                    permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED))
            }

            "LOCATION" -> return currentAPIVersion < Build.VERSION_CODES.M ||
                    ((ContextCompat.checkSelfPermission(
                        mActivity,
                        permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) &&
                            (ContextCompat.checkSelfPermission(
                                mActivity,
                                permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED))

            else -> return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                ((ContextCompat.checkSelfPermission(
                    mActivity,
                    permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.READ_MEDIA_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.READ_MEDIA_VIDEO
                        ) == PackageManager.PERMISSION_GRANTED))
            else
                ((ContextCompat.checkSelfPermission(
                    mActivity,
                    permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED) &&
                        (ContextCompat.checkSelfPermission(
                            mActivity,
                            permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED))
        }
    }
}
