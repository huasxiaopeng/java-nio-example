package javas.nios.exampless.nios;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;

/**
 * @author lktbz
 * @version 1.0.0
 * @date 2021/8/9
 * @desc 缓冲区深入学习：
 */
public class BufferImproveDemo {
    public static void main(String[] args) throws IOException {
        /**
         * 准备条件
         */
        RandomAccessFile file=new RandomAccessFile("img.png", "rw");
        FileChannel  channel =file.getChannel();

        /**
         * 缓冲区的分配方式
         */
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        CharBuffer allocate1 = CharBuffer.allocate(1024);

        /**
         * 写数据到缓冲区的方式：
         */
        channel.read(allocate);
        /**
         * or 根据重载函数，可以提供很多种类型
         */
        allocate1.put("Hello Buffer".toCharArray());


        /**
         * flip 翻转，将buffer模式，读写来回切换
         */

        /**
         * 读取数据方式 write
         */
        int write = channel.write(allocate);
        /**
         *  读取数据方式 get
         */
        char c = allocate1.get();

        /**
         * 数据的重复读取  rewind()
         */
        allocate.rewind();

        /**
         * 数据读取完毕的操作，清空缓冲区（全部清除或者清除只读取过的数据）
         * .clear(); 清除全部数据
         * compact(); 保留未读取的数据
         */

        allocate.clear();
        allocate1.compact();

        /**
         * mark()与reset()方法
         * 通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。
         * 之后可以通过调用Buffer.reset()方法恢复到这个position
         */
    }
}
