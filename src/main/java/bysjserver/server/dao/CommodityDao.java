package bysjserver.server.dao;

import bysjserver.server.bean.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommodityDao {

    //添加商品
    public Integer addCommodity(Commodity commodity);

    //获取商家所有的商品信息
    List<Commodity> getAllCommodity(String shopId);

    //转换商品状态
    Integer updateCommodityState(@Param("commodityId") Integer commodityId,@Param("state") String state);
}
