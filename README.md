# AsyncMySQLPoolHandler
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/open-source.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)

[![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/mInT-runs/JdbcPoolFrameworkHandler?include_prereleases&style=for-the-badge)](https://github.com/mInT-runs/JdbcPoolFrameworkHandler/releases) ![GitHub lines)](https://img.shields.io/tokei/lines/github/mInT-runs/JdbcPoolFrameworkHandler?color=yellow&style=for-the-badge) ![GitHub language)](https://img.shields.io/github/languages/top/mInT-runs/JdbcPoolFrameworkHandler?color=red&style=for-the-badge) ![GitHub bytes)](https://img.shields.io/github/languages/code-size/mInT-runs/JdbcPoolFrameworkHandler?color=orange&style=for-the-badge) ![GitHub language count](https://img.shields.io/github/languages/count/mInT-runs/JdbcPoolFrameworkHandler?style=for-the-badge) 
#### An simplify library to create jdbc pools via HikariCP. The Statement/PreparedStatement methods are performed asynchronously 
# Features
# Implemented JDBC Frameworks
#### [HikariCP](https://github.com/brettwooldridge/HikariCP)
# Repository
```java
<repositories>
   <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
   </repository>
</repositories>
```
# Dependency
```java
<dependency>
  <groupId>com.github.mInT-runs</groupId>
  <artifactId>JdbcPoolFrameworkHandler</artifactId>
  <version>Tag</version>
</dependency>
```
# Utilization
### Initialized AsyncMySQLPoolHandler Object
```java
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler = new AsyncMySQLPoolHandler(hostname, username, password, enumPoolFramework, configPoolFramework);
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler = new AsyncMySQLPoolHandler(hostname, port, username, password, enumPoolFramework, configPoolFramework);
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler = new AsyncMySQLPoolHandler(hostname, username, password , database, enumPoolFramework, configPoolFramework);
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler = new AsyncMySQLPoolHandler(hostname, port, username, password, database, enumPoolFramework, configPoolFramework);
```
### ConfigPoolFramework
```java
private final ConfigPoolFramework configPoolFramework = ConfigBuilder.getConfigBuilder().build(); // returns a default configuration
// You can add more and more variables and you do not need to change all variables. The rest will be filled up with the default variables.
```
### OpenPool:
```java
private void openPool(){
        if (this.asyncMySQLPoolHandler.openPool()){
            //successful
        } else {
            //failed
        }
    }
```
### ClosePool:
```java
private void closePool(){
        if (this.asyncMySQLPoolHandler.closePool()){
            //successful
        } else {
            //failed
        }
    }
```
### ExecuteQuery:
```java
private void testQuery() {
        this.asyncMySQLPoolHandler.executeQueryAsync("SELECT * FROM `" + "yourTable" + "`;").whenComplete((cachedRowSet, throwable) -> {
            try {
                final Collection<String> collection = new ArrayList<>();
                while (cachedRowSet.next()) {
                    collection.add(cachedRowSet.getString(1));
                }
                // now you can work with the cachedRowSet
                cachedRowSet.close();
            } catch (final SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
```
### ReturnExecuteQuery:
##### ExecuteQueryAsync:
```java
public int testQueryResult() {
        try {
            final CachedRowSet resultSet = this.asyncMySQLPoolHandler.executeQueryAsync("SELECT `yourColumn` FROM `" + "yourTable" + "` WHERE `yourValue`= '" + value + "';").join();
            if (resultSet.last()) {
                final int test = resultSet.getInt("yourColumn");
                resultSet.close();
                // return your async request result
                return test;
            } else {
                resultSet.close();
                // return a custom result if your request has failed
                return -1;
            }
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }
```
##### ExecuteQueryInstantLastResultAsync:
```java
public int test() {
        final Integer column = (Integer) this.asyncMySQLPoolHandler.executeQueryInstantLastResultAsync("SELECT `yourColumn` FROM `" + "yourTable" + "` WHERE `yourValue`= '" + value + "';" , "yourColumn").join();
        return column != null ? result : -1;
    }
```
##### ExecuteQueryInstantFirstResultAsync:
```java
public int test() {
        final Integer column = (Integer) this.asyncMySQLPoolHandler.executeQueryInstantFirstResultAsync("SELECT `yourColumn` FROM `" + "yourTable" + "` WHERE `yourValue`= '" + value + "';" , "yourColumn").join();
        return column != null ? result : -1;
    }
```
##### ExecuteQueryInstantNextResultAsync:
```java
public boolean test() {
        return this.asyncMySQLPoolHandler.executeQueryInstantNextResultAsync("SELECT * FROM `" + "yourTable" + "` WHERE `yourValue`= '" + value + "';").join();
    }
```

### ExecuteUpdate with Statement:
```java
  private void testUpdate() {
        this.asyncMySQLPoolHandler.executeUpdateAsync("INSERT INTO `" + "yourTable" + "` SET `yourColumn` = '" + "yourValue" + "';").whenComplete((aVoid, throwable) -> {
            //now you can work with the result
        });
    }
```
### ExecuteUpdate with PreparedStatement:
```java
  private void testUpdate() {
        this.asyncMySQLPoolHandler.executeUpdatePreparedStatementAsync("INSERT INTO `" + "yourTable" + "` (value1, value2, value3, value4) VALUES (?, ?, ?, ?)", value1,value2,value3,value4).whenComplete((aVoid, throwable) -> {
            //now you can work with the result
        });
    }
```

