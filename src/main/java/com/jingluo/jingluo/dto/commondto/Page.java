package com.jingluo.jingluo.dto.commondto;

import lombok.Data;

import java.util.List;

/**
 * @Description TODO
 * @Author 焦斌
 * @Date 2020/5/24 20:36
 */
@Data
public class Page {
    //当前页 页码
    private Integer currentPage;
    //每页显示记录条数
    private int pageSize = 10;
    //总页数
    private int totalPage;
    //每页显示的数据
    private List<?> dataList;
    //开始数据
    private int star;
}
