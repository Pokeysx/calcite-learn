package org.ysx;

import org.apache.calcite.avatica.util.Casing;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.parser.SqlAbstractParserImpl;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.impl.SqlParserImpl;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.util.SourceStringReader;

/**
 * Project: calcite-learn
 *
 * <p>Address:
 *
 * <p>Date: 2024/10/20 16:38
 *
 * <p>@Author: yang shengxiong
 *
 * <p>Description: The current project is based on what I learned while working at Hikvision.
 *
 * <p>Copyright (c) 2024. 2002-2024@Hangzhou yangshengxiong Co., Ltd. All Right Reserved.
 */
public class CalciteParserDemo {
    public static void main(String[] args) throws SqlParseException {
        // 提供SQL语句
        final String sql = "select * from test where id = 1";
        // 生成sql解析配置
        SqlParser.Config config =
                SqlParser.config()
                        .withParserFactory(SqlParserImpl.FACTORY)
                        .withUnquotedCasing(Casing.UNCHANGED)
                        .withQuotedCasing(Casing.UNCHANGED)
                        .withCaseSensitive(false)
                        .withConformance(SqlConformanceEnum.MYSQL_5);
        // 创建一个SQL解析器
        SqlParser parser = SqlParser.create(sql, config);
        SqlAbstractParserImpl parserArray =
                config.parserFactory().getParser(new SourceStringReader(sql));

        // 执行SQL解析，查看解析的AST
        SqlNode sqlNode = parser.parseQuery();
        SqlAbstractParserImpl.Metadata metadata = parser.getMetadata();
        System.out.println(sqlNode);
        System.out.println(metadata);
        // 将sqlNode输出为sql，进行方言转换
        String sqlString = sqlNode.toSqlString(OracleSqlDialect.DEFAULT).toString();
        System.out.println(sqlString);
    }
}
