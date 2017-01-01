package com.scut.controller;

import com.google.gson.*;
import com.scut.dto.*;
import com.scut.entity.*;
import com.scut.service.*;
import org.apache.log4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

import javax.annotation.*;
import javax.servlet.http.*;
import java.util.*;

/**
 * Created by pc on 2016/12/30.
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Resource
    private UserService userService;
    private Gson gson=new Gson();
//    处理用户注册
    @PostMapping(value = "/user")
    @ResponseBody
    public String insertUser(@RequestParam(value = "email")String email,
                             @RequestParam(value = "username")String username,
                             @RequestParam(value = "password1")String password1,
                             @RequestParam(value = "password2")String password2){
        System.out.print(email+" "+username+" "+password1+" "+password2);
        Message<String> message=userService.insertUser(email,username,password1,password2);
        return gson.toJson(message);
    }

//    处理用户登录
    @PostMapping(value = "/loginResult",produces = "application/json")
    @ResponseBody
    public String doLogin(@RequestBody User user, HttpSession session){
        Message<String> message=new Message<>();
        System.out.println(user);
        User user1=userService.doLogin(user);
        if(user1!=null){
            message.setFlag("success");
            session.setAttribute("user",user1);
        }else{
            message.setFlag("fail");
        }
        return gson.toJson(message);
    }

//    请求id用户信息
    @GetMapping(value = "/user/{id}")
    @ResponseBody
    public String showUser(@PathVariable("id")Integer id){
        Map<String,Object> map=new HashMap<>();
        User user=userService.findUserById(id);
        if(user!=null){
            map.put("result","success");
            user.setPassword(null);
            map.put("user",user);
//            添加回答数，提问数
        }else{
            map.put("result","fail");
        }
        return gson.toJson(map);
    }

    @GetMapping(value = "/u/{id}")
    public String showUserPage(@PathVariable("id")Integer uid){
       return "myAnswers";
    }
}
