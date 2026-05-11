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

    // Place holder
    constructor(ivLogo: ImageView?, images: String?, opt: String) {
        ivLogo?.let { imageView ->
            when (opt) {
                "USER_IMAGE" -> imageView.load(
                    if (!images.isNullOrEmpty() && images.length > 5)
                        images else R.drawable.ic_user) {
                    crossfade(true)
                    placeholder(R.drawable.ic_user)
                    error(R.drawable.ic_user)
                }

                "PLACE_HOLDER" -> imageView.load(
                    if (!images.isNullOrEmpty() && images.length > 5)
                        images else R.drawable.ph_small) {
                    crossfade(true)
                    placeholder(R.drawable.ph_small)
                    error(R.drawable.ph_small)
                }

                "PLACE_HOLDER_DB" -> imageView.load(
                    if (!images.isNullOrEmpty() && images.length > 5)
                        images else R.drawable.ph_small_) {
                    crossfade(true)
                    placeholder(R.drawable.ph_small_)
                    error(R.drawable.ph_small_)
                }

                "PLACE_HOLDER_ANNOUNC" -> imageView.load(
                    if (!images.isNullOrEmpty() && images.length > 5)
                        images else R.drawable.ic_announce) {
                    crossfade(true)
                    placeholder(R.drawable.ic_announce)
                    error(R.drawable.ic_announce)
                }
            }
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


