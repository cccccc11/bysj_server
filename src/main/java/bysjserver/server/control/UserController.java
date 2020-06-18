package bysjserver.server.control;

import bysjserver.server.bean.Comment;
import bysjserver.server.bean.Communication;
import bysjserver.server.bean.Shop;
import bysjserver.server.bean.User;
import bysjserver.server.dao.OrderDao;
import bysjserver.server.dao.ShoppingCartDao;
import bysjserver.server.service.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    CommunicationService communicationService;

    @Autowired
    CommodityService commodityService;

    @RequestMapping("/info/changeName.do")
    public String changeName(String jsonStr, HttpServletRequest request){
        String userId = ((User)request.getSession().getAttribute("user")).getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String newName = json.getString("newName");
        if(userService.updateName(userId,newName)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/info/changePhone.do")
    public String changePhone(String jsonStr, HttpServletRequest request){
        String userId = ((User)request.getSession().getAttribute("user")).getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String newPhone = json.getString("newPhone");
        if(userService.updatePhone(userId,newPhone)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/info/changeAddress.do")
    public String changeAddress(String jsonStr, HttpServletRequest request){
        String userId = ((User)request.getSession().getAttribute("user")).getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String newAddress = json.getString("newAddress");
        if(userService.updateAddress(userId,newAddress)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/info/changeBirthday.do")
    public String changeBirthday(String jsonStr, HttpServletRequest request){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        int year = requestJson.getInteger("year")-1900;
        int month = requestJson.getInteger("month");
        int day = requestJson.getInteger("day");
        String userId = requestJson.getString("userId");
        Date date = new Date(year,month,day);
        if(userService.updateBirthday(userId,date)){
            return "1";
        }
        return "0";
    }

    @RequestMapping("/info/changeSex.do")
    public String changeSex(String jsonStr, HttpServletRequest request){
        String userId = ((User)request.getSession().getAttribute("user")).getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String newSex = json.getString("newSex");
        if(userService.updateSex(userId,newSex)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/info/changePassword.do")
    public String changePassword(String jsonStr, HttpServletRequest request){
        String userId = ((User)request.getSession().getAttribute("user")).getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String newPassword = json.getString("newPassword");
        if(userService.updatePassword(userId,newPassword)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/info/uploadHead.do")
    public String uploadPicture(MultipartFile file, HttpServletRequest request) throws IOException {
        String userId = ((User) request.getSession().getAttribute("user")).getId();
        File dir = new File("D://BYSJ//pictures//head", "user" + userId + ".jpg");
        // File dir = new File("D://BYSJ//commoditys//"+uuid,fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String directory = dir.getCanonicalPath();
        System.out.println("directory:" + directory);
        // MultipartFile自带的解析方法
        file.transferTo(dir);

        userService.updateHeadPath(userId,"user" + userId + ".jpg");

        return "";
    }

    @RequestMapping("/info/getAllCommodities")
    public String getAllCommodities(HttpServletRequest request){
        request.getSession().getId();
        List<Map<String, Object>> commodities = userService.getAllCommodities();
        JSONArray array = new JSONArray();
        for(Map<String, Object> map:commodities){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shopName",map.get("shopname"));
            jsonObject.put("id",map.get("id"));
            jsonObject.put("shopId",map.get("shopId"));
            jsonObject.put("name",map.get("name"));
            jsonObject.put("number",map.get("number"));
            jsonObject.put("price",map.get("price"));
            jsonObject.put("startDate",map.get("startDate"));
            jsonObject.put("endDate",map.get("endDate"));
            jsonObject.put("state",map.get("state"));
            jsonObject.put("introduce",map.get("introduce"));

            File file = new File("D://BYSJ//pictures//commodities//"+map.get("photosPath"));
            if(!file.exists())
            {
                jsonObject.put("photosPath",null);
            }
            else {
                String[] paths = new String[file.list().length];
                for(int i =0;i<file.list().length;i++){
                    paths[i] = map.get("photosPath")+"/"+file.list()[i];
                }
                jsonObject.put("photosPath",paths);
            }
            int numNow = orderService.commodityNum((Integer) map.get("id"));
            jsonObject.put("numNow",numNow);
            array.add(jsonObject);
        }
        return array.toJSONString();
    }

    @RequestMapping("/main/search")
    public String search(HttpServletRequest request, String jsonStr){
        request.getSession().getId();

        List<Map<String, Object>> commodities = userService.searchCommodities(jsonStr);
        JSONArray array = new JSONArray();
        for(Map<String, Object> map:commodities){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shopName",map.get("shopname"));
            jsonObject.put("id",map.get("id"));
            jsonObject.put("shopId",map.get("shopId"));
            jsonObject.put("name",map.get("name"));
            jsonObject.put("number",map.get("number"));
            jsonObject.put("price",map.get("price"));
            jsonObject.put("startDate",map.get("startDate"));
            jsonObject.put("endDate",map.get("endDate"));
            jsonObject.put("state",map.get("state"));
            jsonObject.put("introduce",map.get("introduce"));

            File file = new File("D://BYSJ//pictures//commodities//"+map.get("photosPath"));
            if(!file.exists())
            {
                jsonObject.put("photosPath",null);
            }
            else {
                String[] paths = new String[file.list().length];
                for(int i =0;i<file.list().length;i++){
                    paths[i] = map.get("photosPath")+"/"+file.list()[i];
                }
                jsonObject.put("photosPath",paths);
            }
            int numNow = orderService.commodityNum((Integer) map.get("id"));
            jsonObject.put("numNow",numNow);
            array.add(jsonObject);
        }
        return array.toJSONString();

    }

    @Transactional
    @RequestMapping("/pay.do")
    public String pay(HttpServletRequest request, String jsonStr) throws Exception {
        request.getSession().getId();
        String userId;
        Integer commodityId;
        Integer num;
        JSONObject json = JSON.parseObject(jsonStr);
        userId = json.getString("userId");
        commodityId = json.getInteger("commodityId");
        num = json.getInteger("num");

        //团购已经加入
        if(hasPay(request,jsonStr).equals("1")){
            return "haspay";
        }


        //添加
        if(orderService.addOrder(userId,commodityId,num)){

            //如果加入过购物车，则删除购物车记录
            if(shoppingCartService.hasAddToShoppingCart(userId,commodityId)){
                shoppingCartService.deleteShoppingCart(userId,commodityId);
            }

            int numNow = orderService.commodityNum(commodityId);
            int numTotal = orderService.commodityTotalNum(commodityId);

            //判断数量
            if(numNow > numTotal){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "notEnough";
            }
            if(numNow == numTotal){
                //数量已经满了，转换商品状态
                commodityService.updateCommodityState(commodityId,"2");
            }

            return "1";
        }
        return "-1";
    }

    @RequestMapping("/hasPay.do")
    public String hasPay(HttpServletRequest request, String jsonStr){
        request.getSession().getId();
        String userId;
        Integer commodityId;
        JSONObject json = JSON.parseObject(jsonStr);
        userId = json.getString("userId");
        commodityId = json.getInteger("commodityId");
        if(orderService.hasPay(userId,commodityId)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/hasAddToShoppingCart.do")
    public String hasAddToShoppingCart(HttpServletRequest request, String jsonStr){
        request.getSession().getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String userId = json.getString("userId");
        Integer commodityId = json.getInteger("commodityId");
        if(shoppingCartService.hasAddToShoppingCart(userId,commodityId)){
            return "1";
        }
        return "-1";
    }

    @RequestMapping("/addToShoppingCart.do")
    public String addToShoppingCart(HttpServletRequest request, String jsonStr){
        request.getSession().getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String userId = json.getString("userId");
        Integer commodityId = json.getInteger("commodityId");
        Integer num = json.getInteger("num");
        if(!shoppingCartService.addToShoppingCart(userId,commodityId,num)){
            return "-1";
        }
        return "1";
    }

    @RequestMapping("/shoppingCart/getMyShoppingCart.do")
    public String getMyShoppingCart(HttpServletRequest request, String jsonStr){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");
        List<Map<String,Object>> commodities = shoppingCartService.getMyShoppingCart(userId);
        JSONArray array = new JSONArray();
        for(Map<String, Object> map:commodities){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("shopName",map.get("shopname"));
            jsonObject.put("id",map.get("id"));
            jsonObject.put("shopId",map.get("shopId"));
            jsonObject.put("name",map.get("name"));
            jsonObject.put("number",map.get("number"));
            jsonObject.put("price",map.get("price"));
            jsonObject.put("startDate",map.get("startDate"));
            jsonObject.put("endDate",map.get("endDate"));
            jsonObject.put("state",map.get("state"));
            jsonObject.put("introduce",map.get("introduce"));

            File file = new File("D://BYSJ//pictures//commodities//"+map.get("photosPath"));
            if(!file.exists())
            {
                jsonObject.put("photosPath",null);
            }
            else {
                String[] paths = new String[file.list().length];
                for(int i =0;i<file.list().length;i++){
                    paths[i] = map.get("photosPath")+"/"+file.list()[i];
                }
                jsonObject.put("photosPath",paths);
            }
            int numNow = orderService.commodityNum((Integer) map.get("id"));
            jsonObject.put("numNow",numNow);
            array.add(jsonObject);
        }
        return array.toJSONString();

    }

    @RequestMapping("/shoppingCart/deleteShoppingCart")
    public String deleteShoppingCart(HttpServletRequest request, String jsonStr){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");
        Integer commodityId = requestJson.getInteger("commodityId");

        if(shoppingCartService.deleteShoppingCart(userId,commodityId)){
            return "1";
        }else {
            return "0";
        }
    }

    @RequestMapping("/order/getOrders.do")
    public String getOrders(HttpServletRequest request,String jsonStr){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");

        List<Map<String,Object>> orders = orderService.getOrders(userId);

        JSONArray array = new JSONArray();
        for(Map<String, Object> map:orders){
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("num",map.get("num"));

            jsonObject.put("shopName",map.get("shopname"));
            jsonObject.put("id",map.get("id"));
            jsonObject.put("shopId",map.get("shopId"));
            jsonObject.put("name",map.get("name"));
            jsonObject.put("orderState",map.get("orderState"));
            jsonObject.put("number",map.get("number"));
            jsonObject.put("price",map.get("price"));
            jsonObject.put("startDate",map.get("startDate"));
            jsonObject.put("endDate",map.get("endDate"));
            jsonObject.put("state",map.get("state"));
            jsonObject.put("introduce",map.get("introduce"));

            File file = new File("D://BYSJ//pictures//commodities//"+map.get("photosPath"));
            if(!file.exists())
            {
                jsonObject.put("photosPath",null);
            }
            else {
                String[] paths = new String[file.list().length];
                for(int i =0;i<file.list().length;i++){
                    paths[i] = map.get("photosPath")+"/"+file.list()[i];
                }
                jsonObject.put("photosPath",paths);
            }
            int numNow = orderService.commodityNum((Integer) map.get("id"));
            jsonObject.put("numNow",numNow);
            array.add(jsonObject);
        }
        return array.toJSONString();
    }

    @RequestMapping("/order/deleteOrder")
    public String deleteOrder(HttpServletRequest request,String jsonStr){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");
        Integer commodityId = requestJson.getInteger("commodityId");

        if(orderService.deleteOrder(userId,commodityId)){
            return "1";
        }
        else {
            return "0";
        }
    }

    @RequestMapping("/communication/addCommunication")
    public String addCommunication(HttpServletRequest request,String jsonStr){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");
        String title = requestJson.getString("title");
        String content = requestJson.getString("content");
        Communication communication = new Communication();
        communication.setUserId(userId);
        communication.setTitle(title);
        communication.setContent(content);

        Integer communicationId = communicationService.addCommunication(communication);

        JSONObject resultJson = new JSONObject();

        if(communicationId>0){
            resultJson.put("result","1");
            resultJson.put("communicationId",communicationId);
        }
        else {
            resultJson.put("result","0");
            resultJson.put("message","添加失败");
        }

        return resultJson.toJSONString();
    }

    @RequestMapping("/communication/getAllCommunication")
    public String getAllCommunication(HttpServletRequest request){
        request.getSession().getId();
        List<Map<String,Object>> communicationInfo = communicationService.getAllCommunication();
        JSONArray resultArray = new JSONArray();
        for(Map<String,Object> communicationTemp : communicationInfo){
            JSONObject tempJson = new JSONObject();

            Integer communicationId = (Integer) communicationTemp.get("id");
            tempJson.put("communicationId",communicationId);
            tempJson.put("userId",communicationTemp.get("userId"));
            tempJson.put("date",communicationTemp.get("date"));
            tempJson.put("title",communicationTemp.get("title"));
            tempJson.put("content",communicationTemp.get("content"));
            tempJson.put("userName",communicationTemp.get("name"));
            tempJson.put("head",communicationTemp.get("head"));

            int num = communicationService.getCommentNum(communicationId);
            tempJson.put("num",num);

            resultArray.add(tempJson);
        }
        return resultArray.toJSONString();
    }

    @RequestMapping("/communication/getCommunication")
    public String getCommunication(HttpServletRequest request,String jsonStr){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");
        Integer communicationId = requestJson.getInteger("communicationId");

        Map<String,Object> result = communicationService.getCommunication(userId,communicationId);
        JSONObject resultJson = new JSONObject();
        resultJson.put("date",result.get("date"));
        resultJson.put("title",result.get("title"));
        resultJson.put("content",result.get("content"));
        resultJson.put("name",result.get("name"));
        resultJson.put("head",result.get("head"));
        return resultJson.toJSONString();
    }

    @RequestMapping("/communication/getAllComments")
    public String getAllComments(HttpServletRequest request,String jsonStr){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        Integer communicationId = requestJson.getInteger("communicationId");

        List<Map<String,Object>> results = communicationService.getAllComment(communicationId);
        JSONArray resultArray = new JSONArray();
        for(Map<String,Object> result : results){
            JSONObject comment = new JSONObject();
            comment.put("date",result.get("date"));
            comment.put("content",result.get("content"));
            comment.put("name",result.get("name"));
            comment.put("head",result.get("head"));
            resultArray.add(comment);
        }

        return resultArray.toJSONString();
    }

    @RequestMapping("/comment/addComment")
    public String addComment(HttpServletRequest request,String jsonStr){
        request.getSession().getId();

        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");
        String content = requestJson.getString("content");
        Integer communicationId = requestJson.getInteger("communicationId");

        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCommunicationId(communicationId);

        if(communicationService.addComment(comment)>0){
            return "1";
        }else {
            return "0";
        }

    }
}
