package bysjserver.server.service;

import bysjserver.server.bean.Shop;
import bysjserver.server.dao.ShopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired(required = false)
    ShopDao shopDao;

    //团长是否存在
    public boolean hasShop(String id)
    {
        return shopDao.hasShop(id) != null && !shopDao.hasShop(id).equals("");
    }

    //添加团长
    public boolean addShop(String id, String password){
        return shopDao.addShop(id, password) > 0;
    }
    public boolean addShop(String id, String password,String phone){
        return shopDao.addShopWithPhone(id, password,phone) > 0;
    }

    //团长登录
    public Shop shopLogin(String id, String password)
    {
        return shopDao.shopLogin(id,password);
    }

    //修改团长名字
    public boolean changeName(String id, String newName){
        int i = shopDao.changeName(id,newName);
        return i > 0;
    }

    //修改团长地址
    public boolean changeAddress(String id, String newAddress){
        int i= shopDao.changeAddress(id,newAddress);
        return i > 0;
    }

    //修改团长手机
    public boolean changePhone(String id,String newPhone){
        return shopDao.changePhone(id,newPhone)>0;
    }

    //修改团长密码
    public boolean changePassword(String id,String newPassword){
        return shopDao.changePassword(id,newPassword)>0;
    }

    public void changeHead(String id, String path){
        shopDao.changeHead(id,path);
    }

    public String getPhone(String username){
        return shopDao.getPhone(username);
    }
}
