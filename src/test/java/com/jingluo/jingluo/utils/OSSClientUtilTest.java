package com.jingluo.jingluo.utils;

import com.jingluo.jingluo.config.SystemConfig;
import com.jingluo.jingluo.exception.MyException;
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
    public void testUpload() throws IOException, MyException {
        String filePath = "C:\\Users\\DELL\\Pictures\\阳\\QQ图片20200311122240.jpg";
        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        String upload = OSSClientUtil.upload(filePath, data, 0);
        System.out.println(upload);
    }

    @Test
    public void test2() throws IOException, MyException {
        String fileName = "u=3647899918,3120450478&fm=26&gp=0.jpg";
        String filePath = "C:\\Users\\DELL\\Pictures\\CameraRoll\\u=3647899918,3120450478&fm=26&gp=0.jpg";
        InputStream in = new FileInputStream(filePath);
        byte[] data = toByteArray(in);
        in.close();

        String upload = OSSClientUtil.upload(fileName, filePath.getBytes(), SystemConfig.OSS_IMG_OBJECT_NAME);
        System.out.println(upload);

    }

    @Test
    public void testStr() {

    }
}
