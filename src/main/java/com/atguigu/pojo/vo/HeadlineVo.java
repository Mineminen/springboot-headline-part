package com.atguigu.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class HeadlineVo {
    @TableId
    private Integer hid;

    private String title;

    private Integer type;

    private Integer publisher;

    private Integer pastHours;

}
