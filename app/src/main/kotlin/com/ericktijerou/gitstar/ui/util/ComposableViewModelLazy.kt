/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        (LocalViewModelStoreOwner.current as NavBackStackEntry).viewModelStore,
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
