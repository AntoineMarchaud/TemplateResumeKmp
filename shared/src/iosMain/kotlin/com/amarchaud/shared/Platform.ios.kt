package com.amarchaud.shared

import androidx.compose.material3.ColorScheme
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.MutableStateFlow
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

class IosStoreDynamicColor : StoreDynamicColor {

    override fun hasMaterialYou() = false

    override fun updateMaterialYouColor(b: Boolean) {
    }

    override fun getDynamicColorScheme(darkTheme: Boolean): ColorScheme? = null

    override val currentMaterialYouDisplay = MutableStateFlow(false)
}

class IosJsonFileReader : JsonFileReader {
    @OptIn(ExperimentalForeignApi::class)
    override fun loadJsonFile(fileName: String): String {
        val bundle = NSBundle.mainBundle
        val filePath = bundle.pathForResource(
            name = fileName.substringBeforeLast("."),
            ofType = "json"
        )
        return filePath?.let {
            NSString.stringWithContentsOfFile(it, NSUTF8StringEncoding, null)
        }
            ?: throw IllegalArgumentException("File $fileName not found in bundle or could not be read")
    }
}

class IosKmpLauncher : KmpLauncher {
    private val playStoreUrl = "https://play.google.com/store/apps/details?id="

    override fun openEmail(email: String) {
        val emailUrl = NSURL.URLWithString("mailto:$email")
        if (emailUrl != null && UIApplication.sharedApplication.canOpenURL(emailUrl)) {
            UIApplication.sharedApplication.openURLModern(emailUrl)
        } else {
            showAlert("No email application is configured on this device.")
        }
    }

    override fun openPhone(phone: String) {
        val phoneUrl = NSURL.URLWithString("tel:$phone")
        if (phoneUrl != null && UIApplication.sharedApplication.canOpenURL(phoneUrl)) {
            UIApplication.sharedApplication.openURLModern(phoneUrl)
        } else {
            showAlert("No phone application is available on this device.")
        }
    }

    override fun openStoreUrl(store: String) {
        openUrl("$playStoreUrl$store")
    }

    override fun openUrl(url: String) {
        val webUrl = NSURL.URLWithString(url)
        if (webUrl != null && UIApplication.sharedApplication.canOpenURL(webUrl)) {
            UIApplication.sharedApplication.openURLModern(webUrl)
        } else {
            println("No application available to handle the URL.")
        }
    }

    private fun showAlert(message: String) {
        val alert = UIAlertController.alertControllerWithTitle(
            title = "Error",
            message = message,
            preferredStyle = UIAlertControllerStyleAlert
        )
        alert.addAction(UIAlertAction.actionWithTitle("OK", style = UIAlertActionStyleDefault, handler = null))
        UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(alert, animated = true, completion = null)
    }

    private fun UIApplication.openURLModern(url: NSURL) {
        this.openURL(url, emptyMap<Any?, Any?>(), null)
    }
}