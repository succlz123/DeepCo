package org.succlz123.deepco.app.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_my
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.image.AsyncImageUrlMultiPlatform

@Composable
fun UserAvatarView(modifier: Modifier = Modifier, size: Dp, avatar: String?) {
    Box(
        modifier = modifier.size(size).clip(RoundedCornerShape(size)).then(
            if (avatar.isNullOrEmpty()) {
                Modifier.background(ColorResource.theme)
            } else {
                Modifier
            }
        ), contentAlignment = Alignment.Center
    ) {
        if (avatar.isNullOrEmpty()) {
            Image(
                modifier = Modifier.size(size / 2),
                contentDescription = null,
                colorFilter = ColorFilter.tint(ColorResource.white),
                painter = painterResource(resource = Res.drawable.ic_my),
            )
        } else {
            AsyncImageUrlMultiPlatform(modifier = Modifier.size(size), avatar)
        }
    }
}