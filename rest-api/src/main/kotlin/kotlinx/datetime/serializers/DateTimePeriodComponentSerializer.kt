/*
 * Copyright 2019-2021 JetBrains s.r.o.
 * Use of this source code is governed by the Apache 2.0 License that can be found in the LICENSE.txt file.
 *
 * This code is from part of the kotlinx-datetime repository on github!
 * It's used in this project because it's not available now in the current release.
 * When there is an update to kotlinx-datetime we will use the one in the library.
 *
 * https://github.com/Kotlin/kotlinx-datetime/blob/93c91503369f10a079be86849f1565ce2149aaf3/core/common/src/serializers/DateTimePeriodSerializers.kt
 */

package kotlinx.datetime.serializers

import kotlinx.datetime.DateTimePeriod
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*

object DateTimePeriodComponentSerializer : KSerializer<DateTimePeriod> {

  override val descriptor: SerialDescriptor =
    buildClassSerialDescriptor("DateTimePeriod") {
      element<Int>("years", isOptional = true)
      element<Int>("months", isOptional = true)
      element<Int>("days", isOptional = true)
      element<Int>("hours", isOptional = true)
      element<Int>("minutes", isOptional = true)
      element<Long>("seconds", isOptional = true)
      element<Long>("nanoseconds", isOptional = true)
    }

  override fun deserialize(decoder: Decoder): DateTimePeriod =
    decoder.decodeStructure(descriptor) {
      var years = 0
      var months = 0
      var days = 0
      var hours = 0
      var minutes = 0
      var seconds = 0L
      var nanoseconds = 0L
      loop@ while (true) {
        when (val index = decodeElementIndex(descriptor)) {
          0 -> years = decodeIntElement(descriptor, 0)
          1 -> months = decodeIntElement(descriptor, 1)
          2 -> days = decodeIntElement(descriptor, 2)
          3 -> hours = decodeIntElement(descriptor, 3)
          4 -> minutes = decodeIntElement(descriptor, 4)
          5 -> seconds = decodeLongElement(descriptor, 5)
          6 -> nanoseconds = decodeLongElement(descriptor, 6)
          CompositeDecoder.DECODE_DONE -> break@loop // https://youtrack.jetbrains.com/issue/KT-42262
          else -> throw SerializationException("Unexpected index: $index")
        }
      }
      DateTimePeriod(years, months, days, hours, minutes, seconds, nanoseconds)
    }

  override fun serialize(encoder: Encoder, value: DateTimePeriod) {
    encoder.encodeStructure(descriptor) {
      with(value) {
        if (years != 0) encodeIntElement(descriptor, 0, years)
        if (months != 0) encodeIntElement(descriptor, 1, months)
        if (days != 0) encodeIntElement(descriptor, 2, days)
        if (hours != 0) encodeIntElement(descriptor, 3, hours)
        if (minutes != 0) encodeIntElement(descriptor, 4, minutes)
        if (seconds != 0L) encodeLongElement(descriptor, 5, seconds)
        if (nanoseconds != 0L) encodeLongElement(descriptor, 6, value.nanoseconds)
      }
    }
  }
}
