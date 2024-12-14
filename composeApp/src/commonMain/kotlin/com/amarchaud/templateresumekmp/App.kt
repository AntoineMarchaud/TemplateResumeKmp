package com.amarchaud.templateresumekmp

import androidx.compose.runtime.Composable
import com.amarchaud.shared.ui.ResumeComposable
import com.amarchaud.shared.ui.theme.TemplateResumeKmpTheme

@Composable
fun App() {
    TemplateResumeKmpTheme {
        ResumeComposable()
    }
}