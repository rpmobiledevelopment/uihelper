package com.rp.uihelpher.helpher

import java.text.DecimalFormat

class OnDecimal {
    fun onPoints(opt: Int): DecimalFormat {
        val dfTwoPoints: DecimalFormat = when (opt) {
            1 -> DecimalFormat("0.0")
            2 -> DecimalFormat("0.00")
            else -> DecimalFormat("0")
        }
        return dfTwoPoints
    }
}
