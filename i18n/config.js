export default {
  locales: [
    {
      code: 'en',
      file: 'en.js'
    },
    {
      code: 'de',
      file: 'de.js'
    },
    {
      code: 'fr',
      file: 'fr.js'
    },
    {
      code: 'es',
      file: 'es.js'
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
