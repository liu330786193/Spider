package URLGrab;

/**
 * Created by lyl on 2017/11/8.
 */
public class UserDest {

    private long id;

    private Integer age;

    private String username;

    private String desc;


    private String test;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return "UserDest{" +
                "id=" + id +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", desc='" + desc + '\'' +
                ", test='" + test + '\'' +
                '}';
    }
}
