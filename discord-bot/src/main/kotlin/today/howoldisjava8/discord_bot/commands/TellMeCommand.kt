package today.howoldisjava8.discord_bot.commands

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
import org.koin.core.get
import today.howoldisjava8.discord_bot.core.formatMessage

@AutoWired
@ModuleName("General")
fun tellMeCommand() = command("tellme") {
  invoke {
    val lavakord = get<LavaKord>() // injected from Launcher.kt

    val guild = guild // Make smart casting work
    if (guild == null) {
      respond("DMs aren't supported")
      return@invoke
    }

    // Find a Lavalink Node
    val link = guild.getLink(lavakord)

    // Find the channel of the user
    // getOrNull doesn't require a privileged intent as normal bots only get the voice state of users in VCs
    val channel = author.asMember(guild.id).getVoiceStateOrNull()?.channelId
    if (channel == null) {
      respond("Please connect to a VC")
      return@invoke
    }

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
}
