package bysjserver.server.control;

import bysjserver.server.bean.Shop;
import bysjserver.server.bean.User;
import bysjserver.server.service.ShopService;
import bysjserver.server.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class Login {

    @Autowired
    UserService userService;

    @Autowired
    ShopService shopService;

    @RequestMapping("/login.do")
    public String login(String jsonStr, HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject json = JSON.parseObject(jsonStr);
        String id = json.getString("username");
        String password = json.getString("password");
        String type = json.getString("type");
        if(type.equals("user"))
        {
            User user = userService.login(id,password);
            json = new JSONObject();
            if(user!=null)
            {
                json.put("result","1");
                json.put("message","登录成功");
                json.put("name",user.getName());
                json.put("id",user.getId());
                json.put("password",user.getPassword());
                json.put("sex",user.getSex());

                if(user.getBirthday()!=null){
                    json.put("birthdayYear",user.getBirthday().getYear());
                    json.put("birthdayMonth",user.getBirthday().getMonth());
                    json.put("birthdayDay",user.getBirthday().getDay());
                    json.put("birthday",user.getBirthday().toString());
                }
                json.put("phone",user.getPhone());
                json.put("address",user.getAddress());
                json.put("headURL",user.getHead());
                request.getSession().setAttribute("user",user);
            }
            else
            {
                json.put("result","-1");
                json.put("message","用户名或密码错误");
                request.getSession().setAttribute("user",null);
            }
        }else {
            Shop shop = shopService.shopLogin(id,password);
            if(shop!=null)
            {
                json.put("result","1");
                json.put("message","登录成功");
                json.put("name",shop.getName());
                json.put("headURL",shop.getHead());
                json.put("phone",shop.getPhone());
                json.put("address",shop.getAddress());
                json.put("password",shop.getPassword());
                request.getSession().setAttribute("shop",shop);
            }
            else
            {
                json.put("result","-1");
                json.put("message","团长名或密码错误");
                request.getSession().setAttribute("shop",null);
            }
        }
        return json.toJSONString();
    }

    @RequestMapping("/hasShop.do")
    public String hasShop(String jsonStr,HttpServletRequest request)
    {
        request.getSession().getId();
        JSONObject json = JSON.parseObject(jsonStr);
        String id = json.getString("id");
        json = new JSONObject();
        if(shopService.hasShop(id))
        {
            json.put("result","1");
        }
        else {
            json.put("result","0");
        }
        return json.toJSONString();
    }

    @RequestMapping("/hasUser.do")
    public String hasUser(String jsonStr, HttpServletRequest request)
    {
        request.getSession().getId();
        JSONObject json = JSON.parseObject(jsonStr);
        if(userService.hasUser(json.getString("id")))
        {
            json = new JSONObject();
            json.put("result","1");
            return json.toJSONString();
        }
        else {
            json = new JSONObject();
            json.put("result","0");
            return json.toJSONString();
        }
    }

    @RequestMapping("/register.do")
    public String register(String jsonStr, HttpServletRequest request)
    {
        String sessionId = request.getSession().getId();
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String phoneNum = jsonObject.getString("phone");
        //String YZM = jsonObject.getString("YZM");

        JSONObject re = new JSONObject();
//        //判断验证码
//        String sessionCode = (String) request.getSession().getAttribute("captcha");
//        System.out.println(sessionCode);
//        if(!CaptchaUtil.ver(YZM,request))
//        {
//            re.put("state","-1");
//            re.put("message","验证码不正确！");
//            return re.toJSONString();
//        }
        if(jsonObject.getInteger("type")==1)
        {
            //添加注册用户
            int i = userService.addUser(username,password,phoneNum);
            if(i==0)
            {
                re.put("state","-1");
                re.put("message","添加失败");
            }else {
                re.put("state","1");
                re.put("message","注册用户成功，将返回登录界面");
            }
        }
        else {
            //添加商户信息
            if(!shopService.addShop(username,password,phoneNum))
            {
                re.put("state","-1");
                re.put("message","添加失败");
                return re.toJSONString();
            }else {
                re.put("state","1");
                re.put("message","注册团长成功，将返回登录界面");
            }
        }

        return re.toJSONString();
    }

    @RequestMapping("/test.do")
    public String test(String jsonStr,HttpServletRequest request){
        System.out.println(jsonStr);
        request.getSession();
        return "nihao";
    }

    @RequestMapping("/forget_password_getPhone")
    public String forget_password_getPhone(String jsonStr,HttpServletRequest request){
        request.getSession().getId();
        JSONObject requestJSON = JSON.parseObject(jsonStr);
        String username = requestJSON.getString("username");
        Integer userType = requestJSON.getInteger("userType");
        String phone = null;
        if(userType == 0){
            phone = userService.getPhoneNumber(username);
        }else {
            phone = shopService.getPhone(username);
        }
        if(phone==null||phone.equals("")){
            return "-1";
        }
        return phone;
    }

    @RequestMapping("/changePassword")
    public String changePassword(String jsonStr,HttpServletRequest request){
        request.getSession().getId();
        JSONObject requestJSON = JSON.parseObject(jsonStr);
        String username = requestJSON.getString("username");
        String password = requestJSON.getString("password");
        Integer userType = requestJSON.getInteger("userType");
        int i = -1;
        if(userType == 0){
            if(userService.updatePassword(username,password)){
                return "1";
            }
        }
        else {
            if(shopService.changePassword(username,password)){
                return "1";
            }
        }
        return "-1";
    }
}
