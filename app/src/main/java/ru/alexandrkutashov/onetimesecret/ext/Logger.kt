package ru.alexandrkutashov.onetimesecret.ext

import android.util.Log
import ru.alexandrkutashov.onetimesecret.BuildConfig

/**
 * @author Alexandr Kutashov
 * on 17.03.2018
 */

inline fun Exception.log() {
    if (BuildConfig.DEBUG) {
        printStackTrace()
    }
}

inline fun String.debug(tag: String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, this)
    }
}