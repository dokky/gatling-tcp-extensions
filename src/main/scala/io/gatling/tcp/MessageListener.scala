package io.gatling.tcp

import akka.actor.ActorRef
import com.typesafe.scalalogging.StrictLogging
import io.gatling.core.util.TimeHelper._
import org.jboss.netty.channel._

class MessageListener(tx: TcpTx, actor: ActorRef) extends SimpleChannelUpstreamHandler with StrictLogging {


  override def handleUpstream(ctx: ChannelHandlerContext, e: ChannelEvent): Unit = {
    logger.debug("" + e.getClass.getSimpleName + " " + e)
    super.handleUpstream(ctx, e)
  }

  override def messageReceived(ctx: ChannelHandlerContext, e: MessageEvent): Unit = {
    actor ! OnMessage(e.getMessage.asInstanceOf[Array[Byte]], nowMillis)
  }

  override def channelDisconnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
    actor ! OnDisconnect(nowMillis)
  }

  override def channelConnected(ctx: ChannelHandlerContext, e: ChannelStateEvent): Unit = {
//    actor ! OnConnect(tx, ctx.getChannel, nowMillis)
  }

  /**
    * Invoked when an exception was raised by an I/O thread or a
    * ChannelHandler
    */
  override def exceptionCaught(ctx: ChannelHandlerContext, e: ExceptionEvent):Unit = {
//    System.err.println("Error=======================================================: " + e.getCause)
    val cause = e.getCause
    if (cause.isInstanceOf[java.nio.channels.ClosedChannelException])
      actor ! OnDisconnect(nowMillis)
    else
      actor ! OnConnectFailed(tx, nowMillis, Option.apply(cause.getMessage).getOrElse(cause.getClass.getSimpleName))
    cause.printStackTrace()

  }

}
