package io.gatling.tcp.check

import io.gatling.core.check.{ CheckResult, Check }
import io.gatling.core.session.Session
import io.gatling.core.util.TimeHelper._
import io.gatling.core.validation.Validation

import scala.collection.mutable
import scala.concurrent.duration.FiniteDuration

case class TcpCheck(wrapped: Check[Array[Byte]], timeout: FiniteDuration, timestamp: Long = nowMillis) extends Check[Array[Byte]] {
  override def check(message: Array[Byte], session: Session)(implicit cache: mutable.Map[Any, Any]): Validation[CheckResult] = wrapped.check(message, session)
}
