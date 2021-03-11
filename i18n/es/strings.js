// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default context => {
    return new Promise(function (resolve) {
        resolve({
            java_is_old: 'Java 8 ahora es {specifier} antiguo.',
            loading: 'muy',
            // very old
            and: 'y',
            time: {
                years: 'un año | {n} años',
                months: 'un mes | {n} meses',
                days: 'un día | {n} día'
            }
        });
    });
};