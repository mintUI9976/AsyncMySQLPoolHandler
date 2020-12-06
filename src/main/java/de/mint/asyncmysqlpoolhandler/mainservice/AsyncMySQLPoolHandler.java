package de.mint.asyncmysqlpoolhandler.mainservice;

import com.zaxxer.hikari.HikariDataSource;
import de.mint.asyncmysqlpoolhandler.configservice.ConfigPoolFramework;
import de.mint.asyncmysqlpoolhandler.enumservice.EnumPoolFramework;
import de.mint.asyncmysqlpoolhandler.initializationservice.PoolFramework;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public class AsyncMySQLPoolHandler extends PoolFramework {

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private final String database;
    private final EnumPoolFramework enumPoolFramework;
    private final ConfigPoolFramework configPoolFramework;
    private HikariDataSource hikariDataSource = null;
    private final Logger logger = Logger.getLogger(AsyncMySQLPoolHandler.class.getName());


    public AsyncMySQLPoolHandler(@NotNull final String hostname, @NotNull final String username, @NotNull final String password, @NotNull final EnumPoolFramework enumPoolFramework, @NotNull final ConfigPoolFramework configPoolFramework) {
        this(hostname, 3306, username, password, "", enumPoolFramework, configPoolFramework);
    }

    public AsyncMySQLPoolHandler(@NotNull final String hostname, final int port, @NotNull final String username, @NotNull final String password, @NotNull final EnumPoolFramework enumPoolFramework, @NotNull final ConfigPoolFramework configPoolFramework) {
        this(hostname, port, username, password, "", enumPoolFramework, configPoolFramework);
    }

    public AsyncMySQLPoolHandler(@NotNull final String hostname, @NotNull final String username, @NotNull final String password, @NotNull final String database, @NotNull final EnumPoolFramework enumPoolFramework, @NotNull final ConfigPoolFramework configPoolFramework) {
        this(hostname, 3306, username, password, database, enumPoolFramework, configPoolFramework);
    }

    public AsyncMySQLPoolHandler(@NotNull final String hostname, final int port, @NotNull final String username, @NotNull final String password, @NotNull final String database, @NotNull final EnumPoolFramework enumPoolFramework, @NotNull final ConfigPoolFramework configPoolFramework) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.enumPoolFramework = enumPoolFramework;
        this.configPoolFramework = configPoolFramework;
    }

    public EnumPoolFramework getEnumPoolFramework() {
        return this.enumPoolFramework;
    }

    protected void createMessage(final String message, final Level level) {
        if (this.configPoolFramework.isInfoMessage()) {
            this.logger.log(level, message);
        }
    }

    public boolean openPool() {
        try {
            if (!this.isPoolOpen()) {
                if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                    this.hikariDataSource = this.createHikariPool(this.database, this.hostname, this.port, this.username, this.password, this.configPoolFramework);
                }
                Thread.sleep(100);
                this.createMessage("The pool was successfully created! [" + this.enumPoolFramework.name() + "]", Level.INFO);
                return true;
            } else {
                this.createMessage("The pool was not successfully created! [" + this.enumPoolFramework.name() + "]", Level.WARNING);
                return false;
            }
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
        return false;
    }


    protected boolean isPoolOpen() {
        if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
            return this.hikariDataSource != null && !this.hikariDataSource.isClosed();
        }
        return false;
    }

    public String removeSQLInjectionPossibility(@NotNull final String sqlInjectionPossibility) {
        return sqlInjectionPossibility.replaceAll("[']", "\\\\'").replaceAll("[`]", "\\\\`");
    }

    public boolean closePool() {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                this.hikariDataSource.close();
            }
            this.createMessage("The pool was successfully closed! [" + this.enumPoolFramework.name() + "]", Level.INFO);
            return true;
        } else {
            this.createMessage("The pool could not be closed! [" + this.enumPoolFramework.name() + "]", Level.INFO);
            return false;
        }
    }

    public CompletableFuture<Void> executeUpdateAsync(@Language("MYSQL") @NotNull final String sql) {
        return CompletableFuture.runAsync(() -> this.executeUpdateHandlerWithStatement(sql));
    }

    public CompletableFuture<Void> executeUpdatePreparedStatementAsync(@Language("MYSQL") @NotNull final String sql, @NotNull final Object... values) {
        return CompletableFuture.runAsync(() -> this.executeUpdateHandlerWithPreparedStatement(sql, values));
    }

    public CompletableFuture<CachedRowSet> executeQueryAsync(@NotNull final String sql) {
        return CompletableFuture.supplyAsync(() -> this.queryCacheRowSetResult(sql));
    }

    public CompletableFuture<Object> executeQueryInstantLastResultAsync(@Language("MYSQL") @NotNull final String sql, @NotNull final String resultColumn) {
        return CompletableFuture.supplyAsync(() -> this.queryInstantLastObjectResult(sql, resultColumn));
    }

    public CompletableFuture<Object> executeQueryInstantFirstResultAsync(@Language("MYSQL") @NotNull final String sql, @NotNull final String resultColumn) {
        return CompletableFuture.supplyAsync(() -> this.queryInstantFirstObjectResult(sql, resultColumn));
    }

    public CompletableFuture<Boolean> executeQueryInstantLastResultAsBooleanAsync(@Language("MYSQL") @NotNull final String sql, @NotNull final String resultColumn) {
        return CompletableFuture.supplyAsync(() -> this.queryInstantLastBooleanResult(sql, resultColumn));
    }

    public CompletableFuture<Boolean> executeQueryInstantFirstResultAsBooleanAsync(@Language("MYSQL") @NotNull final String sql, @NotNull final String resultColumn) {
        return CompletableFuture.supplyAsync(() -> this.queryInstantFirstBooleanResult(sql, resultColumn));
    }

    public CompletableFuture<Boolean> executeQueryInstantNextResultAsync(@Language("MYSQL") @NotNull final String sql) {
        return CompletableFuture.supplyAsync(() -> this.queryInstantNextBooleanResult(sql));
    }

    protected void executeUpdateHandlerWithStatement(final String sql) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final Statement statement = connection.createStatement()) {
                    statement.executeUpdate(sql);
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            if (this.closePool() && this.openPool()) {
                this.executeUpdateHandlerWithStatement(sql);
            }
        }
    }

    protected void executeUpdateHandlerWithPreparedStatement(final String sql, final Object... values) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    for (int i = 0; i < values.length; i++) {
                        preparedStatement.setObject(i + 1, values[i]);
                    }
                    preparedStatement.executeUpdate();
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } else {
            if (this.closePool() && this.openPool()) {
                this.executeUpdateHandlerWithPreparedStatement(sql, values);
            }
        }
    }

    protected CachedRowSet queryCacheRowSetResult(final String sql) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    final CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
                    cachedRowSet.populate(resultSet);
                    preparedStatement.close();
                    resultSet.close();
                    return cachedRowSet;
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
        } else {
            return this.closePool() && this.openPool() ? this.queryCacheRowSetResult(sql) : null;
        }
        return null;
    }

    protected Object queryInstantLastObjectResult(final String sql, final String resultColumn) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    final CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
                    cachedRowSet.populate(resultSet);
                    preparedStatement.close();
                    resultSet.close();
                    if (cachedRowSet.last()) {
                        final Object object = cachedRowSet.getObject(resultColumn);
                        cachedRowSet.close();
                        return object;
                    } else {
                        cachedRowSet.close();
                        return null;
                    }
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
        } else {
            return this.closePool() && this.openPool() ? this.queryInstantLastObjectResult(sql, resultColumn) : null;
        }
        return null;
    }

    protected boolean queryInstantLastBooleanResult(final String sql, final String resultColumn) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    final CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
                    cachedRowSet.populate(resultSet);
                    preparedStatement.close();
                    resultSet.close();
                    if (cachedRowSet.last()) {
                        final boolean aBoolean = cachedRowSet.getBoolean(resultColumn);
                        cachedRowSet.close();
                        return aBoolean;
                    } else {
                        cachedRowSet.close();
                        return false;
                    }
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                    return false;
                }
            }
        } else {
            return (this.closePool() && this.openPool()) && this.queryInstantLastBooleanResult(sql, resultColumn);
        }
        return false;
    }

    protected boolean queryInstantFirstBooleanResult(final String sql, final String resultColumn) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    final CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
                    cachedRowSet.populate(resultSet);
                    preparedStatement.close();
                    resultSet.close();
                    if (cachedRowSet.first()) {
                        final boolean aBoolean = cachedRowSet.getBoolean(resultColumn);
                        cachedRowSet.close();
                        return aBoolean;
                    } else {
                        cachedRowSet.close();
                        return false;
                    }
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                    return false;
                }
            }
        } else {
            return (this.closePool() && this.openPool()) && this.queryInstantFirstBooleanResult(sql, resultColumn);
        }
        return false;
    }

    protected Object queryInstantFirstObjectResult(final String sql, final String resultColumn) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    final CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
                    cachedRowSet.populate(resultSet);
                    preparedStatement.close();
                    resultSet.close();
                    if (cachedRowSet.first()) {
                        final Object object = cachedRowSet.getObject(resultColumn);
                        cachedRowSet.close();
                        return object;
                    } else {
                        cachedRowSet.close();
                        return null;
                    }
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                    return null;
                }
            }
        } else {
            return this.closePool() && this.openPool() ? this.queryInstantFirstObjectResult(sql, resultColumn) : null;
        }
        return null;
    }

    protected boolean queryInstantNextBooleanResult(final String sql) {
        if (this.isPoolOpen()) {
            if (this.enumPoolFramework == EnumPoolFramework.HIKARICP) {
                try (final Connection connection = this.hikariDataSource.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    final CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
                    cachedRowSet.populate(resultSet);
                    preparedStatement.close();
                    resultSet.close();
                    final boolean result = cachedRowSet.next();
                    cachedRowSet.close();
                    return result;
                } catch (final SQLException exception) {
                    exception.printStackTrace();
                    return false;
                }
            }
        } else {
            return (this.closePool() && this.openPool()) && this.queryInstantNextBooleanResult(sql);
        }
        return false;
    }


}
