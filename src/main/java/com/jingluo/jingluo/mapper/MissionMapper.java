package com.jingluo.jingluo.mapper;

import com.jingluo.jingluo.entity.Mission;

public interface MissionMapper {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mission
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int insert(Mission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mission
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int insertSelective(Mission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mission
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int updateByPrimaryKeySelective(Mission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mission
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    int updateByPrimaryKey(Mission record);
}