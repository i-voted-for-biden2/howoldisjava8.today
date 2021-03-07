module.exports = {
  root: true,
  env: {
    browser: true,
    node: true
  },
  extends: [
    '@nuxtjs/eslint-config-typescript',
    'plugin:nuxt/recommended'
  ],
  plugins: [],
  // add your custom rules here
  rules: {},
  ignorePatterns: [
    '/i18n/*.js', // Generated files that we can't fix
    '!/i18n/config.js', // Self written config that we can fix
    '!/i18n/en.js' // Self written src file that we can fix
  ]
}
