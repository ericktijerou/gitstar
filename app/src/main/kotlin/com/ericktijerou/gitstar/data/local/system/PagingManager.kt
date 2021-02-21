package com.ericktijerou.gitstar.data.local.system

import android.content.Context
import android.content.SharedPreferences
import com.ericktijerou.gitstar.core.EMPTY
import com.ericktijerou.gitstar.core.NUMBER_ZERO_LONG
import javax.inject.Inject

class PagingManager @Inject constructor(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.ericktijerou.gitstar.preferences"
        private const val PREF_KEY_LAST_CACHE = "last_cache"
        private const val PREF_KEY_LAST_USER_CURSOR = "last_user_cursor"
        private const val PREF_KEY_LAST_REPOSITORY_CURSOR = "last_repository_cursor"
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var lastCacheTime: Long
        get() = pref.getLong(PREF_KEY_LAST_CACHE, NUMBER_ZERO_LONG)
        set(lastCache) = pref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

    var lastUserCursor: String?
        get() = pref.getString(PREF_KEY_LAST_USER_CURSOR, EMPTY)
        set(lastCursor) = pref.edit().putString(PREF_KEY_LAST_USER_CURSOR, lastCursor).apply()

    var lastRepositoryCursor: String?
        get() = pref.getString(PREF_KEY_LAST_REPOSITORY_CURSOR, EMPTY)
        set(lastCursor) = pref.edit().putString(PREF_KEY_LAST_REPOSITORY_CURSOR, lastCursor).apply()
}