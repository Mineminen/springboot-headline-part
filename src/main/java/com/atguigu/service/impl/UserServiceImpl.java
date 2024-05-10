package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
* @author 10938
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-05-01 17:30:11
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private  UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(queryWrapper);

        Result r = null;
        if (loginUser == null) {
            r =  Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        } else if (StringUtils.isEmpty(user.getUserPwd())
                ||!((loginUser.getUserPwd()).equalsIgnoreCase(MD5Util.encrypt(user.getUserPwd())))) {
            r = Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }else{
           Map  data = new HashMap<>();
           String token = jwtHelper.createToken(loginUser.getUid().longValue());
           data.put("token",token);
           r = Result.ok(data);
        }

        return  r;
    }

    @Override
    public Result getUserInfoByToken(String token) {
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration ) {
            return  Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        if (user == null) {
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        user.setUserPwd("");
        Map<String,Object> data = new HashMap<>();
        data.put("loginUser",user);
        return Result.ok(data);
    }

    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        Long aLong = userMapper.selectCount(queryWrapper);
        if (aLong>0) {
            return  Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        return Result.ok(null);
    }

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);
        if (count>0) {
            return  Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
         userMapper.insert(user);
        return Result.ok(null);
    }
}




