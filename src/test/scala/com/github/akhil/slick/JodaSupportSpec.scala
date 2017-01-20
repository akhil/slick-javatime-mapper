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

import org.scalatest.{ BeforeAndAfter, FunSpec }
import org.scalatest._
import java.time._
import scala.concurrent.ExecutionContext.Implicits.global
import slick.driver.JdbcProfile
import slick.jdbc.GetResult
import slick.jdbc.ActionBasedSQLInterpolation._
import java.util.{ TimeZone, Locale }

abstract class JodaSupportSpec(
  val driver: JdbcProfile,
  val javaTimeSupport: GenericJavaTimeSupport,
  val jdbcUrl: String,
  val jdbcDriver: String,
  val jdbcUser: String,
  val jdbcPassword: String) extends FunSpec
    with Matchers
    with BeforeAndAfter {

  import driver.api._
  import javaTimeSupport._

  case class Jodas(
    localDate: LocalDate,
    instant: Instant,
    localDateTime: LocalDateTime,
    localTime: LocalTime,
    optLocalDate: Option[LocalDate],
    optInstant: Option[Instant],
    optLocalDateTime: Option[LocalDateTime],
    optLocalTime: Option[LocalTime])

  class JodaTest(tag: Tag) extends Table[Jodas](tag, "JODA_TEST") {
    def localDate = column[LocalDate]("LOCAL_DATE")
    def instant = column[Instant]("INSTANT")
    def localDateTime = column[LocalDateTime]("LOCAL_DATE_TIME")
    def localTime = column[LocalTime]("LOCAL_TIME")
    def optLocalDate = column[Option[LocalDate]]("OPT_LOCAL_DATE")
    def optInstant = column[Option[Instant]]("OPT_INSTANT")
    def optLocalDateTime = column[Option[LocalDateTime]]("OPT_LOCAL_DATE_TIME")
    def optLocalTime = column[Option[LocalTime]]("OPT_LOCAL_TIME")
    def * = (localDate, instant, localDateTime, localTime, optLocalDate, optInstant, optLocalDateTime, optLocalTime) <> (Jodas.tupled, Jodas.unapply _)
  }

  val db = Database.forURL(url = jdbcUrl, user = jdbcUser, password = jdbcPassword, driver = jdbcDriver)

  val jodaTest = TableQuery[JodaTest]

  before {
    Locale.setDefault(Locale.JAPAN)
    val tz = TimeZone.getTimeZone("Asia/Tokyo")
    TimeZone.setDefault(tz)

    db.run(DBIO.seq(jodaTest.schema.create))
  }

  after {
    db.run(DBIO.seq(jodaTest.schema.drop))
  }

  def insertTestData(): DBIOAction[Unit, NoStream, Effect.Write] = {
    DBIO.seq(
      jodaTest += Jodas(
        LocalDate.of(2012, 12, 4),
        LocalDateTime.of(2012, 12, 4, 0, 0, 0, 0).toInstant(null),
        LocalDateTime.of(2012, 12, 4, 0, 0, 0, 0),
        LocalTime.of(0,0),
        Some(LocalDate.of(2012, 12, 4)),
        Some(LocalDateTime.of(2012, 12, 4, 0, 0, 0, 0).toInstant(null)),
        Some(LocalDateTime.of(2012, 12, 4, 0, 0, 0, 0)),
        Some(LocalTime.of(0,0))
      ),
      jodaTest +=
        Jodas(
          LocalDate.of(2012, 12, 5),
          LocalDateTime.of(2012, 12, 5, 0, 0, 0, 0).toInstant(null),
          LocalDateTime.of(2012, 12, 5, 0, 0, 0, 0),
          LocalTime.of(0,0),
          Some(LocalDate.of(2012, 12, 5)),
          None,
          None,
          Some(LocalTime.of(0,0))
        ),
      jodaTest +=
        Jodas(
          LocalDate.of(2012, 12, 6),
          LocalDateTime.of(2012, 12, 6, 0, 0, 0, 0).toInstant(null),
          LocalDateTime.of(2012, 12, 6, 0, 0, 0, 0),
          LocalTime.of(0,0),
          Some(LocalDate.of(2012, 12, 6)),
          None,
          None,
          Some(LocalTime.of(0,0))
        )
    )
  }

  describe("JodaSupport") {

    it("should enable us to use joda-time with slick") {
      db.run(insertTestData()).flatMap { _ =>
        db.run(jodaTest.result).map(_ should have size 3)
      }
    }

    it("should enable us to use joda-time with string interpolation API") {
      db.run(insertTestData()).flatMap { _ =>
        db.run(sql"SELECT opt_local_date FROM joda_test WHERE local_date = ${new LocalDate(2012, 12, 4)}"
          .as[Option[LocalDate]].head) should be(Some(new LocalDate(2012, 12, 4)))
        db.run(sql"SELECT opt_date_time FROM joda_test WHERE date_time = ${new DateTime(2012, 12, 4, 0, 0, 0, 0)}"
          .as[Option[DateTime]].head) should be(Some(new DateTime(2012, 12, 4, 0, 0, 0, 0)))
        db.run(sql"SELECT opt_instant FROM joda_test WHERE instant = ${new DateTime(2012, 12, 4, 0, 0, 0, 0)}"
          .as[Option[Instant]].head) should be(Some(new DateTime(2012, 12, 4, 0, 0, 0, 0).toInstant))
        db.run(sql"SELECT opt_local_date_time FROM joda_test WHERE local_date_time = ${new LocalDateTime(2012, 12, 4, 0, 0, 0, 0)}"
          .as[Option[LocalDateTime]].head) should be(Some(new LocalDateTime(2012, 12, 4, 0, 0, 0, 0)))
        db.run(sql"SELECT opt_local_time FROM joda_test WHERE local_time = ${new LocalTime(0)}"
          .as[Option[LocalTime]].head) should be(Some(new LocalTime(0)))
        db.run(sql"SELECT local_date FROM joda_test WHERE opt_local_date = ${Some(new LocalDate(2012, 12, 5))}"
          .as[LocalDate].head) should be(new LocalDate(2012, 12, 5))
        db.run(sql"SELECT date_time FROM joda_test WHERE opt_date_time = ${Some(new DateTime(2012, 12, 4, 0, 0, 0, 0))}"
          .as[DateTime].head) should be(new DateTime(2012, 12, 4, 0, 0, 0, 0))
        db.run(sql"SELECT instant FROM joda_test WHERE opt_instant = ${Some(new DateTime(2012, 12, 4, 0, 0, 0, 0).toInstant)}"
          .as[Instant].head) should be(new DateTime(2012, 12, 4, 0, 0, 0, 0).toInstant)
        db.run(sql"SELECT local_date_time FROM joda_test WHERE opt_local_date_time = ${Some(new LocalDateTime(2012, 12, 4, 0, 0, 0, 0))}"
          .as[LocalDateTime].head) should be(new LocalDateTime(2012, 12, 4, 0, 0, 0, 0))
        db.run(sql"SELECT local_time FROM joda_test WHERE opt_local_time = ${Some(new LocalTime(0))}"
          .as[LocalTime].head) should be(new LocalTime(0))

        implicit val getResult: GetResult[(LocalDate, Instant, LocalDateTime, LocalTime)] = GetResult(r => (r.<<, r.<<, r.<<, r.<<))
        implicit val getResult2: GetResult[Jodas] = GetResult(r => Jodas(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))

        db.run(sql"SELECT local_date, instant, local_date_time, local_time FROM joda_test".as[(LocalDate, Instant, LocalDateTime, LocalTime)]) map (_ should have size 3)
        db.run(sql"SELECT local_date, instant, local_date_time, local_time, opt_local_date, opt_instant, opt_local_date_time, opt_local_time FROM joda_test".as[Jodas]) map (_ should have size 3)

      }
    }

    it("can be used with comparative operators") {
      db.run(insertTestData()).flatMap { _ =>
        val q1 = jodaTest.filter(_.localDate > new LocalDate(2012, 12, 5))
        db.run(q1.result).map(_ should have size 1)
      }
    }

    it("should be able to filter with the specified date") {
      db.run(insertTestData()).flatMap { _ =>
        val q1 = for {
          jt <- jodaTest
          if jt.localDate === new LocalDate(2012, 12, 5)
        } yield jt

        db.run(q1.result) map { res1 =>
          res1 should have size 1
          res1.headOption.map(_.localDate) should be(Some(new LocalDate(2012, 12, 5)))
        }

        val q2 = for {
          jt <- jodaTest
          if jt.localDate =!= new LocalDate(2012, 12, 5)
        } yield jt
        db.run(q2.result).map { res2 =>
          res2 should have size 2
          res2.lift(1).map(_.localDate) should not be Some(new LocalDate(2012, 12, 5))
          res2.lift(2).map(_.localDate) should not be Some(new LocalDate(2012, 12, 5))
        }
      }

    }
  }
}

import slick.driver._

class H2JodaSupportSpec extends JodaSupportSpec(H2Driver, H2JavaTimeSupport, "jdbc:h2:mem:testh2;DB_CLOSE_DELAY=-1", "org.h2.Driver", "sa", null)
