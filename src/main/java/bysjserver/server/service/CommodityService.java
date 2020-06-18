package bysjserver.server.service;

import bysjserver.server.bean.Commodity;
import bysjserver.server.dao.CommodityDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityService {

    @Autowired(required = false)
    CommodityDao commodityDao;

    //添加商品
    public boolean addCommodity(Commodity commodity)
    {
        if(commodityDao.addCommodity(commodity)>0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    //获取团长的所有商品
    public List<Commodity> getAllCommodity(String shopId){
        return commodityDao.getAllCommodity(shopId);
    }

    public boolean updateCommodityState(Integer commodityId, String state){
        if(commodityDao.updateCommodityState(commodityId,state)>0){
            return true;
        }else {
            return false;
        }

    }
}
