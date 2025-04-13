package org.succlz123.deepco.app.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material.icons.sharp.Build
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.LocationOn
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material.icons.sharp.Star
import kotlinx.coroutines.flow.MutableStateFlow
import org.succlz123.lib.vm.BaseViewModel

class MainViewModel : BaseViewModel() {

    companion object {
        val MAIN_TITLE = arrayListOf("Chat", "LLM", "Prompt", "User", "MCP", "Setting")

        val MAIN_ICON =
            arrayListOf(
                Icons.Sharp.Home,
                Icons.Sharp.Star,
                Icons.Sharp.Add,
                Icons.Sharp.Person,
                Icons.Sharp.Build,
                Icons.Sharp.Settings
            )
    }

    val leftSelectItem = MutableStateFlow(0)
}
