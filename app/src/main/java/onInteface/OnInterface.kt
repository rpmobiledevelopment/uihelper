package onInteface

class OnInterface {
    interface OnShared {
        fun details(sltOpt: String?)
    }

    interface CallbackListener {
        fun onFetchProgress(responseCode: Int?,response: String?, apiNameRef: String?)
        fun onFetchComplete(opt: String?, apiNameRef: String?)
    }


    interface OnLoc {
        fun onViewLatLng(sltOpt: String?, lat: Double, lng: Double)
    }
}
