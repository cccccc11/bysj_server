package bysjserver.server.service;

import bysjserver.server.bean.User;
import bysjserver.server.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired(required = false)
    private UserDao userDao;

    //判断用户是否存在
    public boolean hasUser(String id)
    {
        return userDao.hasUser(id) != null && !userDao.hasUser(id).equals("");
    }

    //添加用户
    public Integer addUser(String id,String password)
    {
        return userDao.addUser(id,password);
    }
    public Integer addUser(String id,String password,String phone)
    {
        return userDao.addUserWithPhone(id,password,phone);
    }

    //登录
    public User login(String id,String password)
    {
        return userDao.login(id,password);
    }

    //修改姓名
    public boolean updateName(String id, String newName){
        return userDao.updateName(id,newName)>0;
    }

    //修改手机
    public boolean updatePhone(String id, String newPhone){
        return userDao.updatePhone(id,newPhone)>0;
    }

    //修改地址
    public boolean updateAddress(String id, String newAddress){
        return userDao.updateAddress(id,newAddress)>0;
    }

    //修改生日
    public boolean updateBirthday(String id, Date newBirthday){
        return userDao.updateBirthday(id,newBirthday)>0;
    }

    //修改性别
    public boolean updateSex(String id, String newSex){
        return userDao.updateSex(id,newSex)>0;
    }

    //修改密码
    public boolean updatePassword(String id, String newPassword){
        return userDao.updatePassword(id,newPassword)>0;
    }

    public boolean updateHeadPath(String id, String path){
        return userDao.updateHeadPath(id,path)>0;
    }

    public List<Map<String, Object>> getAllCommodities(){
        return userDao.getAllCommodities();
    }

    public List<Map<String, Object>> searchCommodities(String searchStr){
        return userDao.searchCommodities(searchStr);
    }

    public String getPhoneNumber(String username){
        return userDao.getPhoneNumber(username);
    }
}
