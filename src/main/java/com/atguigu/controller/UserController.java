package com.atguigu.controller;


import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("login")
    public Result login(@RequestBody  User user){
        Result result = userService.login(user);
        return  result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result=  userService.getUserInfoByToken(token);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(@RequestParam String username){
        Result result = userService.checkUserName(username);
        return  result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user) {
       Result result =  userService.regist(user);
       return result;
    }

    @GetMapping("checkLogin")
    public  Result checkLogin(@RequestHeader String token){
        if (jwtHelper.isExpiration(token)|| StringUtils.isEmptyOrWhitespaceOnly(token)) {
            return  Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return  Result.ok(null);
    }
}
