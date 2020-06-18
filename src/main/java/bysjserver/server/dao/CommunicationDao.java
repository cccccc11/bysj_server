package bysjserver.server.dao;

import bysjserver.server.bean.Comment;
import bysjserver.server.bean.Communication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommunicationDao {

    Integer addCommunication(Communication communication);

    List<Map<String,Object>> getAllCommunication();

    //获取评论数量
    Integer getCommentNum(Integer communicationId);

    //根据communicationID和userID获取communication和user表信息
    Map<String,Object> getCommunication(@Param("userId")String userId,@Param("communicationId")Integer communication);

    //获取所有评论
    List<Map<String,Object>> getAllComment(Integer communicationId);

    //添加评论
    Integer addComment(Comment comment);
}
