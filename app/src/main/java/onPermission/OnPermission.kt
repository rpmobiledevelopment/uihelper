package onPermission

import android.Manifest.permission
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class OnPermission {
    private val TAG: String = OnPermission::class.java.simpleName

    constructor()

    constructor(
        multiplePermissionLauncher: ActivityResultLauncher<Array<String?>?>,
        opt: String
    ) {
        when (opt) {
            "NOTIFICATION" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                multiplePermissionLauncher.launch(arrayOf(permission.POST_NOTIFICATIONS))
            }

            "RECORD_AUDIO" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                multiplePermissionLauncher.launch(arrayOf(permission.RECORD_AUDIO))
            } else {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.RECORD_AUDIO,
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE
                    )
                )
            }

            "LOCATION" -> multiplePermissionLauncher.launch(
                arrayOf(
                    permission.ACCESS_FINE_LOCATION,
                    permission.ACCESS_COARSE_LOCATION
                )
            )

            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.READ_MEDIA_IMAGES,
                        permission.READ_MEDIA_AUDIO, permission.READ_MEDIA_VIDEO, permission.CAMERA
                    )
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE, permission.CAMERA
                    )
                )
            } else {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE, permission.CAMERA
                    )
                )
            }
        }
    }


    constructor(
        mActivity: Activity, multiplePermissionLauncher: ActivityResultLauncher<Array<String?>?>,
        opt: String
    ) {
        when (opt) {
            "DOWNLOAD_ACCESS" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                multiplePermissionLauncher.launch(arrayOf(permission.CAMERA))
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE, permission.CAMERA
                    )
                )
            } else {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE, permission.CAMERA
                    )
                )
            }

            "NOTIFICATION" -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                multiplePermissionLauncher.launch(arrayOf(permission.POST_NOTIFICATIONS))
            }

            "LOCATION" -> if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,
                    permission.ACCESS_FINE_LOCATION
                )
            ) {
                buildAlertMessageNoGps(mActivity)
            } else {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.ACCESS_FINE_LOCATION,
                        permission.ACCESS_BACKGROUND_LOCATION
                    )
                )
            }

            else -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.READ_MEDIA_IMAGES,
                        permission.READ_MEDIA_AUDIO, permission.READ_MEDIA_VIDEO, permission.CAMERA
                    )
                )
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE, permission.CAMERA
                    )
                )
            } else {
                multiplePermissionLauncher.launch(
                    arrayOf(
                        permission.WRITE_EXTERNAL_STORAGE,
                        permission.READ_EXTERNAL_STORAGE, permission.CAMERA
                    )
                )
            }
        }
    }


    fun checkBool(mActivity: Activity, opt: String): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT

        when (opt) {
            "DOWNLOAD_ACCESS" -> return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
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

    fun checkBool(mActivity: Context, opt: String): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT

        when (opt) {
            "DOWNLOAD_ACCESS" -> return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
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

    private fun buildAlertMessageNoGps(mActivity: Activity) {
        val builder = AlertDialog.Builder(mActivity)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog: DialogInterface?, id: Int ->
                    val intent = Intent()
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", mActivity.getPackageName(), null)
                    intent.setData(uri)
                    mActivity.startActivity(intent)
                }).setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog: DialogInterface?, id: Int -> dialog!!.cancel() })
        val alert = builder.create()
        alert.show()
    }
}
