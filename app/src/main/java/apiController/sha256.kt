package apiController

import android.util.Base64
import java.security.MessageDigest

fun sha256Hash(input: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hash = digest.digest(input.toByteArray(Charsets.UTF_8))
    return "sha256/${Base64.encodeToString(hash, Base64.NO_WRAP)}"
}