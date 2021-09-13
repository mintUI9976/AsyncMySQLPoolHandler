# AsyncMySQLPoolHandler

[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/open-source.svg)](https://forthebadge.com) [![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)

[![GitHub release](https://img.shields.io/badge/RELEASE-100?style=for-the-badge)](https://gitlab.zyonicsoftware.com/mintUI9976/sqlpoolfactory/-/packages)   [![GitHub stars](https://img.shields.io/github/stars/mintUI9976/AsyncMySQLPoolHandler?style=for-the-badge)](https://github.com/mintUI9976/AsyncMySQLPoolHandler/stargazers)

#### An simplify library to create jdbc pools via HikariCP.

#### Statement/PreparedStatement methods are performed asynchronously.

# Features

- full asynchronously statement/preparedStatement methods
- simple jdbc connector
- simple configurator
- default config available
- instant query result via preparedStatement
- multiple object update via preparedStatement
- single update via statement
- add selection PublicKeyRetrival

# Implemented JDBC Frameworks

#### [HikariCP](https://github.com/brettwooldridge/HikariCP)

# Repository

```xml

<repositories>
    <repository>
        <id>gitlab-maven</id>
        <url>https://gitlab.zyonicsoftware.com/api/v4/projects/211/packages/maven</url>
    </repository>
</repositories>

<distributionManagement>
<repository>
    <id>gitlab-maven</id>
    <url>https://gitlab.zyonicsoftware.com/api/v4/projects/211/packages/maven</url>
</repository>

<snapshotRepository>
    <id>gitlab-maven</id>
    <url>https://gitlab.zyonicsoftware.com/api/v4/projects/211/packages/maven</url>
</snapshotRepository>
</distributionManagement>
```

# Dependency

```xml

<dependency>
    <groupId>de.mint.asyncmysqlpoolhandler</groupId>
    <artifactId>AsyncMySQLPoolHandler</artifactId>
    <version>Tag@Gitlab</version>
</dependency>
```

# Utilization

### Initialized AsyncMySQLPoolHandler Object

```java
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler=new AsyncMySQLPoolHandler(hostname,username,password,enumPoolFramework,configPoolFramework);
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler=new AsyncMySQLPoolHandler(hostname,port,username,password,enumPoolFramework,configPoolFramework);
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler=new AsyncMySQLPoolHandler(hostname,username,password,database,enumPoolFramework,configPoolFramework);
private final AsyncMySQLPoolHandler asyncMySQLPoolHandler=new AsyncMySQLPoolHandler(hostname,port,username,password,database,enumPoolFramework,configPoolFramework);
```

### ConfigPoolFramework

```java
private final ConfigPoolFramework configPoolFramework=ConfigBuilder.getConfigBuilder().build(); // returns a default configuration
// You do not have to change all values, the remaining values are filled with default values.
```

### OpenPool:

```java
private void openPool(){
        if(this.asyncMySQLPoolHandler.openPool()){
        //successful
        }else{
        //failed
        }
        }
```

### ClosePool:

```java
private void closePool(){
        if(this.asyncMySQLPoolHandler.closePool()){
        //successful
        }else{
        //failed
        }
        }
```

### ExecuteQuery:

```java
private void testQuery(){
        this.asyncMySQLPoolHandler.executeQueryAsync("SELECT * FROM `"+"yourTable"+"`;").whenComplete((cachedRowSet,throwable)->{
        try{
final Collection<String> collection=new ArrayList<>();
        while(cachedRowSet.next()){
        collection.add(cachedRowSet.getString(1));
        }
        // now you can work with the cachedRowSet
        cachedRowSet.close();
        }catch(final SQLException exception){
        exception.printStackTrace();
        }
        });
        }
```

### ReturnExecuteQuery:

##### ExecuteQueryAsync:

```java
public int testQueryResult(final String value){
        try{
final CachedRowSet resultSet=this.asyncMySQLPoolHandler.executeQueryAsync("SELECT `yourColumn` FROM `"+"yourTable"+"` WHERE `yourValue`= '"+this.asyncMySQLPoolHandler.removeSQLInjectionPossibility(value)+"';").join();
        if(resultSet.last()){
final int test=resultSet.getInt("yourColumn");
        resultSet.close();
        // return your async request result
        return test;
        }else{
        resultSet.close();
        // return a custom result if your request has failed
        return-1;
        }
        }catch(final SQLException exception){
        exception.printStackTrace();
        }
        return-1;
        }
```

##### ExecuteQueryInstantLastResultAsync:

```java
public int test(final String value){
final Integer result=(Integer)this.asyncMySQLPoolHandler.executeQueryInstantLastResultAsync("SELECT `yourColumn` FROM `"+"yourTable"+"` WHERE `yourValue`= '"+this.asyncMySQLPoolHandler.removeSQLInjectionPossibility(value)+"';","yourColumn").join();
        return result!=null?result:-1;
        }
```

##### ExecuteQueryInstantFirstResultAsync:

```java
public int test(final String value){
final Integer result=(Integer)this.asyncMySQLPoolHandler.executeQueryInstantFirstResultAsync("SELECT `yourColumn` FROM `"+"yourTable"+"` WHERE `yourValue`= '"+this.asyncMySQLPoolHandler.removeSQLInjectionPossibility(value)+"';","yourColumn").join();
        return result!=null?result:-1;
        }
```

##### ExecuteQueryInstantLastResultAsBooleanAsync:

```java
public boolean test(final String value){
        return this.asyncMySQLPoolHandler.executeQueryInstantLastResultAsBooleanAsync("SELECT `yourColumn` FROM `"+"yourTable"+"` WHERE `yourValue`= '"+this.asyncMySQLPoolHandler.removeSQLInjectionPossibility(value)+"';","yourColumn").join();
        }
```    

##### ExecuteQueryInstantFirstResultAsBooleanAsync:

```java
public boolean test(final String value){
        return this.asyncMySQLPoolHandler.executeQueryInstantFirstResultAsBooleanAsync("SELECT `yourColumn` FROM `"+"yourTable"+"` WHERE `yourValue`= '"+this.asyncMySQLPoolHandler.removeSQLInjectionPossibility(value)+"';","yourColumn").join();
        }
```    

##### ExecuteQueryInstantNextResultAsync:

```java
public boolean test(final String value){
        return this.asyncMySQLPoolHandler.executeQueryInstantNextResultAsync("SELECT * FROM `"+"yourTable"+"` WHERE `yourValue`= '"+this.asyncMySQLPoolHandler.removeSQLInjectionPossibility(value)+"';").join();
        }
```

### ExecuteUpdate with Statement:

```java
  private void testUpdate(final String yourValue){
        this.asyncMySQLPoolHandler.executeUpdateAsync("INSERT INTO `"+"yourTable"+"` SET `yourColumn` = '"+this.asyncMySQLPoolHandler.removeSQLInjectionPossibility(yourValue)+"';").whenComplete((aVoid,throwable)->{
        //now you can work with the result
        });
        }
```

### ExecuteUpdate with PreparedStatement:

```java
  private void testUpdate(final String...value){
        this.asyncMySQLPoolHandler.executeUpdatePreparedStatementAsync("INSERT INTO `"+"yourTable"+"` (value1, value2, value3, value4) VALUES (?, ?, ?, ?)",value1,value2,value3,value4).whenComplete((aVoid,throwable)->{
        //now you can work with the result
        });
        }
```

