/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
 * Create Author  : dsfan
 * Create Date    : 2016年9月6日
 * File Name      : AdHocResultMapperProvider.java
 */

package com.baifendian.swordfish.dao.mysql.mapper;

import com.baifendian.swordfish.common.job.FlowStatus;
import com.baifendian.swordfish.dao.mysql.mapper.utils.EnumFieldUtil;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * 即席查询结果 sql 生成器
 * <p>
 * 
 * @author : dsfan
 * @date : 2016年9月6日
 */
public class AdHocResultMapperProvider {
    /** table name */
    private static final String TABLE_NAME = "ad_hoc_results";

    /**
     * 生成插入语句
     * <p>
     *
     * @param parameter
     * @return sql语句
     */
    public String insert(Map<String, Object> parameter) {
        return new SQL() {
            {
                INSERT_INTO(TABLE_NAME);
                VALUES("exec_id", "#{adHocResult.execId}");
                VALUES("node_id", "#{adHocResult.nodeId}");
                VALUES("`index`", "#{adHocResult.index}");
                VALUES("stm", "#{adHocResult.stm}");
                VALUES("result", "#{adHocResult.result}");
                VALUES("status", EnumFieldUtil.genFieldStr("adHocResult.status", FlowStatus.class));
                VALUES("create_time", "#{adHocResult.createTime}");
            }
        }.toString();
    }

    /**
     * 生成更新语句
     * <p>
     *
     * @param parameter
     * @return sql语句
     */
    public String update(Map<String, Object> parameter) {
        return new SQL() {
            {
                UPDATE(TABLE_NAME);
                SET("result = #{adHocResult.result}");
                SET("status = " + EnumFieldUtil.genFieldStr("adHocResult.status", FlowStatus.class));
                WHERE("exec_id = #{adHocResult.execId}");
                WHERE("node_id = #{adHocResult.nodeId}");
                WHERE("`index` = #{adHocResult.index}");
            }
        }.toString();
    }

    /**
     * 生成查询语句
     * <p>
     *
     * @param parameter
     * @return sql语句
     */
    public String selectByExecIdAndNodeId(Map<String, Object> parameter) {
        return new SQL() {
            {
                SELECT("*");
                FROM(TABLE_NAME);
                WHERE("node_id = #{nodeId}");
                WHERE("exec_id = #{execId}");
            }
        }.toString();
    }

    /**
     * 生成查询语句
     * <p>
     *
     * @param parameter
     * @return sql语句
     */
    public String selectByExecIdAndFlowId(Map<String, Object> parameter) {
//        new SQL() {
//            {
//                SELECT("ad_hoc_results.index, max(create_time) create_time");
//                FROM(TABLE_NAME);
//                LEFT_OUTER_JOIN("flows_nodes as fn ON ad_hoc_results.node_id = fn.id");
//                WHERE("flow_id = #{flowId}");
//                WHERE("exec_id = #{execId}");
//                GROUP_BY("ad_hoc_results.index");
//            }
//        }.toString();

        return "SELECT a.* " +
                "FROM ad_hoc_results a, " +
                "  (SELECT ad_hoc_results.index, " +
                "          max(ad_hoc_results.create_time) create_time " +
                "   FROM ad_hoc_results " +
                "   LEFT OUTER JOIN flows_nodes AS fn ON ad_hoc_results.node_id = fn.id " +
                "   WHERE (flow_id = #{flowId} " +
                "          AND exec_id = #{execId}) " +
                "   GROUP BY ad_hoc_results.index)b " +
                "WHERE a.index = b.index " +
                "  AND a.create_time = b.create_time " +
                "ORDER BY a.index";
    }

    public String selectByExecIdAndFlowIdAndIndex(Map<String, Object> parameter) {
        return new SQL() {
            {
                SELECT("ad_hoc_results.*");
                FROM(TABLE_NAME);
                LEFT_OUTER_JOIN("flows_nodes as fn ON ad_hoc_results.node_id = fn.id");
                WHERE("flow_id = #{flowId}");
                WHERE("exec_id = #{execId}");
                WHERE("ad_hoc_results.index = #{index}");
                ORDER_BY("create_time DESC limit 1 ");
            }
        }.toString();
    }

    public static void main(String[] args) {
        System.out.println(new AdHocResultMapperProvider().selectByExecIdAndFlowIdAndIndex(null));
    }
}