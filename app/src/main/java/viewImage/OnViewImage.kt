package viewImage

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.github.chrisbanes.photoview.PhotoView
import com.ui.helper.R
import loadImage.OnLoadImage

@RequiresApi(Build.VERSION_CODES.M)
class OnViewImage(mActivity: Activity?, listing: String?) {

    private val TAG: String = OnViewImage::class.java.simpleName
    private var eventGallery: AlertDialog? = null
    private var imgArray: ArrayList<String?>? = ArrayList()
    private var vp_img: ViewPager
    private var mActivity: Activity?
    private var alertDialogBuilder: AlertDialog.Builder? = null

    init {
        this.mActivity = mActivity
        imgArray = ArrayList<String?>()

        imgArray?.add(listing)

        alertDialogBuilder = mActivity?.let { AlertDialog.Builder(it) }
        val localizedContext = LocaleUtils.getLocalizedContext(mActivity)
        val themedContext = ContextThemeWrapper(localizedContext, mActivity?.theme)
        val inflater = LayoutInflater.from(themedContext)

        val view = inflater.inflate(R.layout.dlg_slideshow, null)

        vp_img = view.findViewById<ViewPager>(R.id.vp_img)

        if (imgArray != null) {
            vp_img.setAdapter(ImageSliderAdapter1(mActivity, imgArray, 1))
        }

        doShow(view)
    }

    private fun OnView(mActivity: Activity, imgArrayMultiple: ArrayList<String?>?, selectPos: Int) {
        this.mActivity = mActivity
        imgArray = ArrayList()

        imgArray = imgArrayMultiple

        alertDialogBuilder = mActivity.let { AlertDialog.Builder(it) }
        val localizedContext = LocaleUtils.getLocalizedContext(mActivity)
        val themedContext = ContextThemeWrapper(localizedContext, mActivity.theme)
        val inflater = LayoutInflater.from(themedContext)
        val view = inflater.inflate(R.layout.dlg_slideshow, null)

        vp_img = view.findViewById(R.id.vp_img)

        if (imgArray != null) {
            vp_img.setAdapter(ImageSliderAdapter1(mActivity, imgArray, 1))
        }

        doShow(view)
    }

    private fun doShow(view: View) {
        view.findViewById<View>(R.id.iv_close)
            .setOnClickListener { view1: View? -> eventGallery?.dismiss() }


        alertDialogBuilder?.setView(view)?.setCancelable(true)
        eventGallery = alertDialogBuilder?.create()

        eventGallery?.setOnShowListener {
            val window = eventGallery?.window
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
            window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        }
        eventGallery?.show()

        eventGallery?.window?.decorView?.setPadding(0, 0, 0, 0)
        eventGallery?.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
    }


    internal inner class ImageSliderAdapter1(var mContext: Context?, private val images: ArrayList<String?>?,
                                             var selectPos: Int) : PagerAdapter() {
        private val inflater: LayoutInflater = LayoutInflater.from(mContext)

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return images?.size ?: 0
        }

        override fun instantiateItem(view: ViewGroup, position: Int): Any {
            val myImageLayout = inflater.inflate(R.layout.item_slide, view, false)

            val photo_view = myImageLayout.findViewById<PhotoView?>(R.id.photo_view)

            OnLoadImage(photo_view, images?.get(position), "PLACE_HOLDER")

            view.addView(myImageLayout)
            return myImageLayout
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }
    }
}