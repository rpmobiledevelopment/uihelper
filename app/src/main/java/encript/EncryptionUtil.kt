package encript

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {
    private val TAG: String = EncryptionUtil::class.java.getSimpleName()
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val SECRET_KEY = "7r7gQf25gDkDwPawFqA4EM+uU7AcVN77agrLRftErLA=" // 256-bit key (Base64-encoded)
    private const val IV = "wqgipUY5t+vqJnTjdNu0NQ==" // Initialization vector (Base64-encoded)

    // Encrypt the username / password
    @Throws(Exception::class)
    fun encrypt(data: String): String? {
        // Decode the Base64-encoded key and IV

        val decodedKey = Base64.decode(SECRET_KEY, Base64.DEFAULT)
        val decodedIV = Base64.decode(IV, Base64.DEFAULT)

        val secretKeySpec = SecretKeySpec(decodedKey, "AES")
        val ivSpec = IvParameterSpec(decodedIV)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)

        val encryptedData = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encryptedData, Base64.DEFAULT)
    }

    // Decrypt the username/password
    @Throws(Exception::class)
    fun decrypt(encryptedData: String?): String {
        // Decode the Base64-encoded key and IV
        val decodedKey = Base64.decode(SECRET_KEY, Base64.DEFAULT)
        val decodedIV = Base64.decode(IV, Base64.DEFAULT)

        val secretKeySpec = SecretKeySpec(decodedKey, "AES")
        val ivSpec = IvParameterSpec(decodedIV)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)

        val decodedEncryptedData = Base64.decode(encryptedData, Base64.DEFAULT)
        val decryptedData = cipher.doFinal(decodedEncryptedData)
        return kotlin.text.String(decryptedData!!)
    }
}