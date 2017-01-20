/*
 * Copyright 2013 Toshiyuki Takahashi
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.akhil.slick

import java.time.{ LocalDate, LocalDateTime, LocalTime }

import com.github.akhil.slick.converter._

object Converters {

  implicit class WrappedLocalDateTime(d: LocalDateTime) extends LocalDateTimeSqlTimestampConverter {
    def toSqlTimestamp(): java.sql.Timestamp = toSqlType(d)
  }

  implicit class WrappedLocalDateTimeOption(d: Option[LocalDateTime]) extends LocalDateTimeSqlTimestampConverter {
    def toSqlTimestampOption(): Option[java.sql.Timestamp] = d.map(toSqlType)
  }

  implicit class WrappedLocalDate(d: LocalDate) extends LocalDateSqlDateConverter {
    def toSqlDate(): java.sql.Date = toSqlType(d)
  }

  implicit class WrappedLocalDateOption(d: Option[LocalDate]) extends LocalDateSqlDateConverter {
    def toSqlDateOption(): Option[java.sql.Date] = d.map(toSqlType)
  }

  implicit class WrappedLocalTime(t: LocalTime) extends LocalTimeSqlTimeConverter {
    def toSqlTime(): java.sql.Time = toSqlType(t)
  }

  implicit class WrappedLocalTimeOption(t: Option[LocalTime]) extends LocalTimeSqlTimeConverter {
    def toSqlTimeOption(): Option[java.sql.Time] = t.map(toSqlType)
  }

  implicit class WrappedSqlTimestampForLocalDateTime(t: java.sql.Timestamp) extends LocalDateTimeSqlTimestampConverter {
    def toLocalDateTime(): LocalDateTime = fromSqlType(t)
  }

  implicit class WrappedSqlTimestampOptionForLocalDateTime(t: Option[java.sql.Timestamp]) extends LocalDateTimeSqlTimestampConverter {
    def toLocalDateTimeOption(): Option[LocalDateTime] = t.map(fromSqlType)
  }

  implicit class WrappedSqlTime(t: java.sql.Time) extends LocalTimeSqlTimeConverter {
    def toLocalTime(): LocalTime = fromSqlType(t)
  }

  implicit class WrappedSqlTimeOption(t: Option[java.sql.Time]) extends LocalTimeSqlTimeConverter {
    def toLocalTimeOption(): Option[LocalTime] = t.map(fromSqlType)
  }

  implicit class WrappedSqlDate(d: java.sql.Date) extends LocalDateSqlDateConverter {
    def toLocalDate(): LocalDate = fromSqlType(d)
  }

  implicit class WrappedSqlDateOption(d: Option[java.sql.Date]) extends LocalDateSqlDateConverter {
    def toLocalDateOption(): Option[LocalDate] = d.map(fromSqlType)
  }

}
