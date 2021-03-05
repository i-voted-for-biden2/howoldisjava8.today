// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default (context) => {
  return new Promise(function (resolve) {
    resolve({
      java_is_old: 'Java 8 ist heute {specifier} alt.',
      loading: 'sehr', // sehr alt
      and: 'und',
      time: {
        years: 'ein Jahr | {n} Jahre',
        months: 'einen Monat | {n} Monate',
        days: 'einen Tag | {n} Tage'
      }
    })
  })
}
