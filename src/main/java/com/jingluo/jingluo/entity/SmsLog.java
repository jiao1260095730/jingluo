package com.jingluo.jingluo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SmsLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_log.id
     *
     * @mbggenerated Sun Apr 12 16:01:07 CST 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_log.rec_phone
     *
     * @mbggenerated Sun Apr 12 16:01:07 CST 2020
     */
    private String recPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_log.code
     *
     * @mbggenerated Sun Apr 12 16:01:07 CST 2020
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_log.info
     *
     * @mbggenerated Sun Apr 12 16:01:07 CST 2020
     */
    private String info;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_log.type
     *
     * @mbggenerated Sun Apr 12 16:01:07 CST 2020
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_log.flag
     *
     * @mbggenerated Sun Apr 12 16:01:07 CST 2020
     */
    private Integer flag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sms_log.send_time
     *
     * @mbggenerated Sun Apr 12 16:01:07 CST 2020
     */
    private Date sendTime;
}