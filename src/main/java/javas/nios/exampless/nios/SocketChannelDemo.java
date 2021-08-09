package javas.nios.exampless.nios;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/8/9
 * @desc  客户端代码
 */
public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("localhost", 9999));
        if(socketChannel.isOpen()){
            /**
             * 服务器写缓冲区
             */
            ByteBuffer writeBuffer = ByteBuffer.allocate(128);
            //缓冲区写数据
            writeBuffer.put("hello server this is  from client ".getBytes(StandardCharsets.UTF_8));
            //切换模式
            writeBuffer.flip();
            //向通道写数据
            socketChannel.write(writeBuffer);
            /**
             * 读缓冲区
             */
            ByteBuffer readBuffer = ByteBuffer.allocate(128);

            socketChannel.read(readBuffer);
            StringBuffer stringBuffer=new StringBuffer();
            while (readBuffer.hasRemaining()){
                stringBuffer.append((char) readBuffer.get());
            }
            System.out.println("从服务端接受的数据为："+stringBuffer);
            socketChannel.close();
        }

    }
}
