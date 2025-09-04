package com.rp.uihelpher.helpher

object OnArabicNumber {
    fun isArabicNumber(input: String): Boolean {
        return input.matches("[٠-٩]+".toRegex()) // Arabic numerals only
    }

    fun convertArabicToInteger(input: String): Int {
        var input = input
        val arabicDigits = arrayOf<String?>("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")
        val westernDigits = arrayOf<String?>("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

        for (i in 0..9) {
            input = input.replace(arabicDigits[i]!!, westernDigits[i]!!)
        }

        return input.toInt() // Convert to integer
    }
}
