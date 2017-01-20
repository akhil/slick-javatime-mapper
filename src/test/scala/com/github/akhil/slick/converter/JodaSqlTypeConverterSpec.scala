package com.github.akhil.slick.converter

import java.time._
import java.sql.Time
import org.scalatest._
import java.util.{ TimeZone, Locale }

class SqlTypeConverterSpec extends FunSpec with Matchers with BeforeAndAfter {

  def fixture = new {
    val localDateConverter = new LocalDateSqlDateConverter {}
    val instantConverter = new InstantSqlTimestampConverter {}
    val localDateTimeConverter = new LocalDateTimeSqlTimestampConverter {}
    val localTimeConverter = new LocalTimeSqlTimeConverter {}
  }

  before {
    Locale.setDefault(Locale.JAPAN)
    val tz = TimeZone.getTimeZone("Asia/Tokyo")
    TimeZone.setDefault(tz)
  }

  describe("JodaLocalDateSqlDateConverter") {

    it("should convert LocalDate to java.sql.Date") {
      fixture.localDateConverter.toSqlType(null) should be(null)
      val date = new LocalDate(2013, 3, 23)
      fixture.localDateConverter.toSqlType(date).getTime should be(1363964400000L)
    }

    it("should convert java.sql.Date to LocalDate") {
      fixture.localDateConverter.fromSqlType(null) should be(null)
      fixture.localDateConverter.fromSqlType(new java.sql.Date(1364000000000L)) should be(new LocalDate(2013, 3, 23))
    }

  }

  describe("JodaInstantSqlTimestampConverter") {

    it("should convert Instant to java.sql.Timestamp") {
      fixture.instantConverter.toSqlType(null) should be(null)
      val current = Instant.now
      val timestamp = current.getMillis
      fixture.instantConverter.toSqlType(current).getTime should be(timestamp)
    }

    it("should convert java.sql.Timestamp to Instant") {
      fixture.instantConverter.fromSqlType(null) should be(null)
      val current = Instant.now
      val timestamp = current.getMillis
      fixture.instantConverter.fromSqlType(new java.sql.Timestamp(timestamp)) should be(current)
    }

  }

  describe("JodaLocalDateTimeSqlDateConverter") {

    it("should convert LocalDateTime to java.sql.Timestamp") {
      fixture.localDateTimeConverter.toSqlType(null) should be(null)
      val current = LocalDateTime.now
      val timestamp = current.toDate.getTime
      fixture.localDateTimeConverter.toSqlType(current).getTime should be(timestamp)
    }

    it("should convert java.sql.Timestamp to LocalDateTime") {
      fixture.localDateTimeConverter.fromSqlType(null) should be(null)
      val current = LocalDateTime.now
      val timestamp = current.toDate.getTime
      fixture.localDateTimeConverter.fromSqlType(new java.sql.Timestamp(timestamp)) should be(current)
    }

  }

  describe("JodaLocalTimeSqlTimeConverter") {

    it("should convert LocalTime to java.sql.Time") {
      fixture.localTimeConverter.fromSqlType(null) should be(null)
      val current = LocalTime.now
      fixture.localTimeConverter.toSqlType(current) should be(new Time(current.toDateTimeToday.getMillis))
    }

    it("should convert java.sql.Time to LocalTime") {
      fixture.localTimeConverter.fromSqlType(null) should be(null)
      val current = LocalTime.now
      val timestamp = current.toDateTimeToday.getMillis
      fixture.localTimeConverter.fromSqlType(new java.sql.Time(timestamp)) should be(current)
    }

  }

}
