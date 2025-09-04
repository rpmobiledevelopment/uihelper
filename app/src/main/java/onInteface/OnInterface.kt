package onInteface

class OnInterface {
    interface OnShared {
        fun details(sltOpt: String?)
    }

    interface CallbackListener {
        fun onFetchProgress(response: String?, apiNameRef: String?)
        fun onFetchComplete(opt: String?, apiNameRef: String?)
    }
}
