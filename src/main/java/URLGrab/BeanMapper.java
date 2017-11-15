package URLGrab;

import com.google.common.collect.Lists;

import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lyl on 2017/11/8.
 */
public class BeanMapper {

    /**
     * Comment is created by lyl on 2017/11/11 下午1:10.
     *
     *
     */
    public static Map<String, Object> objectToMap(Object obj) {
        final Class clazz = obj.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        try {
            final Map<String, Object> map = new HashMap<String, Object>();
            final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            Arrays.stream(fields).forEach(field -> {
                String fieldName = field.getName();
                Arrays.stream(propertyDescriptors)
                        .filter(pd -> (pd.getReadMethod() != null) && pd.getReadMethod().getName().equals("get" + upperFirstLetter(fieldName)))
                        .forEach(propertyDescriptor -> {
                            Method getter = propertyDescriptor.getReadMethod();
                            ObjectToMapField mapField = field.getAnnotation(ObjectToMapField.class);
                            try {
                                if (mapField == null){
                                    map.put(fieldName, getter.invoke(obj, null));
                                }else {
                                    map.put(mapField.name(), getter.invoke(obj, null));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        });
            });
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Comment is created by lyl on 2017/11/10 下午2:55.
     *
     * map转换成object
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        final Field[] fields = clazz.getDeclaredFields();
        try {
            T t = (T)clazz.newInstance();
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            Arrays.stream(fields).forEach(field -> {
                String fieldName = field.getName();
                Arrays.stream(propertyDescriptors)
                        .filter(pd -> (pd.getWriteMethod() != null) && pd.getWriteMethod().getName().equals("set" + upperFirstLetter(fieldName)))
                        .forEach(propertyDescriptor -> {
                            Method setter = propertyDescriptor.getWriteMethod();
                            MapToObjectField mapToObjectField = field.getAnnotation(MapToObjectField.class);
                            try {
                                if (mapToObjectField != null){
                                    setter.invoke(t, convertFieldValue(field, map.get(mapToObjectField.name()).toString()));
                                }else {
                                    setter.invoke(t, convertFieldValue(field, map.get(propertyDescriptor.getName()).toString()));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        });
            });
            return t;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Comment is created by lyl on 2017/11/10 下午5:20
     *
     * 转换字段类型
     */
    public static <T> T objectToObject(Object source, Class<T> destination){
        try {
            final T t = destination.newInstance();
            final Field[] sFields = source.getClass().getDeclaredFields();
            final PropertyDescriptor[] dpds = Introspector.getBeanInfo(destination).getPropertyDescriptors();
            Arrays.stream(sFields).forEach(sField -> {
                ObjectToObjectField objectToObjectField = sField.getAnnotation(ObjectToObjectField.class);
                Arrays.stream(dpds)
                        .filter(dpd -> {
                            if (dpd.getWriteMethod() == null){
                                return false;
                            }
                            String setterName = dpd.getWriteMethod().getName();
                            if (setterName.substring(3, setterName.length()).equals(upperFirstLetter(sField.getName()))
                                    || objectToObjectField != null
                                    && upperFirstLetter(objectToObjectField.name()).equals(setterName.substring(3, setterName.length()))
                                    ){
                                return true;
                            }
                            return false;
                        })
                        .forEach(des -> {
                            try {
                                String getterName = des.getWriteMethod().getName();
                                if (upperFirstLetter(sField.getName()).equals(getterName.substring(3, getterName.length()))
                                        || objectToObjectField != null
                                        && upperFirstLetter(objectToObjectField.name()).equals(getterName.substring(3, getterName.length()))){
                                    sField.setAccessible(true);
                                    des.getWriteMethod().invoke(t, new Object[]{sField.get(source)});
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

            });
            return t;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Comment is created by lyl on 2017/11/15 下午6:17.
     *
     * list之间相互映射
     */
    public static <T> List<T> listToList(List<Object> sourceList, Class<T> destinationClass){
        List list = Lists.newArrayList();
        sourceList.stream()
                .forEach(source -> {
                    list.add(objectToObject(source, destinationClass));
                });
        return list;
    }

    /**
     * Comment is created by lyl on 2017/11/10 下午3:25.
     *
     * 转换字段类型
     */
    private static Object convertFieldValue(Field field, String value) throws IllegalArgumentException, IllegalAccessException, ParseException {
        field.setAccessible(true);
        String fieldType = field.getType().getName();
        if (fieldType.equals("java.lang.String")) {
            return value;
        }
        if (fieldType.equals("java.lang.Integer") || fieldType.equals("int")) {
            return Integer.valueOf(value);
        }
        if (fieldType.equals("java.lang.Long") || fieldType.equals("long")) {
            return Long.valueOf(value);
        }
        if (fieldType.equals("java.lang.Float") || fieldType.equals("float")) {
            return Float.valueOf(value);
        }
        if (fieldType.equals("java.lang.Double") || fieldType.equals("double")) {
            return Double.valueOf(value);
        }
        if (fieldType.equals("java.util.Date")) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.parse(value);
        }
        if (fieldType.equals("java.math.BigDecimal")) {
            return new BigDecimal(value);
        }
        if (fieldType.equals("java.lang.Boolean") || fieldType.equals("boolean")) {
            return Boolean.valueOf(value);
        }
        if (fieldType.equals("java.lang.Byte") || fieldType.equals("byte")) {
            return Byte.valueOf(value);
        }
        if (fieldType.equals("java.lang.Short") || fieldType.equals("short")) {
            return Short.valueOf(value);
        }
        return null;
    }

    public static String upperFirstLetter(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

}
