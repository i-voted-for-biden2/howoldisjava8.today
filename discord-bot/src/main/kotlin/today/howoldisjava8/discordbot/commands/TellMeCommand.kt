package today.howoldisjava8.discordbot.commands

import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.InteractionResponseType
import dev.kord.common.entity.optional.optional
import dev.kord.core.Kord
import dev.kord.core.behavior.*
import dev.kord.core.behavior.respond
import dev.kord.core.entity.Member
import dev.kord.core.entity.interaction.GuildInteraction
import dev.kord.core.event.interaction.InteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.FollowupMessageCreateBuilder
import dev.kord.rest.builder.interaction.InteractionApplicationCommandCallbackDataBuilder
import dev.kord.rest.json.request.FollowupMessageCreateRequest
import dev.kord.rest.json.request.InteractionResponseCreateRequest
import dev.kord.rest.json.request.MultipartFollowupMessageCreateRequest
import dev.kord.x.commands.annotation.AutoWired
import dev.kord.x.commands.annotation.ModuleName
import dev.kord.x.commands.kord.module.command
import dev.kord.x.commands.model.command.invoke
import dev.kord.x.lavalink.LavaKord
import dev.kord.x.lavalink.audio.TrackEndEvent
import dev.kord.x.lavalink.kord.connectAudio
import dev.kord.x.lavalink.kord.getLink
import dev.kord.x.lavalink.rest.loadItem
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.koin.core.Koin
import today.howoldisjava8.discordbot.command.AbstractSlashCommand
import today.howoldisjava8.discordbot.core.formatMessage
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@AutoWired
@ModuleName("General")
fun tellMeCommand() = command("tellme") {
  invoke {
    val guild = guild // Make smart casting work
    if (guild == null) {
      respond("DMs aren't supported")
      return@invoke
    }

    author.asMember(guild.id).playSound(getKoin(), this::respond)
  }
}

private suspend fun MemberBehavior.playSound(koin: Koin, notInVCFail: suspend (String) -> Unit) {
  val lavakord = koin.get<LavaKord>() // injected from Launcher.kt

  // Find a Lavalink Node
  val link = guild.getLink(lavakord)

  // Find the channel of the user
  // getOrNull doesn't require a privileged intent as normal bots only get the voice state of users in VCs
  val channel = getVoiceStateOrNull()?.channelId ?: return notInVCFail("Please connect to a VC")

  // Connect to the channel
  link.connectAudio(channel)

  // Load the track
  val googleTTS =
    "https://translate.google.com/translate_tts?ie=UTF-8&tl=en-US&client=tw-ob&q=" +
      formatMessage().replace(' ', '+')
  val tts = link.loadItem(googleTTS).track

  // Play the track
  link.player.playTrack(tts)

  lavakord.launch {
    link.player
      .events
      .filterIsInstance<TrackEndEvent>()
      .take(1)
      .single() // This waits until the first Track end event

    link.destroy()
  }
}

@OptIn(KordPreview::class)
object TellMeSlashCommand : AbstractSlashCommand() {
  override val name: String = "tell-me"
  override val description: String = "Tells you the age of Java 8 in a VoiceChannel"

  override suspend fun InteractionCreateEvent.onInvocation() {
    with(interaction) {
      if (command.rootName == "tellme") {
        val ack = acknowledge()

        if (this !is GuildInteraction) {
          ack.followUp { content = "DMs aren't supported!" }
          return@with
        }

        member.playSound(koin) {
          ack.followUp { content = it }
        }
      }
    }
  }
}

