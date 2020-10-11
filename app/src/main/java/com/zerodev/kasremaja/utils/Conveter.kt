package com.zerodev.kasremaja.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    fun numberFormat(number: Int): String {
        return DecimalFormat("Rp. ###,###,###,###").format(number)
    }

    fun formatRupiah(price: Int): String? {
        val localeID = Locale("in", "ID")
        val format =
            NumberFormat.getCurrencyInstance(localeID)
        return format.format(price.toLong())
    }

    @Throws(ParseException::class)
    fun timeFormat(date: String): String {
        var format = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        var newDate: Date?

        newDate = format.parse(date)

        format = SimpleDateFormat("HH:mm", Locale.getDefault())

        return format.format(newDate)
    }

    fun decimalFormat(number : Int): String{
        val numberFormat = DecimalFormat("00")
        return numberFormat.format(number.toLong())
    }

}