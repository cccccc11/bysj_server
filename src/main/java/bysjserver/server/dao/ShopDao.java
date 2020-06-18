package bysjserver.server.dao;

import bysjserver.server.bean.Commodity;
import bysjserver.server.bean.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopDao {

    //团长是否存在
    String hasShop(String id);

    //添加团长信息
    Integer addShop(String id,String password);
    Integer addShopWithPhone(String id,String password,String phone);

    //团长登录
    Shop shopLogin(String id, String password);

    //修改团长姓名
    Integer changeName(String id, String newName);

    //修改团长地址
    Integer changeAddress(String id, String newAddress);

    //修改团长手机
    Integer changePhone(String id,String newPhone);

    //修改团长密码
    Integer changePassword(String id,String newPassword);

    //修改团长头像路径
    Integer changeHead(String id, String path);

    //获得手机号
    String getPhone(String username);
}
