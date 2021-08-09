package javas.nios.exampless.nios;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/8/9
 * @desc
 */
public class SelectorWebServerDemo {
    /**
     * 官方介绍selector
     *A selector may be created by invoking the open method of this class,
     *  which will use the system's default selector provider to create a new selector.
     *  A selector may also be created by invoking the openSelector method of a custom selector provider.
     * A selector remains open until it is closed via its close method.
     *
     *  可以简单的理解 selector 为 channel 通道的管理者，负责通道的创建与消亡，
     *  根据通道的状态，是读是写，进行轮训,

     */
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        open.socket().bind(new InetSocketAddress("localhost",9999));
        //非阻塞
        open.configureBlocking(false);
        //打开选择器
        Selector selector = Selector.open();
        //通道与选择器建立关系，并接受
        open.register(selector, SelectionKey.OP_ACCEPT);

        //读写缓冲区
        ByteBuffer readBuffer=ByteBuffer.allocate(1024);
        ByteBuffer writeBuff  = ByteBuffer.allocate(128);


        writeBuff.put("received".getBytes(StandardCharsets.UTF_8));
        writeBuff.flip();

        while(true){
            int nReady  = selector.select();

            Set<SelectionKey> keys  = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                if(key.isAcceptable()){
                    // 创建新的连接，并且把连接注册到selector上，而且，
                    // 声明这个channel只对读操作感兴趣。
                    SocketChannel socketChannel  = open.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else  if(key.isReadable()){
                  SocketChannel socketChannel=(SocketChannel) key.channel();
                  readBuffer.clear();
                  socketChannel.read(writeBuff);
                  readBuffer.flip();
                  System.out.println("receive:"+new String(readBuffer.array()));
                  key.interestOps(SelectionKey.OP_WRITE);

                }else if (key.isWritable()){
                    writeBuff.rewind();
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    socketChannel.write(writeBuff);
                    key.interestOps(SelectionKey.OP_READ);
                }
            }


        }


    }



}
