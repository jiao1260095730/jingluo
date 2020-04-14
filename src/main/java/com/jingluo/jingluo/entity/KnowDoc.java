package com.jingluo.jingluo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class KnowDoc {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.id
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.doc_id
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer docId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.base_id
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer baseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.dir_id
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer dirId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.doc_title
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private String docTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.read_num
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer readNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.yest_num
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer yestNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.update_time
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.collect_num
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer collectNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.att_num
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private Integer attNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.is_delete
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private String isDelete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.reserved_1
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private String reserved1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.reserved_2
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private String reserved2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.reserved_3
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private String reserved3;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column know_doc.doc_article
     *
     * @mbggenerated Sun Apr 12 14:02:13 CST 2020
     */
    private String docArticle;
}