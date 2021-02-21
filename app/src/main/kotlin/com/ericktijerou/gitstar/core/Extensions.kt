package com.ericktijerou.gitstar.core

fun Any.asString(): String = (this as? String).orEmpty()
fun Int?.orZero() = this ?: NUMBER_ZERO