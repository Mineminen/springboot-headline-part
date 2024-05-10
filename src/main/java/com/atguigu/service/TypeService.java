package com.atguigu.service;

import com.atguigu.pojo.Type;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 10938
* @description 针对表【news_type】的数据库操作Service
* @createDate 2024-05-01 17:30:11
*/
public interface TypeService extends IService<Type> {

    /**
     * 查询首页分类
     * @return
     */
    Result findAllTypes();


}
