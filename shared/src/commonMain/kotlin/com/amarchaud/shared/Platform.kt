package com.amarchaud.shared

import androidx.compose.material3.ColorScheme
import kotlinx.coroutines.flow.StateFlow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

interface StoreDynamicColor {
    fun hasMaterialYou(): Boolean
    fun updateMaterialYouColor(b: Boolean)
    fun getDynamicColorScheme(darkTheme: Boolean): ColorScheme?

    val currentMaterialYouDisplay: StateFlow<Boolean>
}

interface JsonFileReader {
    fun loadJsonFile(fileName: String): String?
}

interface KmpLauncher {
    fun openEmail(email: String)
    fun openPhone(phone: String)
    fun openStoreUrl(store: String)
    fun openUrl(url: String)
}
