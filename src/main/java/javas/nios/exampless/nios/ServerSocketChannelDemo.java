package javas.nios.exampless.nios;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/8/9
 * @desc server channel Demo
 */
public class ServerSocketChannelDemo {
    public static void main(String[] args) throws IOException {
        //创建socket 通道
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定地址
        ssc.socket().bind(new InetSocketAddress("localhost", 9999));
        //接受链接
        SocketChannel socketChannel = ssc.accept();
        if(socketChannel.isConnected()){
            //写数据缓冲区
            ByteBuffer writeBuffer = ByteBuffer.allocate(128);
            writeBuffer.put("hello client this is from webserver".getBytes(StandardCharsets.UTF_8));
            writeBuffer.flip();
            //往客户端写数据
            socketChannel.write(writeBuffer);
            //读缓冲区
            ByteBuffer readBuffer = ByteBuffer.allocate(128);
            socketChannel.read(readBuffer);
            StringBuffer stringBuffer = new StringBuffer();
            //模式切换
            readBuffer.flip();
            while (readBuffer.hasRemaining()) {
                //从缓冲区里获取数据
                stringBuffer.append((char) readBuffer.get());
            }
            System.out.println("receice client is :" + stringBuffer);
            socketChannel.close();
            ssc.close();
        }

    }
}
