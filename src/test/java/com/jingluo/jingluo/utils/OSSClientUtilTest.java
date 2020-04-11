package com.jingluo.jingluo.utils;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.google.common.io.ByteStreams.toByteArray;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/4/9 23:42
 */
public class OSSClientUtilTest {

    @Test
    public void testUpload() throws IOException {
        String filePath = "C:\\Users\\DELL\\Pictures\\阳\\QQ图片20200311122240.jpg";
        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        String upload = OSSClientUtil.upload(filePath, data, 0);
        System.out.println(upload);
    }
}
