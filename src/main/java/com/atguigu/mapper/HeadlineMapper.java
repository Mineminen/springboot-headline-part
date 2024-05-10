package com.atguigu.mapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.HeadlineVo;
import com.atguigu.pojo.vo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author 10938
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-05-01 17:30:11
* @Entity com.atguigu.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    List<HeadlineVo> findNewsPage( PortalVo portalVo);

    IPage<Map> selectMyPage(IPage page,@Param("portalVo") PortalVo portalVo);

   Map showHeadlineDetail(Integer hid);
}




