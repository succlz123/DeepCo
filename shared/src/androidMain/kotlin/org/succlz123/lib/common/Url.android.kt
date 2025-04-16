package org.succlz123.lib.common

import android.content.Intent
import androidx.core.net.toUri
import org.succlz123.lib.context.getApplicationContext

actual fun openURLByBrowser(url: String) {
    try {
        val context = getApplicationContext() ?: return
        val parsedUri = url.toUri()
        val intent = Intent(Intent.ACTION_VIEW, parsedUri)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent, null)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
