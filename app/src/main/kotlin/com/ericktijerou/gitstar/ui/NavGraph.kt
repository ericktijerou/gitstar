package com.ericktijerou.gitstar.ui

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.ericktijerou.gitstar.ui.util.Navigator
import kotlinx.parcelize.Parcelize

sealed class Destination : Parcelable {
    @Parcelize
    object Home : Destination()

    @Immutable
    @Parcelize
    data class Search(val itemId: String) : Destination()
}

class Actions(navigator: Navigator<Destination>) {
    val selectItem: (String) -> Unit = { itemId: String ->
        navigator.navigate(Destination.Search(itemId))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}
