package bysjserver.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShoppingCartDao {

    //判断是否加入过购物车
    Integer hasAddToShoppingCart(@Param("userId") String userId, @Param("commodityId") Integer commodityId);

    //加入购物车
    Integer addToShoppingCart(@Param("userId") String userId, @Param("commodityId") Integer commodityId,@Param("num") Integer num);

    //删除购物车
    Integer deleteShoppingCart(@Param("userId") String userId, @Param("commodityId") Integer commodityId);

    //获取用户的购物车信息
    public List<Map<String, Object>> getMyShoppingCart(@Param("userId") String userId);
}
