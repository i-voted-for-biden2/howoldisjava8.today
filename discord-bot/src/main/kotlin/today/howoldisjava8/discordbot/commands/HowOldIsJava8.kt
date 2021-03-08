package today.howoldisjava8.discordbot.commands

import dev.kord.common.annotation.KordPreview
import dev.kord.core.Kord
import dev.kord.core.event.interaction.InteractionCreateEvent
import dev.kord.core.on
import dev.kord.x.commands.annotation.AutoWired
import dev.kord.x.commands.annotation.ModuleName
import dev.kord.x.commands.argument.extension.named
import dev.kord.x.commands.argument.extension.withDefault
import dev.kord.x.commands.argument.primitive.BooleanArgument
import dev.kord.x.commands.kord.module.command
import dev.kord.x.commands.model.command.invoke
import today.howoldisjava8.discordbot.core.sendNotice

// See this for more: https://github.com/kordlib/kordx.commands
// This has to annotate every command
@AutoWired
// This is a shortcut to set the module name of the command
@ModuleName("General")
fun howOldIsJava8() = command("howoldisjava8") {
  // The invoke method is the one, as the name suggests, that gets called whenever a command gets invokes
  invoke(
    // You can either provide no arguments or up to 23 arguments like BooleanArgument
    BooleanArgument
      // Arguments can have names
      .named("use-tts")
      // And default values
      .withDefault(false)
  ) { useTTS -> // The arguments later translate
    // into lambda arguments, which are type and null-safe
    sendNotice(useTTS) // see core/AgeHandler.kt
  }
}

@OptIn(KordPreview::class)
object HowOldIsJava8SlashCommand {
  /**
   * Registers this command to [kord].
   */
  suspend fun register(kord: Kord) {
    registerListener(kord)
    registerCommand(kord)
  }

  // Eventually you will be able to use kordx.commands for this
  private fun registerListener(kord: Kord) = kord.on<InteractionCreateEvent> {
    val command = interaction.command
    if (command.rootName == "howoldisjava8") {
      // this acks and responds as you can see in core/AgeHandler.kt
      interaction.sendNotice(command.options["use-tts"]?.value as? Boolean == true)
    }
  }

  private suspend fun registerCommand(kord: Kord) {
    // Global commands have a 2 hour update time so for testing you may want to use guild commands
    // as they update instantly
    // You can only create guild commands or use global commands if the bot is added with the application.commands scope
    // In addition to the bot scope
    // Global commands work on any DM Channel and on any guild the bot has the scope on
    kord.slashCommands.createGlobalApplicationCommand("howoldisjava8", "Tells you the age of Java 8") {
      boolean("use-tts", "Read the message using TTS") {
        default = false
      }
    }
  }
}
