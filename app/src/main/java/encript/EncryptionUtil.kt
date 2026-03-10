package encript

import android.util.Base64
import com.ui.helper.constant.GlobalData
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private val TAG: String = EncryptionUtil::class.java.simpleName

    // Encrypt the username / password
    @Throws(Exception::class)
    fun encrypt(data: String): String? {
        // Decode the Base64-encoded key and IV

        val decodedKey = Base64.decode(GlobalData.SECRET_KEY, Base64.DEFAULT)
        val decodedIV = Base64.decode(GlobalData.IV, Base64.DEFAULT)

        val secretKeySpec = SecretKeySpec(decodedKey, "AES")
        val ivSpec = IvParameterSpec(decodedIV)

        val cipher = Cipher.getInstance(GlobalData.ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)

        val encryptedData = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

    // Decrypt the username/password
    @Throws(Exception::class)
    fun decrypt(encryptedData: String?): String {
        // Decode the Base64-encoded key and IV
        val decodedKey = Base64.decode(GlobalData.SECRET_KEY, Base64.DEFAULT)
        val decodedIV = Base64.decode(GlobalData.IV, Base64.DEFAULT)

        val secretKeySpec = SecretKeySpec(decodedKey, "AES")
        val ivSpec = IvParameterSpec(decodedIV)

        val cipher = Cipher.getInstance(GlobalData.ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)

        val decodedEncryptedData = Base64.decode(encryptedData, Base64.DEFAULT)
        val decryptedData = cipher.doFinal(decodedEncryptedData)
        return kotlin.text.String(decryptedData!!)
    }
}