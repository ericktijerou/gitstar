package com.ericktijerou.gitstar.ui

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.ericktijerou.gitstar.ui.home.Home
import com.ericktijerou.gitstar.ui.theme.GitstarTheme
import com.ericktijerou.gitstar.ui.util.Navigator
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@Composable
fun GitstartMain(backDispatcher: OnBackPressedDispatcher) {
    val navigator: Navigator<Destination> = rememberSaveable(
        saver = Navigator.saver(backDispatcher)
    ) {
        Navigator(Destination.Home, backDispatcher)
    }
    val actions = remember(navigator) { Actions(navigator) }
    ProvideWindowInsets {
        GitstarTheme {
            Crossfade(navigator.current) { destination ->
                if (destination == Destination.Home) Home(actions.selectItem)
            }
        }
    }
}
