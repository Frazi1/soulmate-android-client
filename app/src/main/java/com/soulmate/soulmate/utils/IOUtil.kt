package com.soulmate.soulmate.utils

import java.io.ByteArrayOutputStream
import java.io.InputStream

fun InputStream.toByteArray(): ByteArray? {

    val buffer = ByteArrayOutputStream()


    val data = ByteArray(16384)
    var bytesRead = 0
    while (bytesRead != -1) {
        bytesRead = read(data, 0, data.size)
        buffer.write(data, 0, bytesRead)
    }

    buffer.flush()

    return buffer.toByteArray()
}