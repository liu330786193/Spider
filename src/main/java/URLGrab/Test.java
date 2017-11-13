package URLGrab;

import javax.sound.midi.Soundbank;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by lyl on 2017/11/7.
 */
public class Test {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, IntrospectionException, InstantiationException {
        User user = new User();
        user.setId(1);
        user.setAge(11);
        user.setAddress("111");
        user.setDesc("23");
        user.setName("abc");
        System.out.println(BeanMapper.objectToMap(user));

        /*Map<String, Object> map = BeanMapper.objectToMap(user);
        map.put("id", "1111");
        long statTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++){
            System.out.println(BeanMapper.mapToObject(map, User.class));
        }
        System.out.println(System.currentTimeMillis() - statTime);*/
        System.out.println(user);
        UserDest userDest = BeanMapper.objectToObject(user, UserDest.class);

        System.out.println(userDest);


        /*long statTime1 = System.currentTimeMillis();
        for (int i = 0; i < 100; i++){
            System.out.println(BeanMapper.mapToObject1(map, User.class));
        }
        System.out.println(System.currentTimeMillis() - statTime1);
*/
    }

}
