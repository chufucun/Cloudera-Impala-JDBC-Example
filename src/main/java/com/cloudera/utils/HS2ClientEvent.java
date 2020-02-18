package com.cloudera.utils;

import com.cloudera.impala.jdbc42.internal.apache.hive.service.rpc.thrift.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * HS2 客户端事件操作，注意：相关事件不要抛出异常，影响原有的流程
 *
 */
public class HS2ClientEvent {

    private Logger logger = LoggerFactory.getLogger(HS2ClientEvent.class);

    private static HS2ClientEvent instance;

    private HS2ClientEvent() {
    }

    public static HS2ClientEvent getInstance() {
        if (instance == null) {
            synchronized (HS2ClientEvent.class) {
                if (instance == null) {
                    instance = new HS2ClientEvent();
                }
            }
        }
        return instance;
    }

    /**
     * 添加执行sql事件
     *
     * @param req
     * @param resp
     */
    public void addExecuteStatement(TExecuteStatementReq req, TExecuteStatementResp resp) {
        String statement = req.getStatement();
        byte[] guid = req.getSessionHandle().getSessionId().getGuid();
        String sessionid = StringHelper.getGuid(guid);
        guid = resp.getOperationHandle().getOperationId().getGuid();
        String queryid = StringHelper.getGuid(guid);
        String msg = "ExecuteStatement: statement: " + statement + ", sessionid:" + sessionid +
                ", " +
                "queryid:" + queryid;
        logger.info(msg);
    }

    /**
     * 添加获取操作状态（sql执行状态）事件
     *
     * @param req
     * @param resp
     */
    public void addGetOperationStatus(TGetOperationStatusReq req, TGetOperationStatusResp resp) {
        byte[] guid = req.getOperationHandle().getOperationId().getGuid();
        String queryid = StringHelper.getGuid(guid);
        String msg = "GetOperationStatus: queryid: " + queryid + ", resp:" + resp;
        logger.info(msg);
    }


    /**
     * 添加获取结果事件
     *
     * @param req
     */
    public void addFetchResults(TFetchResultsReq req) {
        byte[] guid = req.getOperationHandle().getOperationId().getGuid();
        String queryid = StringHelper.getGuid(guid);
        String msg = "FetchResults: queryid: " + queryid;
        logger.info(msg);
    }


    /**
     * 添加关闭操作事件
     *
     * @param req
     */
    public void addCloseOperation(TCloseOperationReq req) {
        byte[] guid = req.getOperationHandle().getOperationId().getGuid();
        String queryid = StringHelper.getGuid(guid);
        String msg = "CloseOperation: queryid: " + queryid;
        logger.info(msg);
    }
}
