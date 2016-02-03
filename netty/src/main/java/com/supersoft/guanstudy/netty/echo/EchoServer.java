package com.supersoft.guanstudy.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by guanjunpu on 2016/1/28.
 */
public class EchoServer {
    private final int port;
    public EchoServer(int port){
        this.port = port;
    }
    public void start()throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
             .channel(NioServerSocketChannel.class)
             .localAddress(new InetSocketAddress(port))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel socketChannel) throws Exception {
                     socketChannel.pipeline().addLast(new EchoServerHandler());
                 }
             });
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName()+"started and listen on "+f.channel().localAddress());
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args)throws Exception{
        System.out.println(args.length);
        if(args.length!=1){
            System.err.println("Usage:"+EchoServer.class.getSimpleName()+"<port>");
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }
}
