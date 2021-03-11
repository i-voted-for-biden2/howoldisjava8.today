export default {
  locales: [
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
  defaultLocale: 'en',
  vueI18n: {
    fallbackLocale: 'en'
  },
  lazy: true,
  langDir: 'i18n/',
  detectBrowserLanguage: {
    useCookie: false
  }
}
