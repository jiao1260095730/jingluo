package com.jingluo.jingluo.entity;

import lombok.Data;

@Data
public class StudentLink {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student_link.id
     *
     * @mbggenerated Tue May 05 21:51:04 CST 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student_link.student_code
     *
     * @mbggenerated Tue May 05 21:51:04 CST 2020
     */
    private String studentCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student_link.link_name
     *
     * @mbggenerated Tue May 05 21:51:04 CST 2020
     */
    private String linkName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column student_link.link_url
     *
     * @mbggenerated Tue May 05 21:51:04 CST 2020
     */
    private String linkUrl;
}