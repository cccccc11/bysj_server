package bysjserver.server.service;

import bysjserver.server.bean.Comment;
import bysjserver.server.bean.Communication;
import bysjserver.server.dao.CommunicationDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommunicationService {
    @Autowired(required = false)
    CommunicationDao communicationDao;

    public Integer addCommunication(Communication communication){
        communicationDao.addCommunication(communication);
        return communication.getId();
    }

    public List<Map<String,Object>> getAllCommunication(){
        return communicationDao.getAllCommunication();
    }

    public Integer getCommentNum(Integer communicationId){
        return communicationDao.getCommentNum(communicationId);
    }

    public Map<String,Object> getCommunication(String userId,Integer communication){
        return communicationDao.getCommunication(userId,communication);
    }

    public List<Map<String,Object>> getAllComment(Integer communicationId){
        return communicationDao.getAllComment(communicationId);
    }

    public Integer addComment(Comment comment){
        return communicationDao.addComment(comment);
    }

}
