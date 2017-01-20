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
package com.github.akhil.slick.converter

import java.time._
import java.sql.{ Timestamp, Time }

trait LocalDateSqlDateConverter
    extends SqlTypeConverter[java.sql.Date, LocalDate] {

  def toSqlType(d: LocalDate): java.sql.Date =
    if (d == null) null else java.sql.Date.valueOf(d)

  def fromSqlType(d: java.sql.Date): LocalDate =
    if (d == null) null else d.toLocalDate
}

trait InstantSqlTimestampConverter
    extends SqlTypeConverter[Timestamp, Instant] {

  def fromSqlType(t: java.sql.Timestamp): Instant =
    if (t == null) null else t.toInstant

  def toSqlType(t: Instant): java.sql.Timestamp =
    if (t == null) null else java.sql.Timestamp.from(t)
}

trait LocalDateTimeSqlTimestampConverter
    extends SqlTypeConverter[Timestamp, LocalDateTime] {

  def fromSqlType(t: java.sql.Timestamp): LocalDateTime =
    if (t == null) null else t.toLocalDateTime

  def toSqlType(t: LocalDateTime): java.sql.Timestamp =
    if (t == null) null else java.sql.Timestamp.valueOf(t)

}

trait LocalTimeSqlTimeConverter
    extends SqlTypeConverter[Time, LocalTime] {

  def fromSqlType(t: java.sql.Time): LocalTime =
    if (t == null) null else t.toLocalTime

  def toSqlType(t: LocalTime): java.sql.Time = {
    if (t == null) null else java.sql.Time.valueOf(t)
  }
}
