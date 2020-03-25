package cn.exrick.xboot.common.utils;

import cn.exrick.xboot.common.exception.XbootException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Exrickx
 */
@Component
@Slf4j
public class FileUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

//    @Autowired
//    private FileManageService fileManageService;
//
//    @Autowired
//    private FileManageDao fileManageDao;

    @Value("${filePath}")
    private String filePath = "";


    /**
     * 文件路径上传
     * @param file
     * @param key
     * @return
     */
    public String localUpload(MultipartFile file, String key) {
        String path = "/Users/allen/Documents/Allen_Project/xboot-front-master/src/assets";
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File f = new File(path + "/" + key);
        if(f.exists()){
            throw new XbootException("文件名已存在");
        }
        try {
            file.transferTo(f);
            return "data:image/jpeg;base64,"+Base64Url.getbase64Url(path + "/" + key);
        } catch (IOException e) {
            log.error(e.toString());
            throw new XbootException("上传文件出错");
        }
    }


    /**
     * 读取文件
     * @param url
     * @param response
     */
    public void view(String url, HttpServletResponse response){

        File file = new File(url);
        FileInputStream i = null;
        OutputStream o = null;

        try {
            i = new FileInputStream(file);
            o = response.getOutputStream();

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = i.read(buf))>0){
                o.write(buf, 0, bytesRead);
                o.flush();
            }

            i.close();
            o.close();
        } catch (IOException e) {
            log.error(e.toString());
            throw new XbootException("读取文件出错");
        }
    }

    /**
     * 重命名
     * @param url
     * @param toKey
     * @return
     */
    public String renameFile(String url, String toKey){

        String result = copyFile(url, toKey);
        deleteFile(url);
        return result;
    }

    /**
     * 复制文件
     * @param url
     * @param toKey
     */
    public String copyFile(String url, String toKey){

        File file = new File(url);
        FileInputStream i = null;
        FileOutputStream o = null;

        try {
            i = new FileInputStream(file);
            o = new FileOutputStream(new File(file.getParentFile() + "/" + toKey));

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = i.read(buf))>0){
                o.write(buf, 0, bytesRead);
            }

            i.close();
            o.close();
            return file.getParentFile() + "/" + toKey;
        } catch (IOException e) {
            log.error(e.toString());
            throw new XbootException("复制文件出错");
        }
    }

    /**
     * 删除文件
     * @param url
     */
    public void deleteFile(String url){

        File file = new File(url);
        file.delete();
    }

    /**
     * 删除所有文件夹以及文件夹下的所有文件
     */
    public void deleteFileAll(String creditCode){
        String path = filePath +"/"+creditCode+"/";
        File file = new File(path);
        if(file.isFile()){
            file.delete();
        }else{
            File[] files = file.listFiles();
            if(files == null){
                file.delete();
            }else{
                for (int i = 0; i < files.length; i++){
                    deleteFileAll(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }
    }


    /**
     * 文件路径下载
     * @param filePath
     * @param response
     * @return
     */
    public void downLoad(String filePath, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        if (filePath != null) {
//            //设置文件路径
//            OssSetting oss = getOssSetting1();
//
//            String path = "";
//
//            if ("0".equals(accType)) {
//                path = oss.getFilePath() +licensePath;
//            }else if ("1".equals(accType)){
//                path = oss.getFilePath() +idcardUp;
//            }else{
//                path= oss.getFilePath();
//            }

            File file = new File(filePath);
            if (file.exists()) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
//                response.setContentType("application/force-download");// 设置强制下载不打开
//                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                response.addHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), StandardCharsets.ISO_8859_1));
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("下载成功");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                throw new XbootException("文件不存在");
            }
        }else{
            throw new XbootException("文件为空");
        }
    }

}
