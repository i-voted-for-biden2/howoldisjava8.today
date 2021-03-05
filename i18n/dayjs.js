import i18n from './config';

export default {
  locales: i18n.locales,
  defaultLocale: i18n.defaultLocale,
  defaultTimeZone: 'Europe/Berlin',
  plugins: [
    'utc',
    'timezone',
    'relativeTime'
  ]
}
