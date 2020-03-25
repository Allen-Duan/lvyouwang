package cn.exrick.xboot.common.utils;

import cn.exrick.xboot.common.encoder.BASE64Decoder;
import cn.exrick.xboot.common.encoder.BASE64Encoder;
import org.springframework.stereotype.Component;

import java.io.*;


/**
 ClassName:Base64Url<br/>
 Description:<br/>
 date:2020-03-25 10:39<br/>
 @author allen<br/>
 **/
public class Base64Url {
    /**
     * 将base64编码字符串转换为图片
     * @param imgStr: base64编码字符串
     * @param path:   图片路径-具体到文件
     * @return
     */
    public static boolean getImage(String imgStr, String path){
        if (imgStr == null){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @return
     * 需要注意的是，一般插件返回的base64编码的字符串都是有一个前缀的:"data:image/jpeg;base64," , 解码之前这个得去掉。
     */
    public static String getbase64Url(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
