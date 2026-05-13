package loadImage

import android.widget.ImageView
import com.bumptech.glide.Glide
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
        Glide.with(ivLogo).load(if (!images.isNullOrEmpty() && images.length>5)
            images else placeholder).placeholder(placeholder).error(placeholder).into(ivLogo)
    }

    // Place holder
    constructor(ivLogo: ImageView?, images: String?, placeholderImage: Int?) {

        ivLogo?.let { imageView ->
            placeholderImage?.let { placeholder ->
                Glide.with(imageView).load(if (!images.isNullOrEmpty() && images.length>5)
                    images else placeholder).placeholder(placeholder).error(placeholder).into(imageView)
            }
        }
    }

    // Place holder
    constructor(ivLogo: ImageView?, images: Int?, opt: String?) {
        ivLogo?.let { imageView ->
            Glide.with(imageView).load(images).into(imageView)
        }

    }

    // Place holder
    constructor(iv_logo: ImageView?, images: Int?) {
        iv_logo?.let { imageView ->
            Glide.with(imageView).load(images).into(imageView)
        }
    }
}


