package today.howoldisjava8.api.core

import kotlinx.datetime.*

class AgeServiceImpl : AgeService {
  override fun getAsPeriod(now: Instant): DateTimePeriod {
    return java8ReleaseInstant.periodUntil(now, TimeZone.UTC)
  }

  override fun getAsMessage(now: Instant): String = formatMessage(now)
}
