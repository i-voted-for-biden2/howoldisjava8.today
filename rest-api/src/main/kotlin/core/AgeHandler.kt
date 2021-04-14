package today.howoldisjava8.api.core

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil

// https://www.java.com/de/download/help/release_dates.html
const val JAVA_8_RELEASE = 1395097200000
val java8ReleaseInstant = Instant.fromEpochMilliseconds(JAVA_8_RELEASE)

/**
 * Formats a message telling you how old Java 8 is.
 */
fun formatMessage(now: Instant): String {
  val period = java8ReleaseInstant.periodUntil(now, TimeZone.UTC)

  return buildString {
    append("Java 8 is")
    append(' ')

    val years = period.years
    if (years > 0) {
      addPluralization("one year", "years", years)
    }

    val months = period.months
    val days = period.days
    if (months > 0) {
      addSeparator(days <= 0)
      addPluralization("one month", "months", months)
    }
    if (days > 0) {
      addSeparator(true)
      addPluralization("one day", "days", days)
    }

    append(' ')
    append("old today")
  }
}

/**
 * Adds a pluralized string to this builder
 * If [count] is one it appends [singular]
 * If [count] is > 1 it appends "[count] [plural]"
 */
private fun StringBuilder.addPluralization(singular: String, plural: String, count: Int) = if (count > 1) {
  append(count)
  append(' ')
  append(plural)
} else append(singular)

/**
 * Adds a separator between two words.
 * @param probablyLast whether to append " and " or ", "
 */
private fun StringBuilder.addSeparator(probablyLast: Boolean = false) {
  if (probablyLast) {
    append(' ')
    append("and")
    append(' ')
  } else {
    append(',')
    append(' ')
  }
}
