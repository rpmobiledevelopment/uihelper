package loadImage

import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import coil3.request.error
import com.ui.helper.R
import com.ui.helper.constant.GlobalData

class OnLoadImage : GlobalData {

    private val TAG: String = OnLoadImage::class.java.simpleName

    constructor(ivLogo: ImageView?, images: String?, opt: String) {

        if (ivLogo == null) return

        val placeholder = when (opt) {
            "USER_IMAGE" -> R.drawable.ic_user
            "PLACE_HOLDER" -> R.drawable.ph_small
            "PLACE_HOLDER_DB" -> R.drawable.ph_small_
            "PLACE_HOLDER_ANNOUNC" -> R.drawable.ic_announce
            else -> R.drawable.ph_small
        }

        ivLogo.load(
            if (!images.isNullOrEmpty() && images.startsWith("http"))
                images else placeholder) {
            crossfade(true)
            placeholder(placeholder)
            error(placeholder)
        }
    }

    // Place holder
    constructor(ivLogo: ImageView?, images: String?, placeholderImage: Int?) {

        ivLogo?.let { imageView ->

            placeholderImage?.let { placeholder ->

                imageView.load(
                    if (!images.isNullOrEmpty() && images.length > 5)
                        images else placeholder) {
                    crossfade(true)
                    placeholder(placeholder)
                    error(placeholder)
                }
            }
        }
    }

    // Place holder
    constructor(ivLogo: ImageView?, images: Int?, opt: String?) {
        ivLogo?.load(images) {
            crossfade(true)
        }
    }

    // Place holder
    constructor(iv_logo: ImageView?, images: Int?) {
        iv_logo?.load(images) {
            crossfade(true)
        }
    }
}


