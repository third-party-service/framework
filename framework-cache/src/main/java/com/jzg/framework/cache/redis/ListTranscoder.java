package com.jzg.framework.cache.redis;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Jedis does not support cache the Object directly, the Objects needed to be
 * serialized and de-serialized
 */
public class ListTranscoder<T extends Serializable> extends SerializeTranscoder {

    /**
     * 反序列化
     * @param in in
     * @return List
     */
    @SuppressWarnings("unchecked")
    public List<T> deserialize(byte[] in) {
        List<T> list = new ArrayList<T>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                while (true) {
                    T t = (T) is.readObject();
                    if (t == null) {
                        break;
                    }

                    list.add(t);

                }
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            logger.error(String.format("Caught IOException decoding %d bytes of data",
                    in == null ? 0 : in.length) + e);
        } catch (ClassNotFoundException e) {
            logger.error(String.format("Caught CNFE decoding %d bytes of data",
                    in == null ? 0 : in.length) + e);
        } finally {
            close(is);
            close(bis);
        }

        return list;
    }

    /**
     * 序列化List对象
     * @param value
     * @return 字节数组
     */
    @SuppressWarnings("unchecked")
    @Override
    public byte[] serialize(Object value) {
        if (value == null)
            throw new NullPointerException("Can't serialize null");

        List<T> values = (List<T>) value;

        byte[] results = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;

        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (T t : values) {
                os.writeObject(t);
            }

            os.writeObject(null);
            os.close();
            bos.close();
            results = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            close(os);
            close(bos);
        }

        return results;
    }


}