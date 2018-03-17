package ru.alexandrkutashov.onetimesecret.ext

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