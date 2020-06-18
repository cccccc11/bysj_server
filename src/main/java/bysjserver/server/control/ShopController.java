package bysjserver.server.control;

import bysjserver.server.bean.Commodity;
import bysjserver.server.bean.Shop;
import bysjserver.server.service.CommodityService;
import bysjserver.server.service.OrderService;
import bysjserver.server.service.ShopService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    OrderService orderService;

    @Autowired
    ShopService shopService;

    @RequestMapping("/goods/add.do")
    public String addGoods(String jsonStr, HttpServletRequest request){
        JSONObject json = JSON.parseObject(jsonStr);
        Commodity commodity = new Commodity();
        Shop shop = (Shop) request.getSession().getAttribute("shop");
        commodity.setShopId(shop.getId());
        commodity.setName(json.getString("name"));
        commodity.setNumber(json.getInteger("number"));
        commodity.setPrice(json.getDouble("price"));
        commodity.setStartDate(json.getSqlDate("startDate"));
        commodity.setEndDate(json.getSqlDate("endDate"));
        commodity.setIntroduce(json.getString("introduce"));
        commodity.setState(json.getString("state"));

        //获取照片路径
        commodity.setPhotosPath(json.getString("path"));

        json = new JSONObject();
        if(commodityService.addCommodity(commodity))
        {
            json.put("result","1");
            json.put("message","添加商品成功");
        }else {
            json.put("result",0);
            json.put("message","添加商品失败");
        }
        return json.toJSONString();
    }

    @RequestMapping("/goods/uploadPicture.do")
    public String uploadPicture(MultipartFile file, HttpServletRequest request) throws IOException {
        String uuid = request.getHeader("uuid");
        String fileName = file.getOriginalFilename();// 获取到上传文件的名字
        // file.getSize();获取到上传文件的大小
        //File dir = new File(path, fileName);
        //File dir = new File("src/main/resources/commodityPictures/"+uuid, fileName);
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        File dir = new File("D://BYSJ//pictures//commodities//"+uuid, fileName);
       // File dir = new File("D://BYSJ//commoditys//"+uuid,fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String directory = dir.getCanonicalPath();
        System.out.println("directory:" + directory);
        // MultipartFile自带的解析方法
        file.transferTo(dir);
        return "/upload" + "/" + fileName;

    }

    @Transactional
    @RequestMapping("/goods/getAllCommodity.do")
    public String getAllCommodity(HttpServletRequest request){
        Shop shop = (Shop) request.getSession().getAttribute("shop");
        String shopId = shop.getId();

        JSONArray jsonArray = new JSONArray();
        List<Commodity> commodityList = commodityService.getAllCommodity(shopId);
        for(Commodity commodity :commodityList)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",commodity.getName());
            jsonObject.put("price",commodity.getPrice());
            jsonObject.put("num",commodity.getNumber());
            jsonObject.put("commodityId",commodity.getId());
            jsonObject.put("state",commodity.getState());
            int numNow = orderService.commodityNum(commodity.getId());
            //int numNow = orderService.commodityNum(10);
            jsonObject.put("numNow",numNow);
            //获取图片
            File file = new File("D://BYSJ//pictures//commodities//"+commodity.getPhotosPath());
            if(!file.exists())
            {
                jsonObject.put("photosPath",null);
            }
            else {
                String[] paths = new String[file.list().length];
                for(int i =0;i<file.list().length;i++){
                    paths[i] = commodity.getPhotosPath()+"/"+file.list()[i];
                }
                jsonObject.put("photosPath",paths);
            }
            jsonArray.add(jsonObject);
        }

        return jsonArray.toJSONString();
    }

    @RequestMapping("/goods/uploadHead.do")
    public String uploadHead(MultipartFile file, HttpServletRequest request) throws IOException {
        // file.getSize();获取到上传文件的大小
        //File dir = new File(path, fileName);
        //File dir = new File("src/main/resources/commodityPictures/"+uuid, fileName);
        String shopId = ((Shop)request.getSession().getAttribute("shop")).getId();
        File dir = new File("D://BYSJ//pictures//head","shop"+shopId+".jpg");
        // File dir = new File("D://BYSJ//commoditys//"+uuid,fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String directory = dir.getCanonicalPath();
        System.out.println("directory:" + directory);
        // MultipartFile自带的解析方法
        file.transferTo(dir);


        shopService.changeHead(shopId,"shop"+shopId+".jpg");


        return "";
    }

    @RequestMapping("/info/changeName.do")
    public String changeName(String jsonStr,HttpServletRequest request){
        String shopId = ((Shop)request.getSession().getAttribute("shop")).getId();
        JSONObject object = JSON.parseObject(jsonStr);
        String newName = object.getString("newName");
        boolean i = shopService.changeName(shopId,newName);
        if(i){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/info/changeAddress.do")
    public String changeAddress(String jsonStr,HttpServletRequest request){
        String shopId = ((Shop)request.getSession().getAttribute("shop")).getId();
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String newAddress = jsonObject.getString("newAddress");
        if(shopService.changeAddress(shopId,newAddress)){
            return "1";
        }else {
            return "-1";
        }

    }

    @RequestMapping("/info/changePhone.do")
    public String changePhone(String jsonStr,HttpServletRequest request){
        String newPhone = JSON.parseObject(jsonStr).getString("newPhone");
        String shopId = ((Shop)request.getSession().getAttribute("shop")).getId();

        if(shopService.changePhone(shopId,newPhone)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/info/changePassword.do")
    public String changePassword(String jsonStr,HttpServletRequest request){
        String newPassword = JSON.parseObject(jsonStr).getString("newPassword");
        String shopId = ((Shop)request.getSession().getAttribute("shop")).getId();
        if(shopService.changePassword(shopId,newPassword)){
            return "1";
        }else {
            return "-1";
        }
    }

    @RequestMapping("/goods/getAllOrdersByCommodityId")
    public String getAllOrdersByCommodityId(String jsonStr,HttpServletRequest request){
        request.getSession().getId();
        Integer commodityId = Integer.parseInt(jsonStr);

        List<Map<String,Object>> results = orderService.getAllOrdersByCommodityId(commodityId);

        JSONArray resultsArray = new JSONArray();
        for(Map<String,Object> order:results){
            JSONObject orderJson = new JSONObject();
            orderJson.put("userId",order.get("id"));
            orderJson.put("phone",order.get("phone"));
            orderJson.put("name",order.get("name"));
            orderJson.put("address",order.get("address"));
            orderJson.put("num",order.get("num"));
            orderJson.put("state",order.get("state"));
            resultsArray.add(orderJson);
        }
        return resultsArray.toJSONString();
    }

    @RequestMapping("/goods/updateOrderState")
    public String updateOrderState(String jsonStr, HttpServletRequest request){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        String userId = requestJson.getString("userId");
        String state = requestJson.getString("state");
        Integer commodityId = requestJson.getInteger("commodityId");
        if(orderService.updateOrderState(commodityId,userId,state)){
            return "1";
        }else {
            return "0";
        }
    }

    @Transactional
    @RequestMapping("/goods/finishOrder")
    public String finishOrder(String jsonStr, HttpServletRequest request){
        request.getSession().getId();
        JSONObject requestJson = JSON.parseObject(jsonStr);
        Integer commodityId = requestJson.getInteger("commodityId");
        if(orderService.finishOrder(commodityId)==0){
            if(commodityService.updateCommodityState(commodityId,"3"))
            {
                return "1";
            }
            return "0";
        }else {
            return "0";
        }
    }
}
