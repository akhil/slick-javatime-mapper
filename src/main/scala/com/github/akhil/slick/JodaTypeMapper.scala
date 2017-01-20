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

import slick.driver.JdbcProfile
import java.time._
import java.sql._

import com.github.akhil.slick.converter._
import slick.jdbc.{ PositionedParameters, PositionedResult }

class JodaLocalDateMapper(val driver: JdbcProfile) {

  object TypeMapper extends driver.DriverJdbcType[LocalDate]
      with LocalDateSqlDateConverter {
    def sqlType = java.sql.Types.DATE
    override def setValue(v: LocalDate, p: PreparedStatement, idx: Int): Unit =
      p.setDate(idx, toSqlType(v))
    override def getValue(r: ResultSet, idx: Int): LocalDate =
      fromSqlType(r.getDate(idx))
    override def updateValue(v: LocalDate, r: ResultSet, idx: Int): Unit =
      r.updateDate(idx, toSqlType(v))
    override def valueToSQLLiteral(value: LocalDate): String = "{d '" + toSqlType(value).toString + "'}"
  }

  object JodaGetResult extends JavaTimeGetResult[java.sql.Date, LocalDate] with LocalDateSqlDateConverter {
    def next(rs: PositionedResult): Date = rs.nextDate()
    def nextOption(rs: PositionedResult): Option[Date] = rs.nextDateOption()
  }

  object JodaSetParameter extends JavaTimeSetParameter[java.sql.Date, LocalDate] with LocalDateSqlDateConverter {
    def set(rs: PositionedParameters, d: Date): Unit = rs.setDate(d)
    def setOption(rs: PositionedParameters, d: Option[Date]): Unit = rs.setDateOption(d)
  }

}

class JodaInstantMapper(val driver: JdbcProfile) {

  object TypeMapper extends driver.DriverJdbcType[Instant]
      with InstantSqlTimestampConverter {
    def sqlType = java.sql.Types.TIMESTAMP
    override def sqlTypeName(sym: scala.Option[slick.ast.FieldSymbol]): String =
      driver.columnTypes.timestampJdbcType.sqlTypeName(sym)
    override def setValue(v: Instant, p: PreparedStatement, idx: Int): Unit =
      p.setTimestamp(idx, toSqlType(v))
    override def getValue(r: ResultSet, idx: Int): Instant =
      fromSqlType(r.getTimestamp(idx))
    override def updateValue(v: Instant, r: ResultSet, idx: Int): Unit =
      r.updateTimestamp(idx, toSqlType(v))
    override def valueToSQLLiteral(value: Instant): String =
      driver.columnTypes.timestampJdbcType.valueToSQLLiteral(Timestamp.from(value))
  }

  object JavaTimeGetResult extends JavaTimeGetResult[Timestamp, Instant] with InstantSqlTimestampConverter {
    def next(rs: PositionedResult): Timestamp = rs.nextTimestamp()
    def nextOption(rs: PositionedResult): Option[Timestamp] = rs.nextTimestampOption()
  }

  object JavaTimeSetParameter extends JavaTimeSetParameter[Timestamp, Instant] with InstantSqlTimestampConverter {
    def set(rs: PositionedParameters, d: Timestamp): Unit = rs.setTimestamp(d)
    def setOption(rs: PositionedParameters, d: Option[Timestamp]): Unit = rs.setTimestampOption(d)
  }

}

class JodaLocalDateTimeMapper(val driver: JdbcProfile) {

  object TypeMapper extends driver.DriverJdbcType[LocalDateTime]
      with LocalDateTimeSqlTimestampConverter {
    def sqlType = java.sql.Types.TIMESTAMP
    override def sqlTypeName(sym: scala.Option[slick.ast.FieldSymbol]): String =
      driver.columnTypes.timestampJdbcType.sqlTypeName(sym)
    override def setValue(v: LocalDateTime, p: PreparedStatement, idx: Int): Unit =
      p.setTimestamp(idx, toSqlType(v))
    override def getValue(r: ResultSet, idx: Int): LocalDateTime =
      fromSqlType(r.getTimestamp(idx))
    override def updateValue(v: LocalDateTime, r: ResultSet, idx: Int): Unit =
      r.updateTimestamp(idx, toSqlType(v))
    override def valueToSQLLiteral(value: LocalDateTime): String =
      driver.columnTypes.timestampJdbcType.valueToSQLLiteral(Timestamp.valueOf(value))
  }

  object JodaGetResult extends JavaTimeGetResult[Timestamp, LocalDateTime] with LocalDateTimeSqlTimestampConverter {
    def next(rs: PositionedResult): Timestamp = rs.nextTimestamp()
    def nextOption(rs: PositionedResult): Option[Timestamp] = rs.nextTimestampOption()
  }

  object JodaSetParameter extends JavaTimeSetParameter[Timestamp, LocalDateTime] with LocalDateTimeSqlTimestampConverter {
    def set(rs: PositionedParameters, d: Timestamp): Unit = rs.setTimestamp(d)
    def setOption(rs: PositionedParameters, d: Option[Timestamp]): Unit = rs.setTimestampOption(d)
  }
}

class JodaLocalTimeMapper(val driver: JdbcProfile) {

  object TypeMapper extends driver.DriverJdbcType[LocalTime]
      with LocalTimeSqlTimeConverter {
    def sqlType = java.sql.Types.TIME
    override def setValue(v: LocalTime, p: PreparedStatement, idx: Int): Unit =
      p.setTime(idx, toSqlType(v))
    override def getValue(r: ResultSet, idx: Int): LocalTime =
      fromSqlType(r.getTime(idx))
    override def updateValue(v: LocalTime, r: ResultSet, idx: Int): Unit =
      r.updateTime(idx, toSqlType(v))
    override def valueToSQLLiteral(value: LocalTime): String = "{t '" + toSqlType(value).toString + "'}"
  }

  object JavaTimeGetResult extends JavaTimeGetResult[Time, LocalTime] with LocalTimeSqlTimeConverter {
    def next(rs: PositionedResult): Time = rs.nextTime()
    def nextOption(rs: PositionedResult): Option[Time] = rs.nextTimeOption()
  }

  object JavaTimeSetParameter extends JavaTimeSetParameter[Time, LocalTime] with LocalTimeSqlTimeConverter {
    def set(rs: PositionedParameters, d: Time): Unit = rs.setTime(d)
    def setOption(rs: PositionedParameters, d: Option[Time]): Unit = rs.setTimeOption(d)
  }

}
