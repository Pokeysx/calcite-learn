#解析器部分
calcite-core中，解析器为SqlParser类，但是核心是SqlAbstractParserImpl类，这个类是一个抽象类，calcite-core通过javacc生成实现相对应的SqlParserImpl类，在SqlParserImpl类中实现了多种方法对sql进行分词操作，并且保留了分词元数据
SqlParser类为SqlAbstractParserImpl的高级封装类，提供了更简单的使用方式，并且对解析过程进行了封装，方便用户使用， 其核心组成有四
1. SqlAbstractParserImpl属性(注意所有生成的parserImpls实现，都在calcite-server模块中)
2. 利用多种create方法创建SqlAbstractParserImpl对象，名为Parser，外部调用的所有解析方法都是从这个对象中调用的
3. 有两个静态内部接口一个是config，这个接口是用@Value.Immutable标识的，表示会生成一个ImmutableSqlParser类
    注意:
   1. @Value.Immutable标识的类，其属性都是final的，并且其属性的getter方法都是public的，并且其属性的setter方法都是private的，使用的代码生成工具是immutables
   2. 这个在后续版本会弃用
   3. 这里用了一个可以借鉴的设计模式，这种建造者模式可以设置默认值，如果建造者在建造时没有设置某些参数，可以用默认的参数
4. 对外提供了四个方法，分别为
   parseQuery,parseQuery(sql) 用来解析sql，主要针对SELECT (这里有疑问，parseStmt最后也是调用的parseQuery,注释有问题)
   parseStmt 用来解析sql，几乎所有的sql都可以解析
   parseStmtList 用来解析sql，解析sql的list，例如：select * from t1; select * from t2;(这里是否能用到onesql-flink中)  
      getMetadata,getWarnings