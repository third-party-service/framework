package com.jzg.framework.hotswap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * HotswapClassLoader
 */
public class HotswapClassLoader extends ClassLoader {
    private String baseDir;

    public HotswapClassLoader(ClassLoader parent, String baseDir) {
        super(parent);
        this.baseDir = baseDir;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException{
        byte[] bytes = loadClassBytes(name);
        Class theClass = defineClass(name, bytes, 0, bytes.length); //A
        if (theClass == null)
            throw new ClassFormatError();
        return theClass;
    }


    protected URL findResource(String name) {
        try {
            URL url = super.findResource(name);
            if (url != null)
                return url;
            url = new URL("file:///" + converName(name));
            //简化处理，所有资源从文件系统中获取
            return url;
        } catch (MalformedURLException mue) {
            return null;
        }
    }

    private String converName(String name) {
        StringBuffer sb = new StringBuffer(baseDir);
        name = name.replace('.', File.separatorChar);
        sb.append(File.separator + name);
        return sb.toString();
    }


    private byte[] loadClassBytes(String className) throws
            ClassNotFoundException {
        try {
            String classFile = getClassFile(className);
            FileInputStream fis = new FileInputStream(classFile);
            FileChannel fileC = fis.getChannel();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WritableByteChannel outC = Channels.newChannel(baos);
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (true) {
                int i = fileC.read(buffer);
                if (i == 0 || i == -1) {
                    break;
                }
                buffer.flip();
                outC.write(buffer);
                buffer.clear();
            }
            fis.close();
            return baos.toByteArray();
        } catch (IOException fnfe) {
            throw new ClassNotFoundException(className);
        }
    }

    private String getClassFile(String name) {
        StringBuffer sb = new StringBuffer(baseDir);
        name = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + name);
        return sb.toString();
    }
}
