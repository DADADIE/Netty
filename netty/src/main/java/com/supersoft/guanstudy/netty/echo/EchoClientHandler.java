package com.supersoft.guanstudy.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * Created by guanjunpu on 2016/1/28.
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        final ByteBuf time = ctx.alloc().buffer(50);
        time.writeBytes("Netty rocks!".getBytes());
        ctx.writeAndFlush(time);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf m = (ByteBuf) msg;
        try {
            byte[] message = new byte[m.readableBytes()];
            m.readBytes(message);
            String messageShow = new String(message,"UTF-8");
            System.out.println("Client received:" + messageShow);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }finally {
            ctx.close();
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
