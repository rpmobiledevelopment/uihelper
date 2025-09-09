package buttonLoading

import com.google.android.material.button.MaterialButton.ICON_GRAVITY_TEXT_START
import com.google.android.material.button.MaterialButton.IconGravity

class ProgressParams(
    var isEnabled: Boolean,
    var textResourceId: String?, // nullable string resource ID
    var showProgress: Boolean,
    eId: Int? = null,
    @IconGravity
    var iconGravity: Int = ICON_GRAVITY_TEXT_START
)