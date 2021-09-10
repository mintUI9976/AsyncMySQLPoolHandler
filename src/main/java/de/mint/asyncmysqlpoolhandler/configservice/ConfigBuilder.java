package de.mint.asyncmysqlpoolhandler.configservice;

public class ConfigBuilder {

    private final ConfigPoolFramework configDataSource = new ConfigPoolFramework();
    private final static ConfigBuilder configBuilder = new ConfigBuilder();

    public ConfigBuilder setMaximumPoolSize(final int amount) {
        this.configDataSource.setMaximumPoolSize(amount);
        return this;
    }

    public ConfigBuilder setMinimumIdle(final int amount) {
        this.configDataSource.setMinimumIdle(amount);
        return this;
    }

    public ConfigBuilder setConnectionTimeout(final long amount) {
        this.configDataSource.setConnectionTimeout(amount);
        return this;
    }

    public ConfigBuilder setMaxLifetime(final long amount) {
        this.configDataSource.setMaxLifetime(amount);
        return this;
    }

    public ConfigBuilder setPrepStmtCacheSqlLimit(final int amount) {
        this.configDataSource.setPrepStmtCacheSqlLimit(amount);
        return this;
    }

    public ConfigBuilder setPrepStmtCacheSize(final int amount) {
        this.configDataSource.setPrepStmtCacheSize(amount);
        return this;
    }

    public ConfigBuilder setCachePrepStmts(final boolean amount) {
        this.configDataSource.setCachePrepStmts(amount);
        return this;
    }

    public ConfigBuilder setUseSSL(final boolean amount) {
        this.configDataSource.setUseSSL(amount);
        return this;
    }

    public ConfigBuilder setUseServerPrepStmts(final boolean amount) {
        this.configDataSource.setUseServerPrepStmts(amount);
        return this;
    }

    public ConfigBuilder setUseLocalSessionState(final boolean amount) {
        this.configDataSource.setUseLocalSessionState(amount);
        return this;
    }

    public ConfigBuilder setUseLocalTransactionState(final boolean amount) {
        this.configDataSource.setUseLocalTransactionState(amount);
        return this;
    }

    public ConfigBuilder setRewriteBatchedStatements(final boolean amount) {
        this.configDataSource.setRewriteBatchedStatements(amount);
        return this;
    }

    public ConfigBuilder setCacheResultSetMetadata(final boolean amount) {
        this.configDataSource.setCacheResultSetMetadata(amount);
        return this;
    }

    public ConfigBuilder setCacheServerConfiguration(final boolean amount) {
        this.configDataSource.setCacheServerConfiguration(amount);
        return this;
    }

    public ConfigBuilder setElideSetAutoCommits(final boolean amount) {
        this.configDataSource.setElideSetAutoCommits(amount);
        return this;
    }

    public ConfigBuilder setMaintainTimeStats(final boolean amount) {
        this.configDataSource.setMaintainTimeStats(amount);
        return this;
    }

    public ConfigBuilder setTcpKeepAlive(final boolean amount) {
        this.configDataSource.setTcpKeepAlive(amount);
        return this;
    }

    public ConfigBuilder setAutoReconnect(final boolean amount) {
        this.configDataSource.setAutoReconnect(amount);
        return this;
    }

    public ConfigBuilder setInfoMessage(final boolean amount) {
        this.configDataSource.setInfoMessage(amount);
        return this;
    }

    public ConfigBuilder setAllowPublicKeyRetrieval(final boolean allowed) {
        this.configDataSource.setAllowPublicKeyRetrieval(allowed);
        return this;
    }

    public ConfigBuilder setUseUnicode(final boolean allowed) {
        this.configDataSource.setUseUnicode(allowed);
        return this;
    }

    public ConfigBuilder setServerTimezone(final String timezone) {
        this.configDataSource.setServerTimezone(timezone);
        return this;
    }

    public ConfigPoolFramework build() {
        return this.configDataSource;
    }

    public static ConfigBuilder getConfigBuilder() {
        return ConfigBuilder.configBuilder;
    }
}
