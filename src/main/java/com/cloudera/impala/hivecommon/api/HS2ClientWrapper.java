package com.cloudera.impala.hivecommon.api;

import com.cloudera.impala.jdbc42.internal.apache.hive.service.rpc.thrift.*;
import com.cloudera.impala.jdbc42.internal.apache.thrift.TException;
import com.cloudera.impala.jdbc42.internal.apache.thrift.protocol.TProtocol;
import com.cloudera.impala.support.ILogger;
import com.cloudera.impala.support.LogUtilities;
import com.cloudera.utils.HS2ClientEvent;

/**
 * 重写 HS2ClientWrapper 以拿到提交sql时服务端返回的id参数
 * <p>
 * 重写以下方法：
 * ExecuteStatement 提交sql时调用，会返回查询id（queryid）
 * GetOperationStatus 查询sql状态
 * CloseOperation 关闭操作
 */
public class HS2ClientWrapper extends TCLIService.Client {
    ILogger m_logger;

    public HS2ClientWrapper(TProtocol tProtocol, ILogger logger) {
        super(tProtocol);
        this.m_logger = logger;
    }

    public HS2ClientWrapper(TProtocol tProtocol1, TProtocol tProtocol2,
                            ILogger logger) {
        super(tProtocol1, tProtocol2);
        this.m_logger = logger;
    }

    @Override
    public TCancelDelegationTokenResp CancelDelegationToken(TCancelDelegationTokenReq tCancelDelegationTokenReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger,
                new Object[]{tCancelDelegationTokenReq});
        synchronized (this) {
            return super.CancelDelegationToken(tCancelDelegationTokenReq);
        }
    }

    @Override
    public TCloseOperationResp CloseOperation(TCloseOperationReq tCloseOperationReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tCloseOperationReq});
        HS2ClientEvent.getInstance().addCloseOperation(tCloseOperationReq);
        synchronized (this) {
            return super.CloseOperation(tCloseOperationReq);
        }
    }

    @Override
    public TCloseSessionResp CloseSession(TCloseSessionReq tCloseSessionReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tCloseSessionReq});
        synchronized (this) {
            return super.CloseSession(tCloseSessionReq);
        }
    }

    @Override
    public TExecuteStatementResp ExecuteStatement(TExecuteStatementReq tExecuteStatementReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tExecuteStatementReq});
        synchronized (this) {
            TExecuteStatementResp resp = super.ExecuteStatement(tExecuteStatementReq);
            HS2ClientEvent.getInstance().addExecuteStatement(tExecuteStatementReq, resp);
            return resp;
        }
    }

    @Override
    public TFetchResultsResp FetchResults(TFetchResultsReq tFetchResultsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tFetchResultsReq});
        HS2ClientEvent.getInstance().addFetchResults(tFetchResultsReq);
        synchronized (this) {
            return super.FetchResults(tFetchResultsReq);
        }
    }

    @Override
    public TGetCatalogsResp GetCatalogs(TGetCatalogsReq tGetCatalogsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetCatalogsReq});
        synchronized (this) {
            return super.GetCatalogs(tGetCatalogsReq);
        }
    }

    @Override
    public TGetColumnsResp GetColumns(TGetColumnsReq tGetColumnsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetColumnsReq});
        synchronized (this) {
            return super.GetColumns(tGetColumnsReq);
        }
    }

    @Override
    public TGetDelegationTokenResp GetDelegationToken(TGetDelegationTokenReq tGetDelegationTokenReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetDelegationTokenReq});
        synchronized (this) {
            return super.GetDelegationToken(tGetDelegationTokenReq);
        }
    }

    @Override
    public TGetFunctionsResp GetFunctions(TGetFunctionsReq tGetFunctionsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetFunctionsReq});
        synchronized (this) {
            return super.GetFunctions(tGetFunctionsReq);
        }
    }

    @Override
    public TGetInfoResp GetInfo(TGetInfoReq tGetInfoReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetInfoReq});
        synchronized (this) {
            return super.GetInfo(tGetInfoReq);
        }
    }

    @Override
    public TGetOperationStatusResp GetOperationStatus(TGetOperationStatusReq tGetOperationStatusReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetOperationStatusReq});
        synchronized (this) {
            TGetOperationStatusResp resp = super.GetOperationStatus(tGetOperationStatusReq);
            HS2ClientEvent.getInstance().addGetOperationStatus(tGetOperationStatusReq, resp);
            return resp;
        }
    }

    @Override
    public TGetResultSetMetadataResp GetResultSetMetadata(TGetResultSetMetadataReq tGetResultSetMetadataReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger,
                new Object[]{tGetResultSetMetadataReq});
        synchronized (this) {
            return super.GetResultSetMetadata(tGetResultSetMetadataReq);
        }
    }

    @Override
    public TGetSchemasResp GetSchemas(TGetSchemasReq tGetSchemasReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetSchemasReq});
        synchronized (this) {
            return super.GetSchemas(tGetSchemasReq);
        }
    }

    @Override
    public TGetTableTypesResp GetTableTypes(TGetTableTypesReq tGetTableTypesReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetTableTypesReq});
        synchronized (this) {
            return super.GetTableTypes(tGetTableTypesReq);
        }
    }

    @Override
    public TGetTablesResp GetTables(TGetTablesReq tGetTablesReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetTablesReq});
        synchronized (this) {
            return super.GetTables(tGetTablesReq);
        }
    }

    @Override
    public TGetTypeInfoResp GetTypeInfo(TGetTypeInfoReq tGetTypeInfoReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tGetTypeInfoReq});
        synchronized (this) {
            return super.GetTypeInfo(tGetTypeInfoReq);
        }
    }

    @Override
    public TOpenSessionResp OpenSession(TOpenSessionReq tOpenSessionReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[]{tOpenSessionReq});
        synchronized (this) {
            return super.OpenSession(tOpenSessionReq);
        }
    }

    @Override
    public TRenewDelegationTokenResp RenewDelegationToken(TRenewDelegationTokenReq tRenewDelegationTokenReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger,
                new Object[]{tRenewDelegationTokenReq});
        synchronized (this) {
            return super.RenewDelegationToken(tRenewDelegationTokenReq);
        }
    }

    @Override
    public TCancelDelegationTokenResp recv_CancelDelegationToken() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_CancelDelegationToken();
        }
    }

    @Override
    public TCancelOperationResp recv_CancelOperation() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_CancelOperation();
        }
    }

    @Override
    public TCloseOperationResp recv_CloseOperation() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_CloseOperation();
        }
    }

    @Override
    public TCloseSessionResp recv_CloseSession() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_CloseSession();
        }
    }

    @Override
    public TExecuteStatementResp recv_ExecuteStatement() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_ExecuteStatement();
        }
    }

    @Override
    public TFetchResultsResp recv_FetchResults() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_FetchResults();
        }
    }

    @Override
    public TGetCatalogsResp recv_GetCatalogs() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetCatalogs();
        }
    }

    @Override
    public TGetColumnsResp recv_GetColumns() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetColumns();
        }
    }

    @Override
    public TGetDelegationTokenResp recv_GetDelegationToken() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetDelegationToken();
        }
    }

    @Override
    public TGetFunctionsResp recv_GetFunctions() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetFunctions();
        }
    }

    @Override
    public TGetInfoResp recv_GetInfo() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetInfo();
        }
    }

    @Override
    public TGetOperationStatusResp recv_GetOperationStatus() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetOperationStatus();
        }
    }

    @Override
    public TGetResultSetMetadataResp recv_GetResultSetMetadata() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetResultSetMetadata();
        }
    }

    @Override
    public TGetSchemasResp recv_GetSchemas() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetSchemas();
        }
    }

    @Override
    public TGetTableTypesResp recv_GetTableTypes() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetTableTypes();
        }
    }

    @Override
    public TGetTablesResp recv_GetTables() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetTables();
        }
    }

    @Override
    public TGetTypeInfoResp recv_GetTypeInfo() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_GetTypeInfo();
        }
    }

    @Override
    public TOpenSessionResp recv_OpenSession() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_OpenSession();
        }
    }

    @Override
    public TRenewDelegationTokenResp recv_RenewDelegationToken() throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            return super.recv_RenewDelegationToken();
        }
    }

    @Override
    public void send_CancelDelegationToken(TCancelDelegationTokenReq tCancelDelegationTokenReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_CancelDelegationToken(tCancelDelegationTokenReq);
        }
    }

    @Override
    public void send_CancelOperation(TCancelOperationReq tCancelOperationReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_CancelOperation(tCancelOperationReq);
        }
    }

    @Override
    public void send_CloseOperation(TCloseOperationReq tCloseOperationReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_CloseOperation(tCloseOperationReq);
        }
    }

    @Override
    public void send_CloseSession(TCloseSessionReq tCloseSessionReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_CloseSession(tCloseSessionReq);
        }
    }

    @Override
    public void send_ExecuteStatement(TExecuteStatementReq tExecuteStatementReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_ExecuteStatement(tExecuteStatementReq);
        }
    }

    @Override
    public void send_FetchResults(TFetchResultsReq tFetchResultsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_FetchResults(tFetchResultsReq);
        }
    }

    @Override
    public void send_GetCatalogs(TGetCatalogsReq tGetCatalogsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetCatalogs(tGetCatalogsReq);
        }
    }

    @Override
    public void send_GetColumns(TGetColumnsReq tGetColumnsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetColumns(tGetColumnsReq);
        }
    }

    @Override
    public void send_GetDelegationToken(TGetDelegationTokenReq tGetDelegationTokenReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetDelegationToken(tGetDelegationTokenReq);
        }
    }

    @Override
    public void send_GetFunctions(TGetFunctionsReq tGetFunctionsReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetFunctions(tGetFunctionsReq);
        }
    }

    @Override
    public void send_GetInfo(TGetInfoReq tGetInfoReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetInfo(tGetInfoReq);
        }
    }

    @Override
    public void send_GetOperationStatus(TGetOperationStatusReq tGetOperationStatusReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetOperationStatus(tGetOperationStatusReq);
        }
    }

    @Override
    public void send_GetResultSetMetadata(TGetResultSetMetadataReq tGetResultSetMetadataReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetResultSetMetadata(tGetResultSetMetadataReq);
        }
    }

    @Override
    public void send_GetSchemas(TGetSchemasReq tGetSchemasReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetSchemas(tGetSchemasReq);
        }
    }

    @Override
    public void send_GetTableTypes(TGetTableTypesReq tGetTableTypesReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetTableTypes(tGetTableTypesReq);
        }
    }

    @Override
    public void send_GetTables(TGetTablesReq tGetTablesReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetTables(tGetTablesReq);
        }
    }

    @Override
    public void send_GetTypeInfo(TGetTypeInfoReq tGetTypeInfoReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_GetTypeInfo(tGetTypeInfoReq);
        }
    }

    public void send_OpenSession(TOpenSessionReq tOpenSessionReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_OpenSession(tOpenSessionReq);
        }
    }

    @Override
    public void send_RenewDelegationToken(TRenewDelegationTokenReq tRenewDelegationTokenReq) throws TException {
        LogUtilities.logFunctionEntrance(this.m_logger, new Object[0]);
        synchronized (this) {
            super.send_RenewDelegationToken(tRenewDelegationTokenReq);
        }
    }
}
