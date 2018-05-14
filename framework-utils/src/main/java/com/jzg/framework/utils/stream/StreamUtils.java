package com.jzg.framework.utils.stream;

import java.io.*;

/**
 * StreamUtils
 */
public final class StreamUtils {
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Stream转化为字符串
     * @param inputStream
     * @return
     */
    public static String convertStreamToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return new String(sb.toString().getBytes(), DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
