package org.succlz123.deepco.app.ui.user

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import deep_co.shared.generated.resources.Res
import deep_co.shared.generated.resources.ic_select
import org.jetbrains.compose.resources.painterResource
import org.succlz123.deepco.app.base.BaseDialogCardWithTitleNoneScroll
import org.succlz123.deepco.app.i18n.strings
import org.succlz123.deepco.app.theme.ColorResource
import org.succlz123.lib.click.noRippleClick
import org.succlz123.lib.screen.LocalScreenNavigator
import org.succlz123.lib.screen.viewmodel.globalViewModel

@Composable
fun ChatUserSelectDialog() {
    val screenNavigator = LocalScreenNavigator.current
    val vm = globalViewModel {
        MainUserViewModel()
    }
    BaseDialogCardWithTitleNoneScroll(strings().selectAChatUser) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(vm.chatUsers.value) { item ->
                Column(
                    modifier = Modifier.fillMaxWidth().noRippleClick {
                        vm.changeDefault(item)
                        screenNavigator.pop()
                    }.border(BorderStroke(1.dp, ColorResource.black5), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        UserAvatarView(Modifier.align(Alignment.Center), 82.dp, item.avatar)
                        if (item.isSelected) {
                            Image(
                                modifier = Modifier.size(24.dp).align(Alignment.TopEnd),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(ColorResource.green),
                                painter = painterResource(resource = Res.drawable.ic_select),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = item.name, modifier = Modifier, style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = item.description, modifier = Modifier, maxLines = 3, minLines = 3, style = MaterialTheme.typography.body2)
                }
            }
        }
    }
}
