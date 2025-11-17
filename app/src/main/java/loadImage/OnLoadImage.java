package loadImage;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rp.uihelpher.R;
import com.rp.uihelpher.helpher.GlobalData;
import com.rp.uihelpher.localStorage.SharedPre;

public class OnLoadImage  implements GlobalData {

    private String TAG = OnLoadImage.class.getSimpleName();

    public OnLoadImage(Activity mActivity, ImageView iv_logo,String images) {
        if (images!=null && images.length()>5) {
            Glide.with(iv_logo).load(images).placeholder(R.drawable.ph_loading_small)
                    .error(R.drawable.ph_small).into(iv_logo);
        }else {
            Glide.with(iv_logo).load(R.drawable.ph_small)
                    .placeholder(R.drawable.ph_small).error(R.drawable.ph_small).into(iv_logo);
        }
    }

    // Place holder
    public OnLoadImage(Activity mActivity, ImageView iv_logo,String images,String opt) {
        switch (opt) {
            case "USER_IMAGE":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ic_user)
                        .placeholder(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_user).into(iv_logo);
                break;
            case "PLACE_HOLDER":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ph_small)
                        .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small).into(iv_logo);
                break;
            case "PLACE_HOLDER_DB":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ph_small_)
                        .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small_).into(iv_logo);
                break;
            case "PLACE_HOLDER_ANNOUNC":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ic_announce)
                        .placeholder(R.drawable.ic_announce).error(R.drawable.ic_announce).into(iv_logo);
                break;
            case "WITHOUT_PLACE_HOLDER":
                Glide.with(iv_logo).load(images).into(iv_logo);
                break;
        }

    }
    // Place holder
    public OnLoadImage(ImageView iv_logo,String images,String opt) {
        switch (opt) {
            case "USER_IMAGE":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ic_user)
                        .placeholder(R.drawable.ic_user).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_user).into(iv_logo);
                break;
            case "PLACE_HOLDER":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ph_small)
                        .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small).into(iv_logo);
                break;
            case "PLACE_HOLDER_DB":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ph_small_)
                        .placeholder(R.drawable.ph_loading_small).error(R.drawable.ph_small_).into(iv_logo);
                break;
            case "PLACE_HOLDER_ANNOUNC":
                Glide.with(iv_logo).load(images!=null && images.length()>5?images:R.drawable.ic_announce)
                        .placeholder(R.drawable.ic_announce).error(R.drawable.ic_announce).into(iv_logo);
                break;
        }

    }
    // Place holder
    public OnLoadImage(ImageView iv_logo,String images,int placeholderImage) {
        Glide.with(iv_logo).load(images!=null && images.length()>5?images:placeholderImage)
                .placeholder(placeholderImage).diskCacheStrategy(DiskCacheStrategy.ALL).error(placeholderImage).into(iv_logo);

    }
    // Place holder
    public OnLoadImage(ImageView iv_logo,int images,String opt) {
        Glide.with(iv_logo).load(images).into(iv_logo);
    }
    // Place holder
    public OnLoadImage(ImageView iv_logo,int images) {
        Glide.with(iv_logo).load(images).into(iv_logo);
    }

}


