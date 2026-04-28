package com.ui.helper.constant

interface GlobalData {
    companion object {
        var TAG_VIDEO_PATH: String = "TAG_VIDEO_PATH"
        var TAG_VIDEO_PATH_NAME: String = "TAG_VIDEO_PATH_NAME"
        const val SHARED_PREF: String = "SHARED_PREF"
        const val TAG_FCM_KEY: String = "TAG_FCM_KEY"
        const val TAG_CURR_LAT: String = "TAG_CURR_LAT"
        const val TAG_CURR_LNG: String = "TAG_CURR_LNG"

        var TAG_BASE_URL: String = "TAG_BASE_URL"
        var TAG_BEAR_TOKEN: String = "TAG_BEAR_TOKEN"

        var TAG_API_PIN: Boolean = false
        var TAG_API_PIN_BASE_URL: String = "TAG_API_PIN_BASE_URL"
        var TAG_API_PIN_SHA_256: String = "TAG_API_PIN_SHA_256"
        var isDebugging: Boolean = false
        var isApiPopup: Boolean = false
        var isSelectedProject: String = ""
        var isSelectedLanguage: String = "EN"



        var ALGORITHM = "AES/CBC/PKCS5Padding"
        var SECRET_KEY = "7r7gQf25gDkDwPawFqA4EM+uU7AcVN77agrLRftErLA=" // 256-bit key (Base64-encoded)
        var IV = "wqgipUY5t+vqJnTjdNu0NQ==" // Initialization vector (Base64-encoded)
    }
}
