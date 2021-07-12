package today.howoldisjava8.discordbot.commands

import dev.kord.common.annotation.KordPreview
import dev.kord.core.entity.interaction.CommandInteraction
import dev.kord.core.event.interaction.InteractionCreateEvent
import dev.kord.rest.builder.interaction.ApplicationCommandCreateBuilder
import dev.kord.x.commands.annotation.AutoWired
import dev.kord.x.commands.annotation.ModuleName
import dev.kord.x.commands.argument.extension.named
import dev.kord.x.commands.argument.extension.withDefault
import dev.kord.x.commands.argument.primitive.BooleanArgument
import dev.kord.x.commands.kord.module.command
import dev.kord.x.commands.model.command.invoke
import today.howoldisjava8.discordbot.command.AbstractSlashCommand
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
object HowOldIsJava8SlashCommand : AbstractSlashCommand() {
  override val name: String = "howoldisjava8"
  override val description: String = "Tells you the age of Java 8"

  override suspend fun InteractionCreateEvent.onInvocation() {
    val command = (interaction as? CommandInteraction)?.command ?: return
    interaction.sendNotice(command.options["use-tts"]?.value as? Boolean == true)
  }

  override suspend fun ApplicationCommandCreateBuilder.commandOptions() {
    boolean("use-tts", "Read the message using TTS") {
      default = false
    }
  }
}
