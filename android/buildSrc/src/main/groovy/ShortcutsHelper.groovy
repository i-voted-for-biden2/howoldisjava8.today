import groovy.xml.MarkupBuilder

// This said it's a duplicated definition but it's not so just ignore it
class ShortcutsHelper {
  static def performTask(File projectDir) {
    assert TWAManifest.shortcuts.length < 5, "You can have at most 4 shortcuts."
    TWAManifest.shortcuts.toList().eachWithIndex { s, i ->
      assert s.name != null, "Missing `name` in shortcut #" + i
      assert s.short_name != null, "Missing `short_name` in shortcut #" + i
      assert s.url != null, "Missing `icon` in shortcut #" + i
      assert s.icon != null, "Missing `url` in shortcut #" + i
    }

    def shortcutsFolder = new File(projectDir, "/src/main/res/xml")
    def shortcutsFile = new File(shortcutsFolder, "shortcuts.xml")

    def xmlWriter = new StringWriter()
    def xmlMarkup = new MarkupBuilder(new IndentPrinter(xmlWriter, "    ", true))

    xmlMarkup
      ."shortcuts"("xmlns:android": "http://schemas.android.com/apk/res/android") {
        TWAManifest.shortcuts.eachWithIndex { s, i ->
          "shortcut"(
            "android:shortcutId": "shortcut" + i,
            "android:enabled": "true",
            "android:icon": "@drawable/" + s.icon,
            "android:shortcutShortLabel": "@string/shortcut_short_name_" + i,
            "android:shortcutLongLabel": "@string/shortcut_name_" + i) {
            "intent"(
              "android:action": "android.intent.action.MAIN",
              "android:targetPackage": TWAManifest.applicationId,
              "android:targetClass": TWAManifest.applicationId + ".LauncherActivity",
              "android:data": s.url)
            "categories"("android:name": "android.intent.category.LAUNCHER")
          }
        }
      }
    shortcutsFile.text = xmlWriter.toString() + "\n"
  }
}
