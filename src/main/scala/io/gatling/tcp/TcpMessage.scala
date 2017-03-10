package io.gatling.tcp

import io.gatling.core.session.Expression
import org.jboss.netty.buffer.{ChannelBuffer, ChannelBuffers}


case class TcpMessage(message: Array[Byte]) {
  def toChannelBuffer:ChannelBuffer = ChannelBuffers.wrappedBuffer(message)
}
