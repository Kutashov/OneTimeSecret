package ru.alexandrkutashov.onetimesecret.ext

import android.annotation.SuppressLint
import android.content.res.Resources
import ru.alexandrkutashov.onetimesecret.R
import java.lang.String.format
import java.util.concurrent.TimeUnit

/**
 * Helper class for converting
 *
 * @author Alexandr Kutashov
 * on 08.04.2018
 */

@SuppressLint("StringFormatMatches")
fun Int?.secondsToInTimeString(resourceManager: Resources): String? {
    this?.toLong()?.apply {
        return resourceManager.getString(R.string.expired_, secondsToInDaysString(resourceManager))
    }
    return null
}

private fun Long.secondsToInDaysString(resourceManager: Resources): String? {
    val days = TimeUnit.SECONDS.toDays(this).toInt() + 1
    return when (days) {
        in 2..Int.MAX_VALUE -> format(resourceManager.getQuantityString(R.plurals.in_time_days, days), days)
        1 -> secondsToInHoursString(resourceManager)
        else -> null
    }
}


private fun Long.secondsToInHoursString(resourceManager: Resources): String? {
    val hours = TimeUnit.SECONDS.toHours(this).toInt() + 1
    return when (hours) {
        in 2..Int.MAX_VALUE -> format(resourceManager.getQuantityString(R.plurals.in_time_hours, hours), hours)
        1 -> secondsToInMinutesString(resourceManager)
        else -> null
    }
}

private fun Long.secondsToInMinutesString(resourceManager: Resources): String? {
    val minutes = TimeUnit.SECONDS.toMinutes(this).toInt() + 1
    return when (minutes) {
        in 1..Int.MAX_VALUE ->
            return format(resourceManager.getQuantityString(R.plurals.in_time_minutes, minutes), minutes)
        else -> null
    }
}
