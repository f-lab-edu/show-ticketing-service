package com.show.showticketingservice.config;

import com.show.showticketingservice.model.enumerations.RatingType;
import com.show.showticketingservice.model.enumerations.ShowType;
import com.show.showticketingservice.model.enumerations.UserType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.show.showticketingservice.mapper")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "routingDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("mybatis/mappers/*.xml"));
        sqlSessionFactoryBean.setTypeHandlers(new UserType.TypeHandler(), new ShowType.TypeHandler(), new RatingType.TypeHandler());

        return sqlSessionFactoryBean.getObject();
    }

}
