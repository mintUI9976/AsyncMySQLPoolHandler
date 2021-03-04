package de.mint.asyncmysqlpoolhandler.initializationservice;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.mint.asyncmysqlpoolhandler.configservice.ConfigPoolFramework;

public abstract class PoolFramework {

    public HikariDataSource createHikariPool(final String database, final String hostname, final int port, final String username, final String password, final ConfigPoolFramework configPoolFramework) {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setJdbcUrl(!database.equals("") ? "jdbc:mysql://" + hostname + ":" + port + "/" + database : "jdbc:mysql://" + hostname + ":" + port);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", String.valueOf(configPoolFramework.isCachePrepStmts()));
        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(configPoolFramework.isUseSSL()));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", String.valueOf(configPoolFramework.getPrepStmtCacheSize()));
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", String.valueOf(configPoolFramework.getPrepStmtCacheSqlLimit()));
        hikariConfig.addDataSourceProperty("useServerPrepStmts", String.valueOf(configPoolFramework.isUseServerPrepStmts()));
        hikariConfig.addDataSourceProperty("useLocalSessionState", String.valueOf(configPoolFramework.isUseLocalSessionState()));
        hikariConfig.addDataSourceProperty("useLocalTransactionState", String.valueOf(configPoolFramework.isUseLocalTransactionState()));
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", String.valueOf(configPoolFramework.isRewriteBatchedStatements()));
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", String.valueOf(configPoolFramework.isCacheResultSetMetadata()));
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", String.valueOf(configPoolFramework.isCacheServerConfiguration()));
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", String.valueOf(configPoolFramework.isElideSetAutoCommits()));
        hikariConfig.addDataSourceProperty("maintainTimeStats", String.valueOf(configPoolFramework.isMaintainTimeStats()));
        hikariConfig.addDataSourceProperty("tcpKeepAlive", String.valueOf(configPoolFramework.isTcpKeepAlive()));
        hikariConfig.addDataSourceProperty("autoReconnect", String.valueOf(configPoolFramework.isAutoReconnect()));
        hikariConfig.addDataSourceProperty("allowPublicKeyRetrieval", String.valueOf(configPoolFramework.isAllowPublicKeyRetrieval()));
        hikariConfig.setMaximumPoolSize(configPoolFramework.getMaximumPoolSize());
        hikariConfig.setMaxLifetime(configPoolFramework.getMaxLifetime());
        hikariConfig.setMinimumIdle(configPoolFramework.getMinimumIdle());
        hikariConfig.setConnectionTimeout(configPoolFramework.getConnectionTimeout());
        return new HikariDataSource(hikariConfig);
    }

}
