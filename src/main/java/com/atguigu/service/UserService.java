package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 10938
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-05-01 17:30:11
*/
public interface UserService extends IService<User> {

    Result login(User user);

    /**
     * 根据token获取用户详细信息
     * @param token
     * @return
     */
    Result getUserInfoByToken(String token);

    /**
     * 注册用户名检查
     * @param username
     * @return
     */
    Result checkUserName(String username);

    /**
     * 注册
     * @param user
     * @return
     */
    Result regist(User user);
}
