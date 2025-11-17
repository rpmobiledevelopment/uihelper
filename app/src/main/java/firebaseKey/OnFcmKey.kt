package firebaseKey

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.rp.uihelpher.helpher.GlobalData
import com.rp.uihelpher.localStorage.SharedPre
import com.rp.uihelpher.log.IsLog

class OnFcmKey : GlobalData {
    private val TAG: String = OnFcmKey::class.java.simpleName
    private var firebaseToken: String? = "Nextpeak"

    fun getKey(mActivity: Activity?): String? {
        val token = arrayOf<String?>("")

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener { task: Task<String?>? ->
                if (task?.isComplete == true) {
                    try {
                        token[0] = task.getResult()
                        val pref = mActivity?.applicationContext?.getSharedPreferences(
                            GlobalData.SHARED_PREF,
                            0
                        )
                        val editor = pref?.edit()
                        editor?.putString("regId ", token[0])
                        editor?.apply()

                        firebaseToken = try {
                            token[0]
                        } catch (e: NullPointerException) {
                            "Nextpeak"
                        } catch (e: Exception) {
                            "Nextpeak"
                        }

                        SharedPre.setDef(mActivity, GlobalData.TAG_FCM_KEY, firebaseToken)
                    } catch (e: SecurityException) {
                        IsLog(TAG, "SecurityException " + e.message)
                    } catch (e: NullPointerException) {
                        IsLog(TAG, "SecurityException " + e.message)
                    } catch (e: Exception) {
                        IsLog(TAG, "Exception============>>>> " + e.message)
                    }
                }
            }
        return token[0]
    }
}