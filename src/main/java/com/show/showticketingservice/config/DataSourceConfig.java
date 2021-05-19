package com.show.showticketingservice.config;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.tool.util.datasource.RoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:/application-local.properties")
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource slaveDataSource) {

        AbstractRoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSources = new HashMap<>();

        dataSources.put(DatabaseServer.MASTER, masterDataSource);
        dataSources.put(DatabaseServer.SLAVE, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    /*
        LazyConnectionDataSourceProxy
        - Spring의 Transaction은 트랜잭션 실행과 동시에 Connection 객체를 확보 & 해당 트랜잭션은 기본 DataSource를 이용
        - LazyConnectionDataSourceProxy는 트랜잭션 실행 후 첫번째 Statement가 생성될 때까지 JDBC Connection fetch를 늦춤
        - 상황에 맞게 DataSource를 분기하기 위해 사용
    */
    @Bean
    public DataSource lazyRoutingDataSource(@Qualifier("routingDataSource") DataSource dataSource) {
        return new LazyConnectionDataSourceProxy(dataSource);
    }

}
