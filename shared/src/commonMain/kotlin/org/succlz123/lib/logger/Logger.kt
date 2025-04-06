package org.succlz123.lib.logger

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object Logger {
    var DEBUG = true

    fun log(msg: String) {
        if (DEBUG) {
            val timestamp = System.currentTimeMillis()
            val localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formattedDateTime = localDateTime.format(formatter)
            println("$formattedDateTime $msg")
        }
    }
}