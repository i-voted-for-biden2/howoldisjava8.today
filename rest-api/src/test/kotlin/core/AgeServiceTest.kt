package core

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import today.howoldisjava8.api.core.AgeService
import today.howoldisjava8.api.core.AgeServiceImpl
import kotlin.test.*


object AgeServiceTest : KoinTest {

  private val service: AgeService by inject()

  @BeforeTest
  fun setup() {
    startTestKoin()
  }

  @AfterTest
  fun teardown() {
    stopKoin()
  }

  @Test
  fun `Test Message`() {
    val now = Instant.parse("2021-04-13T13:41:43.173513700Z")
    val dateTimePeriod =
      DateTimePeriod(years = 7, months = 0, days = 26, hours = 14, minutes = 41, seconds = 43, nanoseconds = 173513700)
    assertEquals(dateTimePeriod, service.getAsPeriod(now))
  }

  @Test
  fun `Test Message fail`() {
    val dateTimePeriod = DateTimePeriod(0, 0, 0, 0, 0, 0, 0)
    assertNotEquals(dateTimePeriod, service.getAsPeriod())
  }

  private fun startTestKoin() = startKoin {
    modules(
      module {
        single<AgeService> { AgeServiceImpl() }
      }
    )
  }
}
