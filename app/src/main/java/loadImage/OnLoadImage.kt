package loadImage

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ui.helper.R
import com.ui.helper.constant.GlobalData

class OnLoadImage : GlobalData {

    private val TAG: String = OnLoadImage::class.java.simpleName

    constructor(mActivity: Activity?, iv_logo: ImageView?, images: String?) {
        iv_logo?.let {
            if (images != null && images.length > 5) {
                Glide.with(it).load(images).placeholder(R.drawable.ph_loading_small)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .error(R.drawable.ph_small).into(it)
            } else {
                Glide.with(it).load(R.drawable.ph_small)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ph_small).error(R.drawable.ph_small).into(it)
            }
        }

    }

    // Place holder
    constructor(mActivity: Activity?, iv_logo: ImageView?, images: String?, opt: String) {
        iv_logo?.let { iv_logo ->
            when (opt) {
                "USER_IMAGE" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ic_user)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_user).into(iv_logo)

                "PLACE_HOLDER" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ph_small)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small).into(iv_logo)

                "PLACE_HOLDER_DB" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ph_small_)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small_).into(iv_logo)

                "PLACE_HOLDER_ANNOUNC" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ic_announce)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ic_announce).error(R.drawable.ic_announce).into(iv_logo)

                "WITHOUT_PLACE_HOLDER" -> Glide.with(iv_logo).load(images).into(iv_logo)
            }
        }

    }

    // Place holder
    constructor(iv_logo: ImageView?, images: String?, opt: String) {
        iv_logo?.let { iv_logo ->
            when (opt) {
                "USER_IMAGE" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ic_user)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_user).into(iv_logo)

                "PLACE_HOLDER" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ph_small)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small).into(iv_logo)

                "PLACE_HOLDER_DB" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ph_small_)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small_).into(iv_logo)

                "PLACE_HOLDER_ANNOUNC" -> Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else R.drawable.ic_announce)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(R.drawable.ic_announce).error(R.drawable.ic_announce).into(iv_logo)
            }
        }
    }

    // Place holder
    constructor(iv_logo: ImageView?, images: String?, placeholderImage: Int?) {
        iv_logo?.let { iv_logo->
            if (placeholderImage!=null)
                Glide.with(iv_logo)
                    .load(if (images != null && images.length > 5) images else placeholderImage)
                    .thumbnail(0.1f)   // 👈 FAST PREVIEW
                    .placeholder(placeholderImage).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(placeholderImage).into(iv_logo)
        }
    }

    // Place holder
    constructor(iv_logo: ImageView?, images: Int?, opt: String?) {
        iv_logo?.let { iv_logo ->
            Glide.with(iv_logo).load(images).into(iv_logo)
        }
    }

    // Place holder
    constructor(iv_logo: ImageView?, images: Int?) {
        iv_logo?.let { iv_logo ->
            Glide.with(iv_logo).load(images).into(iv_logo)
        }
    }
}


