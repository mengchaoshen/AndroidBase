package com.smc.androidbase.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class NioTest {

    public final static String FILE_STR = "/Users/shenmengchao/Documents/arcvideo/log/test.txt";

    public static void main(String[] args) {
        try {
            testChannel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testSelector() throws IOException {
        Selector selector = Selector.open();//开启一个Selector
        SocketChannel channel = null;
        channel.configureBlocking(false);//设置Channel为非阻塞，只能是网络类型的Channel
        channel.register(selector, SelectionKey.OP_READ);//把channel注册到Selector
        while (true) {//开始使用Selector循环监听channel
            int readyChannels = selector.select();
            //判断当前是否有状态改变的key
            if (readyChannels == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {//遍历SelectorKeys
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isConnectable()) {//判断key的各个状态

                } else if (selectionKey.isAcceptable()) {

                } else if (selectionKey.isReadable()) {

                } else if (selectionKey.isWritable()) {

                }
                iterator.remove();
            }
        }
    }

    private static void testChannel() throws IOException {
        //创建一个RandomAccessFile
        RandomAccessFile raf = new RandomAccessFile(FILE_STR, "rw");
        FileChannel fileChannel = raf.getChannel();//获取一个FileChannel
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);//创建一个ByteBuffer
        int bytesRead = fileChannel.read(byteBuffer);//把Channel中的数据读取到Buffer中去，读取的大小是buffer的大小
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {//判断buffer里面是否有剩余的
                //从buffer中获取数据
                System.out.println((char) byteBuffer.get());
            }
            byteBuffer.clear();//数据打印完毕后，buffer清除掉，以供新的数据读入
            bytesRead = fileChannel.read(byteBuffer);//再次把channel内的数据读取到buffer
        }
        raf.close();
    }


    private static void testBuffer() {
        //分配一个48字节的byte buffer缓冲区
        int[] ints = {1, 2, 3, 4};
        int[] ints2 = {34, 87};
        ByteBuffer byteBuffer = ByteBuffer.allocate(ints.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = byteBuffer.asIntBuffer();

        print(intBuffer, "1");

        intBuffer.put(ints2);

        print(intBuffer, "2");
        intBuffer.flip();
        int value0 = intBuffer.get(); //get一次 position就会+1
        print(intBuffer, "3");
        int value1 = intBuffer.get();

        print(intBuffer, "4");
    }

    private static void print(IntBuffer intBuffer, String op) {
        int capacity = intBuffer.capacity();
        int limit = intBuffer.limit();
        int position = intBuffer.position();
        System.out.println(op + " capacity=" + capacity + ", limit=" + limit + ", position=" + position);
    }

}
