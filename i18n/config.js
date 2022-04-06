export default {
  locales: [
    {
      code: 'default',
      file: 'en/strings.js'
    },
    {
      code: 'en',
      file: 'en/strings.js'
    },
    {
      code: 'de',
      file: 'de/strings.js'
    },
    {
      code: 'fr',
      file: 'fr/strings.js'
    },
    {
      code: 'es',
      file: 'es/strings.js'
    }
  ],
  defaultLocale: 'default',
  vueI18n: {
    fallbackLocale: 'default'
  },
  lazy: true,
  langDir: 'i18n/',
  detectBrowserLanguage: {
    useCookie: false,
    redirectOn: 'root'
  }
}
