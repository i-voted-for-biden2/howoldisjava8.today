object TWAManifest {
  const val applicationId = "today.howoldisjava8.stockbroud"
  const val hostName = "howoldisjava8.today" // The domain being opened in the TWA.
  const val launchUrl = "/?standalone=true" // The start path for the TWA. Must be relative to the domain.
  const val name = "How old is Java 8?" // The application name.
  const val launcherName = "java8isold" // The name shown on the Android Launcher.
  const val themeColor = "#121212" // The color used for the status bar.
  const val navigationColor = "#000000" // The color used for the navigation bar.
  const val navigationColorDark = "#000000" // The color used for the dark navbar.
  const val navigationDividerColor = "#000000" // The navbar divider color.
  const val navigationDividerColorDark = "#000000" // The dark navbar divider color.
  const val backgroundColor = "#121212" // The color used for the splash screen background.
  const val enableNotifications = true // Set to true to enable notification delegation.

  // Every shortcut must include the following fields:
  // - name: String that will show up in the shortcut.
  // - short_name: Shorter string used if |name| is too long.
  // - url: Absolute path of the URL to launch the app with (e.g "/create").
  // - icon: Name of the resource in the drawable folder to use as an icon.
  val shortcuts = emptyArray<ShortCut>()

  // The duration of fade out animation in milliseconds to be played when removing splash screen.
  const val splashScreenFadeOutDuration = 300
  const val generatorApp = "bubblewrap-cli" // Application that generated the Android Project

  // The fallback strategy for when Trusted Web Activity is not avilable. Possible values are
  // "customtabs" and "webview".
  const val fallbackType = "customtabs"
  const val enableSiteSettingsShortcut = "true"
  const val orientation = "portrait-primary"

  class ShortCut(val name: String?, val shortName: String?, val url: String?, val icon: String?)
}
