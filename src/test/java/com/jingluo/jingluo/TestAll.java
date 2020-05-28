package com.jingluo.jingluo;

import com.alibaba.fastjson.JSONObject;
import com.jingluo.jingluo.common.LoggerCommon;
import com.jingluo.jingluo.dto.commondto.Page;
import com.jingluo.jingluo.dto.knowbasemodel.DirDocMsg;
import com.jingluo.jingluo.service.KnowBaseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    //测试字符串包含
    @Test
    public void testContains() {
        String name = "用户123123的知识库";
        String keyWord = "用的知识库";
        System.out.println(name.contains(keyWord));
    }

    //测试字符串解析
    @Test
    public void testStr() {
        String json = "{\"firstMenu\":\n" +
                "\t[\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"一级目录1\",\n" +
                "\t\t\t\"id\":\"1\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"一级目录2\",\n" +
                "\t\t\t\"id\":\"2\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\"secondMenu\":\n" +
                "\t[\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"二级目录1\",\n" +
                "\t\t\t\"id\":\"11\",\n" +
                "\t\t\t\"parentId\":\"1\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"二级目录2\",\n" +
                "\t\t\t\"id\":\"12\",\n" +
                "\t\t\t\"parentId\":\"2\"\t\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\"docs\":\n" +
                "\t[\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"二级目录1下的文档1\",\n" +
                "\t\t\t\"id\":\"111\",\n" +
                "\t\t\t\"parentId\":\"11\",\n" +
                "\t\t\t\"createTime\":\"2020-3-11 11:11\",\n" +
                "\t\t\t\"updateTime\":\"2020-3-12 12:12\",\n" +
                "\t\t\t\"author\":\"高黎明\",\n" +
                "\t\t\t\"authorId\":\"123\",\n" +
                "\t\t\t\"article\":\"文档内容11111111111111111111111111111111111111\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"二级目录2下的文档2\",\n" +
                "\t\t\t\"id\":\"112\",\n" +
                "\t\t\t\"parentId\":\"12\",\n" +
                "\t\t\t\"createTime\":\"2020-3-11 11:11\",\n" +
                "\t\t\t\"updateTime\":\"2020-3-12 12:12\",\n" +
                "\t\t\t\"author\":\"高黎明\",\n" +
                "\t\t\t\"authorId\":\"124\",\n" +
                "\t\t\t\"article\":\"文档内容22222222222222222222222222222222222\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
        DirDocMsg dirDocMsg = JSONObject.parseObject(json, DirDocMsg.class);
        LoggerCommon.info("转换后的实体：" + dirDocMsg);
    }
}
