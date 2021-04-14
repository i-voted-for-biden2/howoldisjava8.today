package today.howoldisjava8.api.core

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
interface AgeService {
  fun getAsPeriod(now: Instant = Clock.System.now()): DateTimePeriod
  fun getAsMessage(now: Instant = Clock.System.now()): String
}
