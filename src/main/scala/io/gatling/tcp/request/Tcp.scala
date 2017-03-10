package io.gatling.tcp.request

import io.gatling.core.session.Expression
import io.gatling.tcp.action.{TcpConnectActionBuilder, TcpDisconnectActionBuilder, TcpSendActionBuilder}
import io.gatling.tcp.TcpMessage

class Tcp(requestName: Expression[String]) {

  def connect() = new TcpConnectActionBuilder(requestName)

  def send(message: Expression[Array[Byte]]) = new TcpSendActionBuilder(requestName, message.map(TcpMessage))

  def disconnect() = new TcpDisconnectActionBuilder(requestName)

}
