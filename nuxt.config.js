import i18n from './i18n/config'

export default {
  target: 'static',

  head: {
    title: "It's time to ditch Java 8",
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1.0' },
      { hid: 'description', name: 'description', content: "It's very old. Click the link for how old it is" },
      { hid: 'image', name: 'image', content: 'https://howoldisjava8.today/icon.png' },
      { hid: 'robots', name: 'robots', content: 'index, follow' },
      { hid: 'application-name', name: 'application-name', content: 'How old is Java 8 today?' },
      { hid: 'og:title', property: 'og:title', content: 'How old is Java 8 today?' },
      { hid: 'og:site_name', property: 'og:site_name', content: 'How old is Java 8 today?' },
      { hid: 'og:type', property: 'og:type', content: 'website' },
      { hid: 'og:url', property: 'og:url', content: 'https://howoldisjava8.today' },
      { hid: 'og:description', property: 'og:description', content: "It's very old. Click the link for how old it is" },
      { hid: 'og:locale', property: 'og:locale', content: 'en_US' },
      { hid: 'og:image', property: 'og:image', content: 'https://howoldisjava8.today/icon.png' },
      { hid: 'og:image:type', property: 'og:image:type', content: 'image/png' },
      { hid: 'og:image:width', property: 'og:image:width', content: '500' },
      { hid: 'og:image:height', property: 'og:image:height', content: '500' },
      { hid: 'og:image:alt', property: 'og:image:alt', content: 'Java logo' },
      { hid: 'twitter:card', name: 'twitter:card', content: 'summary' },
      { hid: 'twitter:creator', name: 'twitter:creator', content: '@SchlaumeierTVDE ' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      { rel: 'apple-touch-icon', type: '57x57', href: '/apple-icon-57x57.png' },
      { rel: 'apple-touch-icon', type: '60x60', href: '/apple-icon-60x60.png' },
      { rel: 'apple-touch-icon', type: '72x72', href: '/apple-icon-72x72.png' },
      { rel: 'apple-touch-icon', type: '76x76', href: '/apple-icon-76x76.png' },
      { rel: 'apple-touch-icon', type: '114x114', href: '/apple-icon-114x114.png' },
      { rel: 'apple-touch-icon', type: '120x120', href: '/apple-icon-120x120.png' },
      { rel: 'apple-touch-icon', type: '144x144', href: '/apple-icon-144x144.png' },
      { rel: 'apple-touch-icon', type: '152x152', href: '/apple-icon-152x152.png' },
      { rel: 'apple-touch-icon', type: '180x180', href: '/apple-icon-180x180.png' }
    ]
  },

  components: true,

  build: {
    analyze: true,
    extractCSS: true
  },

  buildModules: [
    '@nuxt/typescript-build',
    '@nuxtjs/tailwindcss',
    '@nuxtjs/moment',
    '@nuxtjs/pwa',
    '@nuxt/postcss8'
  ],

  modules: [
    '@nuxtjs/axios', ['@nuxtjs/i18n', i18n],
    '@nuxtjs/pwa'
  ],

  moment: {
    plugins: [
      'moment-precise-range-plugin'
    ]
  },

  pwa: {
    icon: [{
      source: 'static/pwa-startup-icon.png',
      targetDir: 'pwa-icons'
    }],
    manifest: {
      languages: i18n.locales.map(it => it.code),
      lang: 'en',
      name: 'How old is Java 8?',
      background_color: '#121212',
      theme_color: '#121212',
      orientation: 'portrait-primary',
      screenshots: [
        {
          src: 'screenshots/light_phone.png',
          sizes: '256x512',
          type: 'image/png'
        },
        {
          src: 'screenshots/dark_phone.png',
          sizes: '256x512',
          type: 'image/png'
        },
        {
          src: 'screenshots/light_tablet.png',
          sizes: '384x512',
          type: 'image/png'
        },
        {
          src: 'screenshots/dark_tablet.png',
          sizes: '384x512',
          type: 'image/png'
        }
      ],
      categories: ['education', 'utilities'],
      shortcuts: []
    },
    workbox: {}
  }
}
