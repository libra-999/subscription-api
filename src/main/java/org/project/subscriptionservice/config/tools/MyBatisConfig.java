package org.project.subscriptionservice.config.tools;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {

    private static final ThreadLocal<Long> TABLE_KEY = new ThreadLocal<>();
    private static final List<String> SUB_TABLES = List.of("BILL_SUB");

    @Bean
    MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        DynamicTableNameInnerInterceptor tableNameInnerInterceptor = new DynamicTableNameInnerInterceptor((sql, tableName) -> {

            if (SUB_TABLES.contains(tableName)) {
                Long suffix = TABLE_KEY.get();
                if (suffix > 0) {
                    return tableName + "_" + suffix;
                }
            }
            return tableName;
        });

        interceptor.addInnerInterceptor(tableNameInnerInterceptor);
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.ORACLE_12C);
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    @Bean
    public IKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }
}
