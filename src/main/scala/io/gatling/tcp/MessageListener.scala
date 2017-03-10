package io.gatling.tcp

import akka.actor.ActorRef
import io.gatling.core.util.TimeHelper._
import org.jboss.netty.channel._

class MessageListener(tx: TcpTx, actor: ActorRef) extends SimpleChannelUpstreamHandler {
  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    actor ! OnMessage(e.getMessage.asInstanceOf[Array[Byte]], nowMillis)
  }

  override def channelDisconnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
    actor ! OnDisconnect(nowMillis)
  }

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
    actor ! OnConnect(tx, ctx.getChannel, nowMillis)
  }

  /**
    * Invoked when an exception was raised by an I/O thread or a
    * {@link ChannelHandler}.
    */
  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent):Unit = {
    e.getCause.printStackTrace()
  }

}
