package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.Class;
public interface ClassMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int insert(Class record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int insertSelective(Class record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int updateByPrimaryKeySelective(Class record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table class
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int updateByPrimaryKey(Class record);

    //根据班级名称查询  班级信息
    Class selectById(String selectName);
}