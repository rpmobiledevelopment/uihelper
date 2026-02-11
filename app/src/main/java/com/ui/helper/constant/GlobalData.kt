package com.ui.helper.constant

interface GlobalData {
    companion object {
        const val TAG_SELECT_PROJ_NAME: String = "TAG_SELECT_PROJ_NAME"
        const val TAG_SELECTED_LANGUAGE: String = "TAG_SELECTED_LANGUAGE"
        const val TAG_BASE_URL: String = "TAG_BASE_URL"
        const val TAG_BEAR_TOKEN: String = "TAG_BEAR_TOKEN"
        const val TAG_SELECT_LANGUAGE: String = "TAG_SELECT_LANGUAGE"
        const val TAG_VIDEO_PATH: String = "TAG_VIDEO_PATH"
        const val SHARED_PREF: String = "SHARED_PREF"
        const val TAG_FCM_KEY: String = "TAG_FCM_KEY"
        const val TAG_CURR_LAT: String = "TAG_CURR_LAT"
        const val TAG_CURR_LNG: String = "TAG_CURR_LNG"

        var TAG_API_PIN: Boolean = false
        var TAG_API_PIN_BASE_URL: String = "TAG_API_PIN_BASE_URL"
        var TAG_API_PIN_SHA_256: String = "TAG_API_PIN_SHA_256"
        var isDebugging: Boolean = false
        var isApiPopup: Boolean = false
        var isSelectedProject: String = ""
    }
}
