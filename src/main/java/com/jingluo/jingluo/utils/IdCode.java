package com.jingluo.jingluo.utils;

import org.junit.Test;
import org.springframework.stereotype.Component;

@Component
public class IdCode {

    //获取id 截取后9位 并转化为 int类型
    public  int id(){
        IdGenerator idGenerator = new IdGenerator();
        long id = idGenerator.nextId();
        String s = String.valueOf(id);
        String ss = s.substring(s.length() - 9, s.length());
        int i = Integer.parseInt(ss);
        return i;
    }


}
