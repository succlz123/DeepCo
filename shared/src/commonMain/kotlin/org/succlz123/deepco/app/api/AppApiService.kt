package org.succlz123.deepco.app.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.sse.SSE
import io.ktor.serialization.kotlinx.json.json
import org.succlz123.deepco.app.AppBuildConfig
import org.succlz123.deepco.app.json.appJson
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object AppApiService {

    val httpClient = HttpClient(OkHttp) {
        defaultRequest {
        }
        engine {
            this.config {
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, arrayOf(trustAllManager), null)
                sslSocketFactory(sslContext.socketFactory, trustAllManager)
            }
        }
        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    if (AppBuildConfig.isDebug) {
                        println("HTTP Client: message: $message")
                    }
                }
            }
        }
        install(SSE) {
            showCommentEvents()
            showRetryEvents()
        }
        install(ContentNegotiation) {
            json(appJson)
        }
    }

    private fun getSLLContext(): SSLContext? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, arrayOf(trustAllManager), SecureRandom())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sslContext
    }

    private val trustAllManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }
}