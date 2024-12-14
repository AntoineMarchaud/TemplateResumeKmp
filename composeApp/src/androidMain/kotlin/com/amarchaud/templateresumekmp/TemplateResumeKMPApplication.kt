package com.amarchaud.templateresumekmp

import android.app.Application
import com.amarchaud.shared.AndroidKmpLauncher
import com.amarchaud.shared.AndroidJsonFileReader
import com.amarchaud.shared.AndroidStoreDynamicColor
import com.amarchaud.shared.KmpLauncher
import com.amarchaud.shared.JsonFileReader
import com.amarchaud.shared.StoreDynamicColor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger
import org.koin.core.module.Module
import org.koin.dsl.module

class TemplateResumeKMP : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(PrintLogger(Level.DEBUG))
            androidContext(this@TemplateResumeKMP)
            modules(coreModule)
        }
    }
}

val coreModule: Module = module {
    single<StoreDynamicColor> {
        AndroidStoreDynamicColor(context = androidContext())
    }
    single<JsonFileReader> {
        AndroidJsonFileReader(context = androidContext())
    }

    single<KmpLauncher> {
        AndroidKmpLauncher(context = androidContext())
    }
}