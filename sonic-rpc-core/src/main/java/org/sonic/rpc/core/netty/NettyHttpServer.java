package org.sonic.rpc.core.netty;

import java.util.function.Function;

import org.sonic.rpc.core.LogCore;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyHttpServer {
	public void start(int port, Function<String, String> httpCall) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
			            .childHandler(new ChannelInitializer<SocketChannel>() {
				            @Override
				            public void initChannel(SocketChannel ch) throws Exception {
					            // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
					            ch.pipeline().addLast(new HttpResponseEncoder());
					            // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
					            ch.pipeline().addLast(new HttpRequestDecoder());
					            ch.pipeline().addLast(new NettyHttpServerInboundHandler(httpCall));
				            }
			            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture f = b.bind(port).sync();

			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		NettyHttpServer server = new NettyHttpServer();
		LogCore.BASE.info("Http Server listening on 8844 ...");
		server.start(8844,msg -> msg);
	}
}
