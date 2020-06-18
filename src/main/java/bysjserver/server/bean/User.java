package bysjserver.server.bean;

import java.sql.Date;

public class User {
    private String id;
    private String name;
    private String password;
    private String sex;
    private Date birthday;
    private String phone;
    private String address;
    private String head;
    private String state;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", head='" + head + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
