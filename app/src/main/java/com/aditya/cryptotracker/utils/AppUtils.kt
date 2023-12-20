package com.aditya.cryptotracker.utils

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUtils @Inject constructor(@ApplicationContext private val context: Context) {

    fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }

    fun showToast(
        message: String, duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(context, message, duration).show()
    }

    fun roundTo6DecimalPlaces(value: String?): String {
        if (value == null) {
            return "0.0"
        }

        try {
            val bigDecimalValue = BigDecimal(value)
            val roundedValue = bigDecimalValue.setScale(6, RoundingMode.HALF_UP)
            var result = roundedValue.toPlainString()

            if (result.contains(".") && !result.contains("E")) {
                result = result.replace("0*$".toRegex(), "")
                result = result.replace("\\.$".toRegex(), "")
            }

            return result
        } catch (e: NumberFormatException) {
            return "0.0"
        }
    }
}
