// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default context => {
    return new Promise(function (resolve) {
        resolve({
            java_is_old: 'Oggi, Java 8 è {specifier} vecchio.',
            loading: 'molto',
            // very old
            and: 'e',
            time: {
                years: 'un anno | {n} anni',
                months: 'un mese | {n} mesi',
                days: 'un giorno | {n} giorni'
            }
        });
    });
};