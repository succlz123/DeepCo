package org.succlz123.lib.time

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.hhMMssSSS(withN: Boolean = false): String {
    if (this <= 0) {
        return ""
    }
    val date = Date(this)
    val formatter = SimpleDateFormat(
        "yyyy-MM-dd${
            if (withN) {
                "\n"
            } else {
                " "
            }
        }HH:mm:ss", Locale.getDefault()
    )
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(date)
}
