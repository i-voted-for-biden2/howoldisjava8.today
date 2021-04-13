package today.howoldisjava8.discordbot

import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.on
import dev.kord.x.commands.kord.bot
import dev.kord.x.commands.kord.model.prefix.kord
import dev.kord.x.commands.kord.model.prefix.mention
import dev.kord.x.commands.model.prefix.literal
import dev.kord.x.commands.model.prefix.or
import dev.schlaubi.lavakord.kord.lavakord
import kapt.kotlin.generated.configure
import org.koin.dsl.module
import today.howoldisjava8.discordbot.commands.HowOldIsJava8SlashCommand
import today.howoldisjava8.discordbot.commands.TellMeSlashCommand

suspend fun main() {
  bot(System.getenv("TOKEN")) {
    // Lavalink is an easy way of doing multiplatform audio with Kord
    // See this for more: https://github.com/DRSchlaubi/lavakord
    val lavakord = kord.lavakord().apply {
      addNode(System.getenv("LAVALINK_HOST"), System.getenv("LAVALINK_KEY"))
    }

    configure() // This method gets auto-generated upon the first build and adds all @AutoWired commands

    // This makes lavakord available for dependency injection
    koin {
      modules(
        module {
          single { lavakord }
        }
      )
    }

    // This set's the prefix configuration
    // If you @file:AutoWire you can put this as a val anywhere in your file
    prefix {
      kord {
        mention() or literal("j8") or literal("java8") or literal("java")
      }
    }

    // We need to wait for a connection before we can do this
    kord.on<ReadyEvent> {
      kord.editPresence {
        playing("With Java 8's big brother (Java 16)")
      }
    }

    HowOldIsJava8SlashCommand.register(getKoin()) // See commands/HowOldIsJava8.kt
    TellMeSlashCommand.register(getKoin()) // See commands/TellMeCommand.kt
  }
}
