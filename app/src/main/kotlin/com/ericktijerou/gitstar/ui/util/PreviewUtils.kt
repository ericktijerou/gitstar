package com.ericktijerou.gitstar.ui.util

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.ericktijerou.gitstar.ui.theme.GitstarTheme

@Composable
internal fun ThemedPreview(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    GitstarTheme(darkTheme = darkTheme) {
        Surface {
            content()
        }
    }
}
