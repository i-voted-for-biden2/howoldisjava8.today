export default {
  locales: [
    {
      code: 'en',
      file: 'en.js'
    },
    {
      code: 'de',
      file: 'de.js'
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
