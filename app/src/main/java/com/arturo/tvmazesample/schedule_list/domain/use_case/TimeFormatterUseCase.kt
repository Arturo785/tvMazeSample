package com.arturo.tvmazesample.schedule_list.domain.use_case

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TimeFormatterUseCase {

    operator fun invoke(timeInMilis: Long): String {
        // Convert milliseconds to Instant
        val instant = Instant.ofEpochMilli(timeInMilis)
        // Specify the time zone
        val timeZone = ZoneId.of("America/New_York") // Example time zone
        // Convert Instant to LocalDate
        val localDate = instant.atZone(timeZone).toLocalDate().plusDays(1)
        // Format the LocalDate to ISO 8601 string (year-month-day)
        val iso8601Date = DateTimeFormatter.ISO_LOCAL_DATE.format(localDate)

        return iso8601Date
    }
}