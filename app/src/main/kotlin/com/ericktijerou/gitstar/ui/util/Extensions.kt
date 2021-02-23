package com.ericktijerou.gitstar.ui.util

import android.annotation.SuppressLint
import android.text.format.DateUtils
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavBackStackEntry
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toJoinedDate(): String {
    return this.toDate()?.formatToViewDateDefaults().orEmpty()
}

fun String.toDate(): Date? {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return dateFormat.parse(this)
}

@SuppressLint("DefaultLocale")
fun Date.formatToViewDateDefaults(): String {
    val sdf = SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
    return sdf.format(this).capitalize()
}

fun String.toRelativeTime(): String {
    val now = Date().time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")
    val date = dateFormat.parse(this)
    val timeInMilliseconds = date?.time ?: 0
    val difference = now - timeInMilliseconds
    val relativeTime = when {
        difference < DateUtils.MINUTE_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            timeInMilliseconds,
            now,
            DateUtils.SECOND_IN_MILLIS
        )
        difference < DateUtils.HOUR_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            timeInMilliseconds,
            now,
            DateUtils.MINUTE_IN_MILLIS
        )
        difference < DateUtils.DAY_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            timeInMilliseconds,
            now,
            DateUtils.HOUR_IN_MILLIS
        )
        difference < DateUtils.WEEK_IN_MILLIS -> DateUtils.getRelativeTimeSpanString(
            timeInMilliseconds,
            now,
            DateUtils.DAY_IN_MILLIS
        )
        else -> DateUtils.getRelativeTimeSpanString(
            timeInMilliseconds,
            now,
            DateUtils.WEEK_IN_MILLIS
        )
    }
    return relativeTime.toString()
}

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.hiltNavGraphViewModel(): VM {
    val viewModelFactory = HiltViewModelFactory(LocalContext.current, this)
    return ViewModelProvider(this, viewModelFactory).get(VM::class.java)
}