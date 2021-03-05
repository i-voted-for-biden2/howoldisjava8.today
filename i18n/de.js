// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default context => {
    return new Promise(function (resolve) {
        resolve({
            java_is_old: 'Java 8 is {specifier} old today.',
            loading: 'very',
            // very old
            and: 'and',
            time: {
                years: 'one year | {n} years',
                months: 'one month | {n} months',
                days: 'one day | {n} days'
            }
        });
    });
};