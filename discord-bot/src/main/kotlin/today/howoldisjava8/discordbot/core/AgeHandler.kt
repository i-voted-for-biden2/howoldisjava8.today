package today.howoldisjava8.discordbot.core

import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Permission
import dev.kord.core.behavior.InteractionBehavior
import dev.kord.core.behavior.channel.MessageChannelBehavior
import dev.kord.core.behavior.channel.createMessage
import dev.kord.core.behavior.respond
import dev.kord.core.entity.channel.TextChannel
import dev.kord.x.commands.kord.model.context.KordCommandEvent
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
fun formatMessage(): String {
  val now = Clock.System.now()
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
      addSeparator(days > 0)
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

suspend fun MessageChannelBehavior.sendNotice(tts: Boolean) = createMessage {
  this.tts = safeTTS(tts)
  content = formatMessage()
}

// This acks and responds to a slash command with the java8 info
// Every slash command has to be acked within 3 seconds
// If you want to ack before you respond you need to replace respond with followUp
@OptIn(KordPreview::class)
suspend fun InteractionBehavior.sendNotice(tts: Boolean) = respond {
  this.tts = (kord.getChannel(channelId) as? TextChannel)?.safeTTS(tts) == true
  content = formatMessage()
}

suspend fun KordCommandEvent.sendNotice(tts: Boolean) = channel.sendNotice(tts)

/**
 * Checks whether a channel can supply tts if wanted.
 */
suspend fun MessageChannelBehavior.safeTTS(wantTTS: Boolean) = wantTTS &&
  (asChannel() as? TextChannel)?.getEffectivePermissions(kord.selfId)?.contains(
  Permission.SendTTSMessages
) == true
