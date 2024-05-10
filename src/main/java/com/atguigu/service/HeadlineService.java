package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 10938
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-05-01 17:30:11
*/
public interface HeadlineService extends IService<Headline> {

    /**
     * 分页查询。返回页码数、页大小、总页数、总记录数、当前页信息，并根据时间降序，浏览量降序排序
     * @return
     */
    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);

    /**
     * 头条发布
     * @param token
     * @param headline
     * @return
     */
    Result publish(String token, Headline headline);


    /**
     * 头条回显
     * @param hid
     * @return
     */
    Result findHeadlineByHid(Integer hid);

    /**
     * 头条修改实现
     * @param headline
     * @return
     */

    Result update(Headline headline);
}
