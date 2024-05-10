package com.atguigu.service.impl;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.atguigu.pojo.vo.HeadlineVo;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.mapper.HeadlineMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 10938
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-05-01 17:30:11
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Resource
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public Result findNewsPage(PortalVo portalVo) {
//       Page<Headline> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
//        headlineMapper.selectPage(page,null);
        Page<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectMyPage(page,portalVo);
        Map data = new HashMap<>();
        Map pageInfo = new HashMap();
        pageInfo.put("pageNum",page.getCurrent());
        pageInfo.put("pageSize",page.getSize());
        pageInfo.put("totalPage",page.getPages());
        pageInfo.put("totalSize",page.getTotal());
//        List<HeadlineVo> list = headlineMapper.findNewsPage(portalVo);
//        pageInfo.put("pageData",list);
        List<Map> records = page.getRecords();
        pageInfo.put("pageData",records);

        data.put("pageInfo",pageInfo);


        return Result.ok(data);
    }

    @Override
    public Result showHeadlineDetail(Integer hid) {

        Map headlineDetail= headlineMapper.showHeadlineDetail(hid);

        Headline headline = new Headline();
        headline.setHid(hid);
        //修改阅读量+1
        headline.setVersion((Integer) headlineDetail.get("version"));
        headline.setPageViews((Integer) headlineDetail.get("pageViews")+1);
        headlineMapper.updateById(headline);
        Map data = new HashMap<>();
        data.put("headline",headlineDetail);
        return Result.ok(data);
    }

    @Override
    public Result publish(String token, Headline headline) {
        if (jwtHelper.isExpiration(token)) {
            return  Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId= jwtHelper.getUserId(token).intValue();
       headline.setPublisher(userId);
       headline.setPageViews(0);
       headline.setCreateTime(new Date());
       headline.setUpdateTime(new Date());
        headlineMapper.insert(headline);
        return Result.ok(null);
    }

    @Override
    public Result findHeadlineByHid(Integer hid) {

        Headline headlineDetail = headlineMapper.selectById(hid);
        Map data = new HashMap<>();
        data.put("headline",headlineDetail);
        return Result.ok(data);
    }

    @Override
    public Result update(Headline headline) {

        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);
        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);
        return Result.ok(null);
    }
}




