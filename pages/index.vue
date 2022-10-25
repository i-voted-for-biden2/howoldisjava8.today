<template>
  <div class="flex flex-col justify-center items-center h-full">
    <p v-if="difference" class="age-text">
      {{
        $t('java_is_old', {
          specifier: enumerate([
            difference.years >= 1 ? $tc('time.years', difference.years) : null,
            difference.months >= 1 ? $tc('time.months', difference.months) : null,
            difference.days >= 1 ? $tc('time.days', difference.days) : null,
          ])
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

function notEmpty<TValue> (value: TValue | null | undefined): value is TValue {
  return value !== null && value !== undefined
}

export default Vue.extend({
  data () {
    return {
      difference2: null
    }
  },

  computed: {
    difference () {
      const current = this.$moment()

      const release = this.$moment(java8Release)
      return this.$moment.preciseDiff(current, release, true)
    }
  },

  methods: {
    enumerate (nullableInputs: (String|null)[]): String {
      const nonNullableInputs = nullableInputs.filter(notEmpty)
      let output = ''
      for (let i = 0; i < nonNullableInputs.length; i++) {
        if (i > 0) {
          output += ' '
          if (i <= nonNullableInputs.length - 2) {
            output += ', '
          } else {
            output += ' '
            output += this.$t('and')
            output += ' '
          }
        }

        output += nonNullableInputs[i]
      }

      return output
    }
  }
})
</script>

<style>
.age-text {
  font-weight: bolder;
  text-align: center;
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
