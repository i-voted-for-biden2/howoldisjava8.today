/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import groovy.xml.MarkupBuilder

plugins {
  id("com.android.application")
  kotlin("android") version "1.5.20" apply false
}

// For some reason automatic applying applied this before the android plugin
apply(plugin = "kotlin-android")

android {
  compileSdkVersion(30)
  defaultConfig {
    applicationId = TWAManifest.applicationId
    minSdkVersion(23)
    targetSdkVersion(30)
    versionCode = 2
    versionName = "1.0.1"

    // The URL that will be used when launching the TWA from the Android Launcher
    val launchUrl = "https://" + TWAManifest.hostName + TWAManifest.launchUrl
    resValue("string", "launchUrl", launchUrl)


    // The URL the Web Manifest for the Progressive Web App that the TWA points to. This
    // is used by Chrome OS to open the Web version of the PWA instead of the TWA, as it
    // will probably give a better user experience for non-mobile devices.
    resValue("string", "webManifestUrl", "https://howoldisjava8.today/_nuxt/manifest.cea7da41.json")


    // The hostname is used when building the intent-filter, so the TWA is able to
    // handle Intents to open https://svgomg.firebaseapp.com.
    resValue("string", "hostName", TWAManifest.hostName)

    // This variable below expresses the relationship between the app and the site,
    // as documented in the TWA documentation at
    // https://developers.google.com/web/updates/2017/10/using-twa#set_up_digital_asset_links_in_an_android_app
    // and is injected into the AndroidManifest.xml

    resValue("string", "assetStatements", """
            [{
                "relation"=["delegate_permission/common.handle_all_urls"],
                "target"={
                    "namespace"="web",
                    "site"="https://${TWAManifest.hostName}"
                }
            }]""")

  // This attribute sets the status bar color for the TWA. It can be either set here or in
  // `res/values/colors.xml`. Setting in both places is an error and the app will not
  // compile. If not set, the status bar color defaults to #FFFFFF - white.
    resValue("color", "colorPrimary", TWAManifest.themeColor)

    // This attribute sets the navigation bar color for the TWA. It can be either set here or
    // in `res/values/colors.xml`. Setting in both places is an error and the app will not
    // compile. If not set, the navigation bar color defaults to #FFFFFF - white.
    resValue("color", "navigationColor", TWAManifest.navigationColor)

    // This attribute sets the dark navigation bar color for the TWA. It can be either set here
    // or in `res/values/colors.xml`. Setting in both places is an error and the app will not
    // compile. If not set, the navigation bar color defaults to #000000 - black.
    resValue("color", "navigationColorDark", TWAManifest.navigationColorDark)

    // This attribute sets the navbar divider color for the TWA. It can be either
    // set here or in `res/values/colors.xml`. Setting in both places is an error and the app
    // will not compile. If not set, the divider color defaults to #00000000 - transparent.
    resValue("color", "navigationDividerColor", TWAManifest.navigationDividerColor)

    // This attribute sets the dark navbar divider color for the TWA. It can be either
    // set here or in `res/values/colors.xml`. Setting in both places is an error and the
    //app will not compile. If not set, the divider color defaults to #000000 - black.
    resValue("color", "navigationDividerColorDark", TWAManifest.navigationDividerColorDark)

    // Sets the color for the background used for the splash screen when launching the
    // Trusted Web Activity.
    resValue("color", "backgroundColor", TWAManifest.backgroundColor)

    // Defines a provider authority fot the Splash Screen
    resValue("string", "providerAuthority", TWAManifest.applicationId + ".fileprovider")

    // The enableNotification resource is used to enable or disable the
    // TrustedWebActivityService, by changing the android:enabled and android:exported
    // attributes
    resValue("bool", "enableNotification", TWAManifest.enableNotifications.toString())

    TWAManifest.shortcuts.forEachIndexed { index, shortcut ->
      resValue("string", "shortcut_name_$index", "${shortcut.name}")
      resValue("string", "shortcut_short_name_$index", "${shortcut.shortName}")
    }

    // The splashScreenFadeOutDuration resource is used to set the duration of fade out animation in milliseconds
    // to be played when removing splash screen. The default is 0 (no animation).
    resValue("integer", "splashScreenFadeOutDuration", TWAManifest.splashScreenFadeOutDuration.toString())
    resValue("string", "generatorApp", TWAManifest.generatorApp)

    resValue("string", "fallbackType", TWAManifest.fallbackType)

    resValue("bool", "enableSiteSettingsShortcut", TWAManifest.enableSiteSettingsShortcut)
    resValue("string", "orientation", TWAManifest.orientation)
  }

  buildTypes {
    get("release").apply {
      isMinifyEnabled = true
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  lintOptions {
    isCheckReleaseBuilds = false
  }
}

tasks {
  val generateShortcutsFile = task("generateShortcutsFile") {
    ShortcutsHelper.performTask(projectDir)
  }

  preBuild {
    dependsOn(generateShortcutsFile)
  }
}

dependencies {
  implementation("com.google.androidbrowserhelper", "androidbrowserhelper", "2.2.2")
}
