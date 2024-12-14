package com.amarchaud.templateresumekmp

import com.amarchaud.shared.KmpLauncher
import com.amarchaud.shared.IosKmpLauncher
import com.amarchaud.shared.IosJsonFileReader
import com.amarchaud.shared.IosStoreDynamicColor
import com.amarchaud.shared.JsonFileReader
import com.amarchaud.shared.StoreDynamicColor
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(coreModule)
    }
}

val coreModule: Module = module {
    single<StoreDynamicColor> {
        IosStoreDynamicColor()
    }

    single<JsonFileReader> {
        IosJsonFileReader()
    }

    single<KmpLauncher> {
        IosKmpLauncher()
    }
}