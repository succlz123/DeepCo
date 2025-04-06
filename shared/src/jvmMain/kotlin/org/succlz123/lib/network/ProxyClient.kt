package org.succlz123.lib.network

import java.net.InetSocketAddress

object ProxyClient {

    fun create(proxyHost: String, proxyPort: Int) {
        val proxy = java.net.Proxy(java.net.Proxy.Type.HTTP, InetSocketAddress(proxyHost, proxyPort))
        System.setProperty("http.proxyHost", proxyHost)
        System.setProperty("http.proxyPort", proxyPort.toString())
    }
}