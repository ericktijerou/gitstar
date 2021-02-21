package com.ericktijerou.gitstar.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import com.ericktijerou.gitstar.ui.util.LocalSysUiController
import com.ericktijerou.gitstar.ui.util.SystemUiController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            Providers(LocalSysUiController provides systemUiController) {
                GitstartMain(onBackPressedDispatcher)
            }
        }
    }
}