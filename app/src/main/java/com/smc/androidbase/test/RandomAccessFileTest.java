package com.smc.androidbase.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class RandomAccessFileTest {

    public final static String FILE_STR = "/Users/shenmengchao/Documents/arcvideo/log/test.txt";

    public static void main(String[] args) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(FILE_STR, "rw");
        raf.setLength(128);
        raf.close();

        String s1 = "a1";
        String s2 = "a2b";
        String s3 = "a3bc";
        String s4 = "a4bcd";
        String s5 = "a5bcde";

        new FileWriteThread(16 * 0, s1.getBytes()).start();
        new FileWriteThread(16 * 1, s2.getBytes()).start();
        new FileWriteThread(16 * 2, s3.getBytes()).start();
        new FileWriteThread(16 * 3, s4.getBytes()).start();
        new FileWriteThread(16 * 4, s5.getBytes()).start();

    }

    static class FileWriteThread extends Thread {

        private int skip;
        private byte[] content;

        public FileWriteThread(int skip, byte[] content) {
            this.skip = skip;
            this.content = content;
        }

        @Override
        public void run() {
            super.run();
            RandomAccessFile raf = null;
            try {
                raf = new RandomAccessFile(FILE_STR, "rw");
                raf.seek(skip);
                raf.write(content);
                System.out.println("写入成功");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
