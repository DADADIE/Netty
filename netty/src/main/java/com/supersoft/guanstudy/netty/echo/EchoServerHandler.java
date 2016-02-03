package com.supersoft.guanstudy.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * Created by guanjunpu on 2016/1/28.
 */

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(final ChannelHandlerContext ctx,Object msg){
        final ByteBuf m = (ByteBuf) msg;
        try {
            byte[] message = new byte[m.readableBytes()];
            m.readBytes(message);
            String messageShow = new String(message,"UTF-8");
            System.out.println("Server received:" + messageShow);
            m.writeBytes(message);
            final ChannelFuture f =ctx.writeAndFlush(m);
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    assert f == future;
                    ctx.close();
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
