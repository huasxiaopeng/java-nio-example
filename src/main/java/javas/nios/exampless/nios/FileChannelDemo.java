package javas.nios.exampless.nios;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/8/8
 * @desc nio 之 channel
 */
public class FileChannelDemo {
    /**
     * Channel channel
     * 顾名思义以：需要进行数据传输 建立的通道
     * 1：数据源是什么方式？
     * 2：怎么去建立通道
     * 3：通道怎么建立，与什么合适的时间去关闭通道
     * <p>
     * 1：FileChannel 文件通道
     * 文件建立通道的方式
     * FileInputStream.getChannel(),
     * FileOutputStream.getChannel(),
     * RandomAccessFile.getChannel()
     * 2：网络方式channel SocketChannel ServerSocketChannel(tcp 读取数据)
     * DatagramChannel  从udp 读取数据
     * 建立通道方式：
     * ServerSocket socket=new ServerSocket(6366);
     * ServerSocketChannel channel = socket.getChannel();
     * <p>
     * 网络相关，将在后续的demo 上补充
     */

    public static void main(String[] args) throws IOException {
        /**
         * 定义一个随机文件存取
         */
        RandomAccessFile file = new RandomAccessFile("out.txt", "rw");

        FileChannel channel = file.getChannel();
//        创建读缓冲区对象
        ByteBuffer bufferRead = ByteBuffer.allocate(48);
        int read = channel.read(bufferRead);
        //定义写缓冲区
        ByteBuffer bufferWrite= ByteBuffer.allocate(48);
//        服务端写会客户端
        bufferWrite.put("fileChannel test".getBytes(StandardCharsets.UTF_8));
        /**
         * 切换成读模式
         */
        bufferWrite.flip();
        channel.read(bufferRead);
        while (read != -1) {
            System.out.println("read = " + read);
            bufferRead.flip();
            while (bufferRead.hasRemaining()) {
                System.out.print((char) bufferRead.get());
            }
            bufferRead.clear();
            read = channel.read(bufferRead);
        }
    }
}
