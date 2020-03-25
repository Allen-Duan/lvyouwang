package cn.exrick.xboot.common.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ReflectUtil {
    private final static String nameFrefix = "set";

    public <T>  T mapConvertBean(Map<String, Object> map, T obj) throws Exception {
        /*
         * Class类是反射的入口 一般获得一个Class对象有三种途径 1.类属性方式，如String.class
         * 2.对象的getClass方法加载，如new String().getClass().
         * 3.forName方法加载，如Class.forName("java.lang.String") 用于动态加载 比如加载驱动
         * 这里我传入一个Object对象,所以我用的是第2种
         */
        Class classz = obj.getClass();
        // 得到传入实体类所有的方法(getXxx setXxx ...)
        // Method[] declaredMethods = classz.getDeclaredMethods();

        // 判断map集合参数不能为null
        if (!map.isEmpty()) {
            for (Map.Entry<String, Object> keyValue : map.entrySet()) {
                // 得到map键值
                String propertyName = keyValue.getKey();
                // 得到map-value值
                Object value = keyValue.getValue();
                // 得到回属性名
                Field field = getClassField(classz, propertyName);

                if (field != null) {
                    // 获取属性类型
                    Class<?> fieldType = field.getType();
                    value  = convertValType(value, fieldType);
                    Method method = null;
                        // 得到属性set方法名
                        String setMethodName = convertKey(propertyName);
                        //得到方法
                        method = classz.getMethod(setMethodName, field.getType());
                        //判断是否能够执行（这个可以不要）
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(obj, value);

                }
            }

        }
        return obj;
    }

    /**
     * DuanShiHao
     * 判断BigDecimal类型不为负数
     * @param num
     * @return
     */
    public boolean isOne(BigDecimal num[]){
        return Arrays.stream(num).filter(bigDecimal1 -> !"".equals(bigDecimal1) && bigDecimal1 != null).anyMatch(bigDecimal1 -> "1".equals(String.valueOf(new BigDecimal("0.00").setScale(2).compareTo(bigDecimal1))));
    }

    /**
     * Description: 去除表中公共字段
     * @return: void
     * @param:  map
     * @author: dsh
     */
    public void removeMap(Map map){
        map.remove("id");
        map.remove("createTime");
        map.remove("createBy");
        map.remove("updateTime");
        map.remove("updateBy");
        map.remove("delFlag");
    }

    /**
     * 注意：转化map集合的key 例如 属性名 xXxx(tNode)类型
     *
     * @return
     */
    public  String convertKey(String propertyName) {
        // 将属性名第一个字母大写然后进行拼接
        String setMethodName = nameFrefix.concat(propertyName.substring(0, 1).toUpperCase().concat(propertyName.substring(1)));
        return setMethodName;
    }

    /**
     * 得到属性名
     *
     * @param clazz
     *            类
     * @param fieldName
     *            属性名
     * @return
     */
    private  Field getClassField(Class<?> clazz, String fieldName) {
        // 传入类是Object类型或者是null直接return
        if (clazz == null || Object.class.getName().equals(clazz.getName())) {
            return null;
        }
        //获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        //获得父类递归
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {// 简单的递归一下
            return getClassField(superClass, fieldName);
        }
        return null;
    }

    /**
     * 将Object类型的值，转换成bean对象属性里对应的类型值
     *
     * @param value  Object对象值
     * @param fieldType 属性的类型
     * @return 转换后的值
     */
    private  Object convertValType(Object value, Class<?> fieldType) {
        Object retVal = null;
        if(value == null){
            return null;
        }else if ("".equals(value.toString().trim())){
            return null;
        }

        if (Long.class.getName().equals(fieldType.getName()) || long.class.getName().equals(fieldType.getName())) {
            retVal = Long.parseLong(value.toString());
        } else if (Integer.class.getName().equals(fieldType.getName())
                || int.class.getName().equals(fieldType.getName())) {
            retVal = Integer.parseInt(value.toString());
        } else if (Float.class.getName().equals(fieldType.getName())
                || float.class.getName().equals(fieldType.getName())) {
            retVal = Float.parseFloat(value.toString());
        } else if (Double.class.getName().equals(fieldType.getName())
                || double.class.getName().equals(fieldType.getName())) {
            retVal = Double.parseDouble(value.toString());
        } else if (Boolean.class.getName().equals(fieldType.getName())
                || boolean.class.getName().equals(fieldType.getName())) {
            retVal = Boolean.parseBoolean(value.toString());
        } else if (Character.class.getName().equals(fieldType.getName())
                || char.class.getName().equals(fieldType.getName())) {
            retVal = value;
        } else if(Date.class.getName().equals(fieldType.getName())){
            retVal = strConvertDate(value.toString());
        } else if(String.class.getName().equals(fieldType.getName())){
            retVal = value+"";
        }else if(Timestamp.class.getName().equals(fieldType.getName())){
            retVal = Timestamp.valueOf(value.toString());
        }else if(BigDecimal.class.getName().equals(fieldType.getName())){
            retVal = new BigDecimal(value.toString());
        }
        return retVal;
    }


    /**
     * String类型转Date
     * @param
     * @return
     */
    public  Date strConvertDate(String dateStr){
        Date parse = null;
        if(dateStr != null) {
            SimpleDateFormat format = null;
            if (dateStr.length()<="2000-00-00".length()){
                format = new SimpleDateFormat("yyyy-MM-dd");
            }else{
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            if (!"".equals(dateStr)) {
                try {
                    parse = format.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return parse;
    }

    /**
     * 将对象属性转化为map结合
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * Description: 判断map中有此属性并且不为空
     *       null = false
     * @return: boolean
     * @param: map，str
     * @author: dsh
     */
    public boolean mapIsNotEmpty(Map paramMap,Object paramStr){
        return paramMap.containsKey(paramStr)  && !StringUtils.isEmpty(paramStr);
    }

}
