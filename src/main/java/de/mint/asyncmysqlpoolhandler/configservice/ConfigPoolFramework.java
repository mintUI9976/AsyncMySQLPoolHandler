package de.mint.asyncmysqlpoolhandler.configservice;

public class ConfigPoolFramework {

    private boolean cachePrepStmts = true;
    private boolean useSSL = false;
    private boolean useServerPrepStmts = true;
    private boolean useLocalSessionState = true;
    private boolean useLocalTransactionState = false;
    private boolean rewriteBatchedStatements = true;
    private boolean cacheResultSetMetadata = true;
    private boolean cacheServerConfiguration = true;
    private boolean elideSetAutoCommits = true;
    private boolean maintainTimeStats = false;
    private boolean tcpKeepAlive = true;
    private boolean autoReconnect = true;
    private boolean infoMessage = true;
    private int maximumPoolSize = 10;
    private int prepStmtCacheSize = 250;
    private int prepStmtCacheSqlLimit = 2048;
    private int minimumIdle = 5;
    private long maxLifetime = 180000;
    private long connectionTimeout = 30000;
    private boolean allowPublicKeyRetrieval = true;
    private String characterEncoding = "utf8";

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public boolean isUseUnicode() {
        return useUnicode;
    }

    public void setUseUnicode(boolean useUnicode) {
        this.useUnicode = useUnicode;
    }

    private boolean useUnicode = true;

    void setMaximumPoolSize(final int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    void setCachePrepStmts(final boolean cachePrepStmts) {
        this.cachePrepStmts = cachePrepStmts;
    }

    public void setUseSSL(final boolean useSSL) {
        this.useSSL = useSSL;
    }

    void setUseServerPrepStmts(final boolean useServerPrepStmts) {
        this.useServerPrepStmts = useServerPrepStmts;
    }

    void setUseLocalSessionState(final boolean useLocalSessionState) {
        this.useLocalSessionState = useLocalSessionState;
    }

    void setUseLocalTransactionState(final boolean useLocalTransactionState) {
        this.useLocalTransactionState = useLocalTransactionState;
    }

    void setRewriteBatchedStatements(final boolean rewriteBatchedStatements) {
        this.rewriteBatchedStatements = rewriteBatchedStatements;
    }

    void setCacheResultSetMetadata(final boolean cacheResultSetMetadata) {
        this.cacheResultSetMetadata = cacheResultSetMetadata;
    }

    void setCacheServerConfiguration(final boolean cacheServerConfiguration) {
        this.cacheServerConfiguration = cacheServerConfiguration;
    }

    void setElideSetAutoCommits(final boolean elideSetAutoCommits) {
        this.elideSetAutoCommits = elideSetAutoCommits;
    }

    void setMaintainTimeStats(final boolean maintainTimeStats) {
        this.maintainTimeStats = maintainTimeStats;
    }

    void setTcpKeepAlive(final boolean tcpKeepAlive) {
        this.tcpKeepAlive = tcpKeepAlive;
    }

    void setAutoReconnect(final boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    void setPrepStmtCacheSize(final int prepStmtCacheSize) {
        this.prepStmtCacheSize = prepStmtCacheSize;
    }

    void setPrepStmtCacheSqlLimit(final int prepStmtCacheSqlLimit) {
        this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
    }

    void setMaxLifetime(final long maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    void setConnectionTimeout(final long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    void setInfoMessage(final boolean infoMessage) {
        this.infoMessage = infoMessage;
    }

    void setMinimumIdle(final int minimumIdle) {
        this.minimumIdle = minimumIdle;
    }

    public boolean isInfoMessage() {
        return this.infoMessage;
    }

    public boolean isCachePrepStmts() {
        return this.cachePrepStmts;
    }

    public boolean isUseSSL() {
        return this.useSSL;
    }

    public boolean isUseServerPrepStmts() {
        return this.useServerPrepStmts;
    }

    public boolean isUseLocalSessionState() {
        return this.useLocalSessionState;
    }

    public boolean isUseLocalTransactionState() {
        return this.useLocalTransactionState;
    }

    public boolean isRewriteBatchedStatements() {
        return this.rewriteBatchedStatements;
    }

    public boolean isCacheResultSetMetadata() {
        return this.cacheResultSetMetadata;
    }

    public boolean isCacheServerConfiguration() {
        return this.cacheServerConfiguration;
    }

    public boolean isElideSetAutoCommits() {
        return this.elideSetAutoCommits;
    }

    public boolean isMaintainTimeStats() {
        return this.maintainTimeStats;
    }

    public boolean isTcpKeepAlive() {
        return this.tcpKeepAlive;
    }

    public boolean isAutoReconnect() {
        return this.autoReconnect;
    }

    public int getPrepStmtCacheSize() {
        return this.prepStmtCacheSize;
    }

    public int getPrepStmtCacheSqlLimit() {
        return this.prepStmtCacheSqlLimit;
    }

    public long getMaxLifetime() {
        return this.maxLifetime;
    }

    public long getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public int getMinimumIdle() {
        return this.minimumIdle;
    }

    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public void setAllowPublicKeyRetrieval(boolean allowPublicKeyRetrieval) {
        this.allowPublicKeyRetrieval = allowPublicKeyRetrieval;
    }

    public boolean isAllowPublicKeyRetrieval() {
        return allowPublicKeyRetrieval;
    }
}
