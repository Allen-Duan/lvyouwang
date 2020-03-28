package cn.exrick.xboot.common.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.springframework.cglib.beans.BeanMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Exrickx
 */
public class ObjectUtil {

    public static String mapToString(Map<String, String[]> paramMap){

        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            String obj = StrUtil.endWithIgnoreCase(param.getKey(), "password") ? "你是看不见我的" : paramValue;
            params.put(key,obj);
        }
        return new Gson().toJson(params);
    }

    public static String mapToStringAll(Map<String, String[]> paramMap){

        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.put(key, paramValue);
        }
        return new Gson().toJson(params);
    }


    public static <T> Map<String, Object> beanToMapFormatDate(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Object key : beanMap.keySet()) {
                Object o = beanMap.get(key);
                if(o instanceof Date){
                    Date date = (Date)o;
                    String format = simpleDateFormat.format(date);
                    map.put(key+"", format);
                }else{
                    map.put(key+"", o);
                }
            }
        }
        return map;
    }
}
