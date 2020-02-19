package com.cloudera.impala.hivecommon.jdbc42;

import com.cloudera.impala.dsi.core.interfaces.IStatement;
import com.cloudera.impala.dsi.dataengine.utilities.ExecutionResult;
import com.cloudera.impala.hivecommon.core.HiveJDBCStatement;
import com.cloudera.impala.hivecommon.core.IHadoopStatement;
import com.cloudera.impala.hivecommon.dataengine.HiveJDBCDSIExtQueryExecutor;
import com.cloudera.impala.hivecommon.dataengine.HiveJDBCNativeQueryExecutor;
import com.cloudera.impala.hivecommon.dataengine.HiveJDBCResultSet;
import com.cloudera.impala.jdbc.common.SConnection;
import com.cloudera.impala.jdbc.common.SStatement;
import com.cloudera.impala.jdbc.jdbc42.S42Statement;
import com.cloudera.impala.jdbc42.internal.apache.hive.service.rpc.thrift.TExecuteStatementResp;
import com.cloudera.impala.jdbc42.internal.com.cloudera.altus.shaded.org.apache.commons.lang3.reflect.FieldUtils;
import com.cloudera.utils.StringHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Hive42Statement extends S42Statement implements IHadoopStatement {
    public Hive42Statement(HiveJDBCStatement hiveJDBCStatement, SConnection sConnection
            , int i) {
        super((IStatement) hiveJDBCStatement, sConnection, i);
    }

    @Override
    public void cancel() throws SQLException {
        super.cancel();
        synchronized (this.m_cancelLock) {
            if (this.m_statement instanceof HiveJDBCStatement && this.m_isInCancelableFunction) {
                ((HiveJDBCStatement) this.m_statement).cancel();
            }
        }
    }

    @Override
    public synchronized ResultSet executeAsync(String sql) throws SQLException {
        setAsyncExecution(true);
        return super.executeQuery(sql);
    }

    @Override
    public synchronized ResultSet executeQuery(String sql) throws SQLException {
        setAsyncExecution(false);
        return super.executeQuery(sql);
    }

    public boolean getAsyncExecution() {
        return ((HiveJDBCStatement) this.m_statement).getAsyncExecution();
    }

    public void setAsyncExecution(boolean isAsyncExecution) {
        ((HiveJDBCStatement) this.m_statement).setAsyncExecution(isAsyncExecution);
    }

    @Override
    protected ResultSet createResultSet(ExecutionResult executionResult) throws SQLException {
        ResultSet resultSet = null;
        if (getAsyncExecution() && executionResult.getResult() instanceof HiveJDBCResultSet) {
            resultSet = new Hive42ForwardResultSet((SStatement) this,
                    (HiveJDBCResultSet) executionResult.getResult(), this.m_logger);
            resultSet.setFetchSize(getFetchSize());
        } else {
            resultSet = super.createResultSet(executionResult);
        }
        return resultSet;
    }

    @Override
    public String getYarnATSGuid() {
        return (null != this.m_queryExecutor && this.m_queryExecutor instanceof HiveJDBCDSIExtQueryExecutor) ? ((HiveJDBCDSIExtQueryExecutor) this.m_queryExecutor).getYarnATSGuid() : null;
    }

    /**
     * Get query id for impalad query interface
     *
     * @return
     */
    public String getQueryId() {
        if (null != this.m_queryExecutor) {

            HiveJDBCNativeQueryExecutor nativeQueryExecutor = null;

            if (this.m_queryExecutor instanceof HiveJDBCDSIExtQueryExecutor) {
                HiveJDBCDSIExtQueryExecutor dsiExtQueryExecutor =
                        (HiveJDBCDSIExtQueryExecutor) this.m_queryExecutor;
                try {
                    nativeQueryExecutor =
                            (HiveJDBCNativeQueryExecutor) FieldUtils.readField(dsiExtQueryExecutor,
                                    "m_nativeQueryExecutor", true);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else if (this.m_queryExecutor instanceof HiveJDBCNativeQueryExecutor) {
                nativeQueryExecutor =
                        (HiveJDBCNativeQueryExecutor) this.m_queryExecutor;
            }

            if (null != nativeQueryExecutor) {
                TExecuteStatementResp resp = nativeQueryExecutor.getExeContext().m_executeResponse;
                byte[] guid = resp.getOperationHandle().getOperationId().getGuid();

                return StringHelper.getGuid(guid);
            }
        }

        return null;
    }
}
