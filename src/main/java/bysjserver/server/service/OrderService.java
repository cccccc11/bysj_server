package bysjserver.server.service;

import bysjserver.server.dao.OrderDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired(required = false)
    OrderDao orderDao;

    public Integer commodityNum(Integer commodityId){
        Integer re = orderDao.commodityNum(commodityId);
        if(re==null)
        {
            return 0;
        }
        return re;
    }

    public boolean hasPay(String userId, Integer commodityId){
        Integer temp = orderDao.hasPay(userId,commodityId);
        if(temp==null||temp==0){
            return false;
        }else {
            return true;
        }
    }

    public boolean addOrder(String userId, Integer commodityId,Integer num){
        if(orderDao.addOrder(userId,commodityId,num)>0){
            return true;
        }else {
            return false;
        }
    }

    public List<Map<String,Object>> getOrders(String userId){
        return orderDao.getOrders(userId);
    }

    public boolean deleteOrder(String userId,Integer commodityId){
        if(orderDao.deleteOrder(userId,commodityId)>0){
            return true;
        }else {
            return false;
        }
    }

    public Integer commodityTotalNum(Integer commodityId){
        return orderDao.commodityTotalNum(commodityId);
    }

    public List<Map<String,Object>> getAllOrdersByCommodityId(Integer commodityId){
        return orderDao.getAllOrdersByCommodityId(commodityId);
    }
    public boolean updateOrderState(Integer commodityId,String userId,String state){
        return orderDao.updateOrderState(commodityId,userId,state)>0;
    }
    public Integer finishOrder(Integer commodityId){
        return orderDao.finishOrder(commodityId);
    }

}
