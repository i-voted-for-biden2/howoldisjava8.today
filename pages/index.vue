<template>
  <div class="flex flex-col justify-center items-center h-full">
    <p v-if="difference" class="age-text">
      {{
        $t('java_is_old', {
          specifier: $tc('time.years', difference.years) + ', '
            + $tc('time.months', difference.months)
            + ' ' + $t('and') + ' '
            + $tc('time.days', difference.days)
        })
      }}
    </p>
    <p v-else class="age-text">
      {{
        $t('java_is_old', {
          specifier: $t('loading')
        })
      }}
    </p>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import 'moment-precise-range-plugin'

// https://www.java.com/de/download/help/release_dates.html
const java8Release = 1395097200000

export default Vue.extend({
  data () {
    return {
      difference: null
    }
  },

  async mounted () {
    const response = await this.$axios.$get('https://worldtimeapi.org/api/ip')
    const current = this.$moment(response.unixtime * 1000) // unixtime is actuallx secs but day.js expects millis
    const release = this.$moment(java8Release)
    this.$data.difference = this.$moment.preciseDiff(current, release, true)
  }

})
</script>

<style>
.age-text {
  font-weight: bolder;
  font-size: xx-large;
}

/* Default colors */
body {
  color: #121212;
  background-color: #ffffff;
}

/* Styles for users who prefer dark mode at the OS level */
@media (prefers-color-scheme: dark) {
  /* defaults to dark theme */
  body {
    color: #ffffff;
    background-color: #121212;
  }
}
</style>
