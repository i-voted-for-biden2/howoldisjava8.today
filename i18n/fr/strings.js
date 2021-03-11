// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default context => {
    return new Promise(function (resolve) {
        resolve({
            java_is_old: 'Java 8 a {specifier} aujourd\'hui.',
            loading: 'beaucoup',
            // very old
            and: 'et',
            time: {
                years: 'un an | {n} ans',
                months: 'un mois | {n} mois',
                days: 'un jour | {n} jours'
            }
        });
    });
};