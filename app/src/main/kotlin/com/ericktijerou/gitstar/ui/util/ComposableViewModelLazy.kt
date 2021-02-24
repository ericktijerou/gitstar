package com.ericktijerou.gitstar.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavBackStackEntry
import kotlin.reflect.KClass

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.hiltNavGraphViewModel(): VM {
    val viewModelFactory = HiltViewModelFactory(LocalContext.current, this)
    return ViewModelProvider(this, viewModelFactory).get(VM::class.java)
}

@Composable
inline fun <reified VM : ViewModel> hiltViewModel(): Lazy<VM> {
    val factory = HiltViewModelFactory(
        LocalContext.current,
        LocalViewModelStoreOwner.current as NavBackStackEntry
    )
    return createHiltViewModelLazy(
        VM::class,
        LocalViewModelStoreOwner.current.viewModelStore,
        factory
    )
}

fun <VM : ViewModel> createHiltViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: ViewModelStore,
    factoryProducer: Factory
): Lazy<VM> {
    return ViewModelLazy(viewModelClass, { storeProducer }, { factoryProducer })
}