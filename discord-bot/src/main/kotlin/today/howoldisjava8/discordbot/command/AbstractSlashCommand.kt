package today.howoldisjava8.discordbot.command

import dev.kord.common.annotation.KordPreview
import dev.kord.core.Kord
import dev.kord.core.entity.interaction.CommandInteraction
import dev.kord.core.event.interaction.InteractionCreateEvent
import dev.kord.rest.builder.interaction.ChatInputCreateBuilder
import dev.kord.rest.builder.interaction.MultiApplicationCommandBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.core.Koin

/**
 * Abstraction for slash commands.
 *
 * @property name the name of the slash command
 * @property description the description of the slash command
 */
@KordPreview
abstract class AbstractSlashCommand {
  abstract val name: String
  abstract val description: String

  /**
   * The projects [Koin] instance.
   */
  protected lateinit var koin: Koin
    private set

  /**
   * Registers the slash command to the project.
   *
   * @throws IllegalStateException if it already got registeres
   */
  open suspend fun MultiApplicationCommandBuilder.register(koin: Koin) {
    require(!this@AbstractSlashCommand::koin.isInitialized) { "Command already registered" }
    this@AbstractSlashCommand.koin = koin
    val kord = koin.get<Kord>()
    registerCommand(kord)
    registerListener(kord)
  }

  private fun registerListener(kord: Kord) {
    kord
      .events
      .buffer(Channel.UNLIMITED)
      .filterIsInstance<InteractionCreateEvent>()
      .filter { (it.interaction as? CommandInteraction)?.command?.rootName == name }
      .onEach {
        kord.launch {
          it.onInvocation()
        }
      }.launchIn(kord)
  }

  /**
   * This gets invoked on any [InteractionCreateEvent] that matches [name].
   */
  protected abstract suspend fun InteractionCreateEvent.onInvocation()

  // Global commands have a 2 hour update time so for testing you may want to use guild commands
  // as they update instantly
  // You can only create guild commands or use global commands if the bot is added with the application.commands scope
  // In addition to the bot scope
  // Global commands work on any DM Channel and on any guild the bot has the scope on
  @OptIn(KordPreview::class)
  private suspend fun MultiApplicationCommandBuilder.registerCommand(kord: Kord) {
    input(name, description) { commandOptions() }
  }

  /**
   * This can be overridden to apply on options to the command.
   */
  open suspend fun ChatInputCreateBuilder.commandOptions() = Unit
}
