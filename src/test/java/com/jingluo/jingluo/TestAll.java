package com.jingluo.jingluo;

import com.jingluo.jingluo.dto.Page;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @Description 综合测试类
 * @Author 焦斌
 * @Date 2020/5/24 20:48
 */
public class TestAll {

    //测试分页查询中分页功能
    @Test
    public void testPage() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 108; i++) {
            strings.add(i+"");
        }
        Page page = new Page();
        page.setCurrentPage(5);
        //每页的开始数
        page.setStar((page.getCurrentPage() - 1) * page.getPageSize() + 1);
        //list的大小
        int count = strings.size();
        //设置总页数
        page.setTotalPage(count % 10 == 0 ? count / 10 : count / 10 + 1);
        //对list进行截取
        page.setDataList(strings.subList(page.getStar()
                , count - page.getStar() > page.getPageSize() ? page.getStar() + page.getPageSize() : count));

        System.out.println(page);
    }
}
