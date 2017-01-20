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

import slick.driver._

class GenericJavaTimeSupport(val driver: JdbcProfile) {
  protected val localDateMapperDelegate = new JodaLocalDateMapper(driver)
  protected val instantMapperDelegate = new JodaInstantMapper(driver)
  protected val localDateTimeMapperDelegate = new JodaLocalDateTimeMapper(driver)
  protected val localTimeMapperDelegate = new JodaLocalTimeMapper(driver)

  implicit val localDateTypeMapper = localDateMapperDelegate.TypeMapper
  implicit val getLocalDateResult = localDateMapperDelegate.JodaGetResult.getResult
  implicit val getLocalDateOptionResult = localDateMapperDelegate.JodaGetResult.getOptionResult
  implicit val setLocalDateParameter = localDateMapperDelegate.JodaSetParameter.setJodaParameter
  implicit val setLocalDateOptionParameter = localDateMapperDelegate.JodaSetParameter.setJodaOptionParameter

  implicit val instantTypeMapper = instantMapperDelegate.TypeMapper
  implicit val getInstantResult = instantMapperDelegate.JavaTimeGetResult.getResult
  implicit val getInstantOptionResult = instantMapperDelegate.JavaTimeGetResult.getOptionResult
  implicit val setInstantParameter = instantMapperDelegate.JavaTimeSetParameter.setJodaParameter
  implicit val setInstantOptionParameter = instantMapperDelegate.JavaTimeSetParameter.setJodaOptionParameter

  implicit val localDatetimeTypeMapper = localDateTimeMapperDelegate.TypeMapper
  implicit val getLocalDatetimeResult = localDateTimeMapperDelegate.JodaGetResult.getResult
  implicit val getLocalDatetimeOptionResult = localDateTimeMapperDelegate.JodaGetResult.getOptionResult
  implicit val setLocalDatetimeParameter = localDateTimeMapperDelegate.JodaSetParameter.setJodaParameter
  implicit val setLocalDatetimeOptionParameter = localDateTimeMapperDelegate.JodaSetParameter.setJodaOptionParameter

  implicit val localTimeTypeMapper = localTimeMapperDelegate.TypeMapper
  implicit val getLocalTimeResult = localTimeMapperDelegate.JavaTimeGetResult.getResult
  implicit val getLocalTimeOptionResult = localTimeMapperDelegate.JavaTimeGetResult.getOptionResult
  implicit val setLocalTimeParameter = localTimeMapperDelegate.JavaTimeSetParameter.setJodaParameter
  implicit val setLocalTimeOptionParameter = localTimeMapperDelegate.JavaTimeSetParameter.setJodaOptionParameter

}

object H2JavaTimeSupport extends GenericJavaTimeSupport(H2Driver)
object PostgresJavaTimeSupport extends GenericJavaTimeSupport(PostgresDriver)
object MySQLJavaTimeSupport extends GenericJavaTimeSupport(MySQLDriver)
object HsqldbJavaTimeSupport extends GenericJavaTimeSupport(HsqldbDriver)
object SQLiteJavaTimeSupport extends GenericJavaTimeSupport(SQLiteDriver)
