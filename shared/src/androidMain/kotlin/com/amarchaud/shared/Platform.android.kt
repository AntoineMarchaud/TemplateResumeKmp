package com.amarchaud.shared

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

class AndroidStoreDynamicColor(private val context: Context) : StoreDynamicColor {
    private val _dynamicColor = MutableStateFlow(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    override fun hasMaterialYou() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    override fun updateMaterialYouColor(b: Boolean) {
        _dynamicColor.value = b
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun getDynamicColorScheme(darkTheme: Boolean): ColorScheme {
        return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    override val currentMaterialYouDisplay = _dynamicColor.asStateFlow()
}

class AndroidJsonFileReader(private val context: Context) : JsonFileReader {
    override fun loadJsonFile(fileName: String): String {
        val inputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}

class AndroidKmpLauncher(private val context: Context) : KmpLauncher {
    private val playStoreUrl = "https://play.google.com/store/apps/details?id="

    override fun openEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(emailIntent)
    }

    override fun openPhone(phone: String) {
        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(phoneIntent)
    }

    override fun openStoreUrl(store: String) {
        val phoneIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("$playStoreUrl$store")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(phoneIntent)
    }

    override fun openUrl(url: String) {
        val urlIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(urlIntent)
    }
}