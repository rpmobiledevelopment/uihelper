package video

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.ui.helper.R
import com.ui.helper.constant.GlobalData
import com.ui.helper.localStorage.SharedPre
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

class VideoPlayerActivity : AppCompatActivity(), View.OnClickListener, GlobalData {

    private val TAG: String = VideoPlayerActivity::class.java.simpleName

    var playerView: PlayerView? = null
    var player: ExoPlayer? = null
    var title: TextView? = null
    private var isLock = false
    var videoBack: ImageView? = null
    var lock: ImageView? = null
    var unlock: ImageView? = null
    var scaling: ImageView? = null
    var root: RelativeLayout? = null
    var nightMode: View? = null
    var isCrossChecked: Boolean = false
    var eqContainer: FrameLayout? = null

    // swipe and zoom variables
    private var device_height = 0
    private var device_width = 0
    private var brightness = 0
    private var media_volume: Int? = 0
    var start: Boolean = false
    var left: Boolean = false
    var right: Boolean = false
    private var baseX = 0f
    private var baseY = 0f
    var swipe_move: Boolean = false
    private var diffX: Long = 0
    private var diffY: Long = 0
    var success: Boolean = false
    var vol_text: TextView? = null
    var brt_text: TextView? = null
    var total_duration: TextView? = null
    var vol_progress: ProgressBar? = null
    var brt_progress: ProgressBar? = null
    var vol_progress_container: LinearLayout? = null
    var vol_text_container: LinearLayout? = null
    var brt_progress_container: LinearLayout? = null
    var brt_text_container: LinearLayout? = null
    var vol_icon: ImageView? = null
    var brt_icon: ImageView? = null
    var audioManager: AudioManager? = null
    private var contentResolver: ContentResolver? = null
    private var window: Window? = null
    var singleTap: Boolean = false

    var zoomLayout: RelativeLayout? = null
    var zoomContainer: RelativeLayout? = null
    var zoom_perc: TextView? = null
    var scaleGestureDetector: ScaleGestureDetector? = null
    private var scale_factor = 1.0f
    var double_tap: Boolean = false
    var double_tap_playpause: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContentView(R.layout.act_video_player)

        playerView = findViewById(R.id.exoplayer_view)

        initViews()
        playVideo()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        device_width = displayMetrics.widthPixels
        device_height = displayMetrics.heightPixels

        playerView?.setOnTouchListener(object : OnSwipeTouchListener(this) {
            override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
                if (!isLock) {
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            playerView?.showController()
                            start = true
                            if (motionEvent.x < (device_width / 2)) {
                                left = true
                                right = false
                            } else if (motionEvent.x > (device_width / 2)) {
                                left = false
                                right = true
                            }
                            baseX = motionEvent.x
                            baseY = motionEvent.y
                        }
                        MotionEvent.ACTION_MOVE -> {
                            swipe_move = true
                            diffX = ceil((motionEvent.x - baseX).toDouble()).toLong()
                            diffY = ceil((motionEvent.y - baseY).toDouble()).toLong()
                            val brightnessSpeed = 0.01
                            if (abs(diffY) > MINIMUM_DISTANCE) {
                                start = true
                                if (abs(diffY) > abs(diffX)) {
                                    val value: Boolean
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        value = Settings.System.canWrite(applicationContext)
                                        if (value) {
                                            if (left) {
                                                contentResolver = getContentResolver()
                                                window = getWindow()
                                                try {
                                                    Settings.System.putInt(
                                                        contentResolver,
                                                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                                                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                                                    )
                                                    brightness = Settings.System.getInt(
                                                        contentResolver,
                                                        Settings.System.SCREEN_BRIGHTNESS
                                                    )
                                                } catch (e: SettingNotFoundException) {
                                                    e.printStackTrace()
                                                }
                                                var new_brightness =
                                                    (brightness - (diffY * brightnessSpeed)).toInt()
                                                if (new_brightness > 250) {
                                                    new_brightness = 250
                                                } else if (new_brightness < 1) {
                                                    new_brightness = 1
                                                }
                                                val brt_percentage =
                                                    ceil(((new_brightness.toDouble() / 250.0) * 100.0))
                                                brt_progress_container?.visibility = View.VISIBLE
                                                brt_text_container?.visibility = View.VISIBLE
                                                brt_progress?.progress = brt_percentage.toInt()

                                                if (brt_percentage < 30) {
                                                    brt_icon?.setImageResource(R.drawable.ic_brightness_low)
                                                } else if (brt_percentage > 30 && brt_percentage < 80) {
                                                    brt_icon?.setImageResource(R.drawable.ic_brightness_moderate)
                                                } else if (brt_percentage > 80) {
                                                    brt_icon?.setImageResource(R.drawable.ic_brightness)
                                                }

                                                brt_text?.text = " " + brt_percentage.toInt() + "%"
                                                Settings.System.putInt(
                                                    contentResolver,
                                                    Settings.System.SCREEN_BRIGHTNESS,
                                                    (new_brightness)
                                                )
                                                val layoutParams = window?.attributes
                                                layoutParams?.screenBrightness = brightness / 255f
                                                window?.attributes = layoutParams
                                            } else if (right) {
                                                vol_text_container?.visibility = View.VISIBLE
                                                media_volume = audioManager?.getStreamVolume(AudioManager.STREAM_MUSIC)
                                                val maxVol =
                                                    audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                                                val cal =
                                                    diffY.toDouble() * ((maxVol ?: 0).toDouble() / ((device_height * 2).toDouble() - brightnessSpeed))
                                                var newMediaVolume = (media_volume?:0) - cal.toInt()
                                                if (newMediaVolume > (maxVol ?: 0)) {
                                                    newMediaVolume = (maxVol ?: 0)
                                                } else if (newMediaVolume < 1) {
                                                    newMediaVolume = 0
                                                }
                                                audioManager?.setStreamVolume(
                                                    AudioManager.STREAM_MUSIC,
                                                    newMediaVolume,
                                                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE
                                                )
                                                val volPer =
                                                    ceil(((newMediaVolume.toDouble() / (maxVol ?: 0).toDouble()) * 100.0))
                                                vol_text?.text = " " + volPer.toInt() + "%"
                                                if (volPer < 1) {
                                                    vol_icon?.setImageResource(R.drawable.ic_volume_off)
                                                    vol_text?.visibility = View.VISIBLE
                                                    vol_text?.text = "Off"
                                                } else if (volPer >= 1) {
                                                    vol_icon?.setImageResource(R.drawable.ic_volume)
                                                    vol_text?.visibility = View.VISIBLE
                                                }
                                                vol_progress_container?.visibility = View.VISIBLE
                                                vol_progress?.progress = volPer.toInt()
                                            }
                                            success = true
                                        } else {
                                            Toast.makeText(
                                                applicationContext,
                                                "Allow write settings for swipe controls",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            val intent =
                                                Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                                            intent.data = ("package:$packageName").toUri()
                                            startActivityForResult(intent, 111)
                                        }
                                    }
                                }
                            }
                        }

                        MotionEvent.ACTION_UP -> {
                            swipe_move = false
                            start = false
                            vol_progress_container?.visibility = View.GONE
                            brt_progress_container?.visibility = View.GONE
                            vol_text_container?.visibility = View.GONE
                            brt_text_container?.visibility = View.GONE
                        }
                    }
                    scaleGestureDetector?.onTouchEvent(motionEvent)
                }
                return super.onTouch(view, motionEvent)
            }

            public override fun onDoubleTouch() {
                super.onDoubleTouch()
                if (double_tap) {
                    player?.playWhenReady = true
                    double_tap_playpause?.visibility = View.GONE
                    double_tap = false
                } else {
                    player?.playWhenReady = false
                    double_tap_playpause?.visibility = View.VISIBLE
                    double_tap = true
                }
            }

            public override fun onSingleTouch() {
                super.onSingleTouch()
                if (singleTap) {
                    playerView?.showController()
                    singleTap = false
                } else {
                    playerView?.hideController()
                    singleTap = true
                }
                if (double_tap_playpause?.isVisible == true) {
                    double_tap_playpause?.visibility = View.GONE
                }
            }
        })
    }

    private fun initViews() {
        total_duration = findViewById(R.id.exo_duration)
        title = findViewById(R.id.video_title)
        videoBack = findViewById(R.id.video_back)
        lock = findViewById(R.id.lock)
        unlock = findViewById(R.id.unlock)
        scaling = findViewById(R.id.scaling)
        root = findViewById(R.id.root_layout)
        nightMode = findViewById(R.id.night_mode)
        eqContainer = findViewById(R.id.eqFrame)
        vol_text = findViewById(R.id.vol_text)
        brt_text = findViewById(R.id.brt_text)
        vol_progress = findViewById(R.id.vol_progress)
        brt_progress = findViewById(R.id.brt_progress)
        vol_progress_container = findViewById(R.id.vol_progress_container)
        brt_progress_container = findViewById(R.id.brt_progress_container)
        vol_text_container = findViewById(R.id.vol_text_container)
        brt_text_container = findViewById(R.id.brt_text_container)
        vol_icon = findViewById(R.id.vol_icon)
        brt_icon = findViewById(R.id.brt_icon)
        zoomLayout = findViewById(R.id.zoom_layout)
        zoom_perc = findViewById(R.id.zoom_percentage)
        zoomContainer = findViewById(R.id.zoom_container)
        double_tap_playpause = findViewById(R.id.double_tap_play_pause)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleDetector())

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager

        if (SharedPre.getDef(this, GlobalData.TAG_VIDEO_PATH) != "") {
            title?.text = SharedPre.getDef(this, GlobalData.TAG_VIDEO_PATH)
        } else {
            title?.text = "Video Player"
        }

        if (SharedPre.getDef(this, GlobalData.TAG_SELECTED_LANGUAGE) == "AR") {
            videoBack?.rotation = 180f
        }

        videoBack?.setOnClickListener(this)
        lock?.setOnClickListener(this)
        unlock?.setOnClickListener(this)
        scaling?.setOnClickListener(this)
    }

    private fun playVideo() {
        player = ExoPlayer.Builder(this).build()
        playerView?.player = player
        playerView?.keepScreenOn = true

        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("app")
            .setAllowCrossProtocolRedirects(true)

        val dataSourceFactory = DefaultDataSource.Factory(this, httpDataSourceFactory)

        val videoUri = SharedPre.getDef(this, GlobalData.TAG_VIDEO_PATH).toUri()

        val mediaItem = MediaItem.fromUri(videoUri)

        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)

        player?.setMediaSource(mediaSource)
        player?.prepare()
        player?.playWhenReady = true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.eqFrame)
        if (eqContainer?.isGone == true) {
            super.onBackPressed()
        } else {
            if (fragment?.isVisible == true && eqContainer?.isVisible == true) {
                eqContainer?.visibility = View.GONE
            } else {
                player?.release()
                super.onBackPressed()
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onPause() {
        super.onPause()
        player?.playWhenReady = false
        player?.playbackState
        if (isInPictureInPictureMode) {
            player?.playWhenReady = true
        } else {
            player?.playWhenReady = false
            player?.playbackState
        }
    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
        player?.playbackState
    }

    override fun onRestart() {
        super.onRestart()
        player?.playWhenReady = true
        player?.playbackState
    }

    private fun setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun hideBottomBar(portLand: String?) {
        if (portLand == "HIDE") {
            val decodeView = window?.decorView
            val uiOptions =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decodeView?.systemUiVisibility = uiOptions
        } else {
            val decorView = window?.decorView
            decorView?.setSystemUiVisibility(
                (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            )
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.video_back) {
            player?.release()
            finish()
        } else if (id == R.id.lock) {
            isLock = false
            root?.visibility = View.VISIBLE
            lock?.visibility = View.INVISIBLE
        } else if (id == R.id.unlock) {
            isLock = true
            root?.visibility = View.INVISIBLE
            lock?.visibility = View.VISIBLE
        } else if (id == R.id.scaling) {
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                hideBottomBar("HIDE")
            } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                hideBottomBar("SHOW")
            }
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        isCrossChecked = isInPictureInPictureMode
        if (isInPictureInPictureMode) {
            playerView?.hideController()
        } else {
            playerView?.showController()
        }
    }

    override fun onStop() {
        super.onStop()
        if (isCrossChecked) {
            player?.release()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val value = Settings.System.canWrite(applicationContext)
                if (value) {
                    success = true
                } else {
                    Toast.makeText(applicationContext, "Not Granted", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private inner class ScaleDetector : SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scale_factor *= detector.scaleFactor
            scale_factor = max(0.5f, min(scale_factor, 6.0f))

            zoomLayout?.scaleX = scale_factor
            zoomLayout?.scaleY = scale_factor
            val percentage = (scale_factor * 100).toInt()
            zoom_perc?.text = " $percentage%"
            zoomContainer?.visibility = View.VISIBLE

            brt_text_container?.visibility = View.GONE
            vol_text_container?.visibility = View.GONE
            brt_progress_container?.visibility = View.GONE
            vol_progress_container?.visibility = View.GONE

            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            zoomContainer?.visibility = View.GONE
            super.onScaleEnd(detector)
        }
    }

    companion object {
        const val MINIMUM_DISTANCE: Int = 100
    }
}
