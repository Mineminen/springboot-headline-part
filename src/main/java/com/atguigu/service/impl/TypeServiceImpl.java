package com.atguigu.service.impl;

import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Type;
import com.atguigu.service.TypeService;
import com.atguigu.mapper.TypeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 10938
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-05-01 17:30:11
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Resource
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {

        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }


}




