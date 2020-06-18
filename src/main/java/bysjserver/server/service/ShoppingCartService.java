package bysjserver.server.service;

import bysjserver.server.dao.ShoppingCartDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartService {

    @Autowired(required = false)
    ShoppingCartDao shoppingCartDao;

    public boolean hasAddToShoppingCart(String userId, Integer commodityId){
        Integer i = shoppingCartDao.hasAddToShoppingCart(userId,commodityId);
        if(i==null||i==0){
            return false;
        }else {
            return true;
        }
    }

    public boolean addToShoppingCart(String userId, Integer commodityId,Integer num){
        Integer i = shoppingCartDao.addToShoppingCart(userId,commodityId,num);
        if(i==null||i==0){
            return false;
        }
        return true;
    }

    public boolean deleteShoppingCart(String userId,Integer commodityId){
        if(shoppingCartDao.deleteShoppingCart(userId,commodityId)>0){
            return true;
        }else {
            return false;
        }
    }

    public List<Map<String, Object>> getMyShoppingCart(String userId){
        return shoppingCartDao.getMyShoppingCart(userId);
    }

}
