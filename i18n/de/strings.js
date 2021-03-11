// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default context => {
    return new Promise(function (resolve) {
        resolve({
            java_is_old: 'Java 8 ist heute {specifier} alt.',
            loading: 'sehr',
            // very old
            and: 'und',
            time: {
                years: 'ein Jahr | {n} Jahre',
                months: 'ein Monat | {n} Monate',
                days: 'ein Tag | {n} Tage'
            }
        });
    });
};
