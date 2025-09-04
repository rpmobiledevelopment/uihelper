package com.rp.uihelpher.onShare

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.text.Html
import android.widget.Toast
import androidx.core.content.FileProvider
import com.rp.uihelpher.R
import com.rp.uihelpher.log.IsLog
import onInteface.OnInterface
import java.io.File

class OnShare {

    var TAG: String = OnShare::class.java.simpleName

    constructor(mActivity: Activity, bitmap: Bitmap?, onShare: OnInterface.OnShared) {
        val saveFile = ScreenshotUtils.getMainDirectoryName(mActivity)
        val file = ScreenshotUtils.store(bitmap, "screenshot.jpg", saveFile)

        doShareOption(mActivity, file, "", "")

        onShare.details("ON_SHARED")
    }

    fun doShareOption(mActivity: Activity, file: File, shareOption: String, shareTitle: String?) {
        val outputFileUri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputFileUri = FileProvider.getUriForFile(
                mActivity,
                mActivity.applicationContext.packageName + ".app_file_provider", file
            )
        } else {
            outputFileUri = Uri.fromFile(file)
        }
        if (shareOption == "N") {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "image/*"
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                mActivity.getResources().getString(R.string.app_name)
            )
            shareIntent.putExtra(Intent.EXTRA_STREAM, outputFileUri) // Pass URI here

            val packageManager = mActivity.packageManager
            val emailApps = packageManager.queryIntentActivities(shareIntent, 0)

            val emailIntents: MutableList<Intent?> = ArrayList<Intent?>()
            for (resolveInfo in emailApps) {
                val packageName = resolveInfo.activityInfo.packageName
                if (packageName.contains("com.google.android.gm") ||
                    packageName.contains("com.microsoft") || packageName.contains("mail")
                ) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "image/*"
                    intent.putExtra(
                        Intent.EXTRA_SUBJECT,
                        mActivity.resources.getString(R.string.app_name)
                    )
                    intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
                    intent.setPackage(packageName)
                    emailIntents.add(intent)
                }
            }
            val chooserIntent = Intent.createChooser(emailIntents.removeAt(0), shareTitle)
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                emailIntents.toTypedArray<Parcelable?>()
            )
            mActivity.startActivity(chooserIntent)
        } else {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "image/*"
            intent.putExtra(
                Intent.EXTRA_SUBJECT,
                mActivity.getResources().getString(R.string.app_name)
            )
            intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            mActivity.startActivity(Intent.createChooser(intent, shareTitle))
        }
    }

    fun doShareOptionContent(
        mActivity: Activity,
        shareContent: String?,
        shareAppContent: String?,
        shareOption: String,
        shareTitle: String?
    ) {
        if (shareOption == "N") {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "image/*"
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                mActivity.getResources().getString(R.string.app_name)
            )
            val packageManager = mActivity.packageManager
            val emailApps = packageManager.queryIntentActivities(shareIntent, 0)

            val emailIntents: MutableList<Intent?> = ArrayList<Intent?>()
            for (resolveInfo in emailApps) {
                val packageName = resolveInfo.activityInfo.packageName
                if (packageName.contains("com.google.android.gm") || packageName.contains("com.microsoft") || packageName.contains(
                        "mail"
                    )
                ) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, shareContent)
                    intent.putExtra(
                        Intent.EXTRA_SUBJECT,
                        mActivity.getResources().getString(R.string.app_name)
                    )
                    intent.setPackage(packageName)
                    emailIntents.add(intent)
                }
            }
            val chooserIntent = Intent.createChooser(emailIntents.removeAt(0), shareTitle)
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                emailIntents.toTypedArray<Parcelable?>()
            )
            mActivity.startActivity(chooserIntent)
        } else {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                mActivity.getResources().getString(R.string.app_name)
            )
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent)
            mActivity.startActivity(Intent.createChooser(shareIntent, shareAppContent))
        }
    }

    // Social Media Image Share
    constructor(
        mActivity: Activity,
        shareBitmap: Bitmap?,
        packageName: String,
        socialMedia: String?,
        plainText: String?
    ) {
        val saveFile =
            ScreenshotUtils.getMainDirectoryName(mActivity) // get the path to save screenshot
        val file = ScreenshotUtils.store(
            shareBitmap,
            "screenshot.jpg",
            saveFile
        ) // save the screenshot to selected path

        var description: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description = Html.fromHtml(plainText, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            description = ""
        }
        IsLog(TAG, plainText) // Output: Hello World

        val outputFileUri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputFileUri = FileProvider.getUriForFile(
                mActivity,
                mActivity.applicationContext.packageName + ".provider", file
            )
        } else {
            outputFileUri = Uri.fromFile(file)
        }


        val uriList = ArrayList<Uri?>()
        uriList.add(outputFileUri)

        val intent = Intent()

        when (packageName) {
            "com.linkedin.android" -> {
                intent.action = Intent.ACTION_SEND_MULTIPLE
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
                intent.putExtra(Intent.EXTRA_TEXT, description)

                val clipboard =
                    mActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("share_text", description)
                clipboard.setPrimaryClip(clip)
            }

            "com.instagram.android" -> {
                intent.type = "image/*"
                intent.action = "com.instagram.share.ADD_TO_FEED"
                intent.setDataAndType(outputFileUri, "image/*")
                intent.putExtra(Intent.EXTRA_TEXT, description)
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            }

            "com.twitter.android" -> {
                intent.type = "image/*"
                intent.action = Intent.ACTION_SEND_MULTIPLE
                intent.putExtra(Intent.EXTRA_TEXT, description)
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
            }

            "com.zhiliaoapp.musically" -> {
                intent.type = "image/*"
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, description)
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            }

            else -> {
                intent.type = "image/*"
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            }
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setPackage(packageName)

        mActivity.grantUriPermission(
            packageName,
            outputFileUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        mActivity.startActivity(intent)
    }

    // Social Media Video Share
    constructor(
        mActivity: Activity,
        shareBitmap: File,
        packageName: String,
        socialMedia: String?,
        plainText: String?
    ) {
        val outputFileUri: Uri?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputFileUri = FileProvider.getUriForFile(
                mActivity,
                mActivity.packageName + ".provider", shareBitmap
            )
        } else {
            outputFileUri = Uri.fromFile(shareBitmap)
        }

        var description: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description = Html.fromHtml(plainText, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            description = ""
        }
        IsLog(TAG, plainText) // Output: Hello World

        val uriList = ArrayList<Uri?>()
        uriList.add(outputFileUri)

        val intent = Intent()

        when (packageName) {
            "com.linkedin.android" -> {
                intent.action = Intent.ACTION_SEND
                intent.type = "video/*"
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
                intent.putExtra(Intent.EXTRA_TEXT, description)

                val clipboard =
                    mActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("share_text", description)
                clipboard.setPrimaryClip(clip)
            }

            "com.instagram.android" -> {
                intent.type = "video/*"
                intent.action = Intent.ACTION_SEND
                //                intent.setAction("com.instagram.share.ADD_TO_FEED");
                intent.setDataAndType(outputFileUri, "video/*")
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            }

            "com.twitter.android" -> {
                intent.type = "video/*"
                intent.action = Intent.ACTION_SEND
                intent.setDataAndType(outputFileUri, "video/*")
                intent.putExtra(Intent.EXTRA_TEXT, description)
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            }

            "com.zhiliaoapp.musically" -> {
                intent.action = Intent.ACTION_SEND
                intent.type = "video/*"
                intent.putExtra(Intent.EXTRA_TEXT, description)
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            }

            else -> {
                intent.type = "video/*"
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, description)
                intent.putExtra(Intent.EXTRA_STREAM, outputFileUri)
            }
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setPackage(packageName)

        mActivity.grantUriPermission(
            packageName,
            outputFileUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        mActivity.startActivity(intent)
    }

    constructor(mActivity: Activity, packageName1: String?) {
        val targetedIntents: MutableList<Intent?> = ArrayList<Intent?>()

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Check out my app: https://play.google.com/store/apps/details?id=" + mActivity.getPackageName()
        )

        val pm = mActivity.getPackageManager()
        val apps = pm.queryIntentActivities(shareIntent, 0)

        for (info in apps) {
            val packageName = info.activityInfo.packageName

            if (packageName == "com.instagram.android" ||
                packageName == "com.linkedin.android" ||
                packageName == "com.twitter.android"
            ) {
                val targeted = Intent(Intent.ACTION_SEND)
                targeted.setType("text/plain")
                targeted.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out my app: https://play.google.com/store/apps/details?id=" + mActivity.getPackageName()
                )
                targeted.setPackage(packageName)
                targetedIntents.add(targeted)
            }
        }

        if (!targetedIntents.isEmpty()) {
            val chooserIntent = Intent.createChooser(targetedIntents.removeAt(0), "Share via")
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                targetedIntents.toTypedArray<Parcelable?>()
            )
            mActivity.startActivity(chooserIntent)
        } else {
            Toast.makeText(mActivity, "No supported apps installed", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun onReturnFile(mActivity: Activity, bitmap: Bitmap?): Uri? {
            val saveFile =
                ScreenshotUtils.getMainDirectoryName(mActivity) // get the path to save screenshot
            val file = ScreenshotUtils.store(
                bitmap,
                "screenshot.jpg",
                saveFile
            ) // save the screenshot to selected path

            val outputFileUri: Uri?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                outputFileUri = FileProvider.getUriForFile(
                    mActivity,
                    mActivity.getApplicationContext().getPackageName() + ".app_file_provider", file
                )
            } else {
                outputFileUri = Uri.fromFile(file)
            }

            return outputFileUri
        }

        private fun getIntents(
            mActivity: Activity,
            apps: MutableList<ResolveInfo>
        ): MutableList<Intent?> {
            val targetedIntents: MutableList<Intent?> = ArrayList<Intent?>()

            for (info in apps) {
                val packageName = info.activityInfo.packageName

                if (packageName.contains("com.instagram.android") ||
                    packageName.contains("com.linkedin.android") ||
                    packageName.contains("com.twitter.android")
                ) {
                    val targeted = Intent(Intent.ACTION_SEND)
                    targeted.type = "text/plain"
                    targeted.putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out my app: https://play.google.com/store/apps/details?id=" + mActivity.getPackageName()
                    )
                    targeted.setPackage(packageName)
                    targetedIntents.add(targeted)
                }
            }
            return targetedIntents
        }
    }
}
