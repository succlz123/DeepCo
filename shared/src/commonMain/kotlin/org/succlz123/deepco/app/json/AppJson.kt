package org.succlz123.deepco.app.json

import kotlinx.serialization.json.Json

val appJson = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
    encodeDefaults = true
}
