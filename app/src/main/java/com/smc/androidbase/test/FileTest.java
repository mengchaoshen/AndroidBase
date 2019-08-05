package com.smc.androidbase.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class FileTest {

    public static void main(String[] args) {
        File file = new File("");
        file.listFiles(new MyFilenameFilter());
        byte[] b = new byte[1024];
        char[] cs = new char[1024];

        try {
            InputStream is = new FileInputStream(file);
            //InputStream读取数据的三种方式
            is.read();
            is.read(b);
            is.read(b, 0, 1024);

            Reader reader = new FileReader(file);
            //使用Reader读取数据的方式
            reader.read();
            reader.read(cs);
            reader.read(cs, 0, 1024);

            OutputStream os = new FileOutputStream(file);
            //OutputStream写入数据的三种方式
            os.write(1);
            os.write(b);
            os.write(b, 0, 1024);
            os.flush();

            Writer writer = new FileWriter(file);
            //使用Writer写入数据的五种方法
            writer.write(1);
            writer.write(cs);
            writer.write(cs, 0, 1024);
            writer.write("1");
            writer.write("1", 0, 1);
            writer.flush();

            writer.close();
            os.close();
            reader.close();
            is.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

    }

    /**
     * 文件过滤器
     */
    public static class MyFilenameFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith("mp4");
        }
    }
}
