package com.jingluo.jingluo.utils;

import org.springframework.beans.factory.annotation.Autowired;

class IdGeneratorTest {
    public static void main(String[] args) {

        IdGenerator idGenerator = new IdGenerator();
        //705860311512514560
        System.out.println(idGenerator.nextId());
    }
}