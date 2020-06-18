package bysjserver.server.dao;

import bysjserver.server.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    //用户是否存在
    String hasUser(String id);

    //添加用户
    Integer addUser(String id,String password);
    Integer addUserWithPhone(String id,String password,String phone);

    //登录
    User login(String id,String password);

    //修改用户名字
    Integer updateName(String id, String newName);
    //修改用户手机
    Integer updatePhone(String id, String newPhone);
    //修改用户地址
    Integer updateAddress(String id, String newAddress);
    //修改用户生日
    Integer updateBirthday(@Param("id") String id,@Param("newDate") Date newDate);
    //修改用户性别
    Integer updateSex(String id, String newSex);
    //修改用户密码
    Integer updatePassword(String id, String newPassword);
    //修改用户头像路径
    Integer updateHeadPath(String id, String path);

    //获取所有进行中的商品
    List<Map<String, Object>> getAllCommodities();

    //按照姓名搜索
    List<Map<String, Object>> searchCommodities(String searchStr);

    //获取手机号码
    String getPhoneNumber(String username);
}
