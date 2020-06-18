package bysjserver.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {

    //获取某一商品的当前团购数量
    Integer commodityNum(Integer commodityId);

    //获取某一个商品总数量
    Integer commodityTotalNum(Integer commodityId);

    //判断是否加入过团购
    Integer hasPay(@Param("userId") String userId, @Param("commodityId") Integer commodityId);

    //加入团购
    Integer addOrder(@Param("userId") String userId, @Param("commodityId") Integer commodityId,@Param("num") Integer num);

    //按照userId获取所有订单
    List<Map<String,Object>> getOrders(@Param("userId")String userId);

    //删除订单
    Integer deleteOrder(@Param("userId") String userId, @Param("commodityId") Integer commodityId);

    //按照commodityId获取所有订单信息
    List<Map<String,Object>> getAllOrdersByCommodityId(Integer commodityId);

    //更改订单状态
    Integer updateOrderState(@Param("commodityId") Integer commodityId,@Param("userId") String userId,@Param("state")String state);

    //完成配送时判断是否还有为配送的用户
    Integer finishOrder(Integer commodityId);

}
