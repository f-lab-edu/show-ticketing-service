package com.show.showticketingservice.tool.aspect;

import com.show.showticketingservice.model.enumerations.DatabaseServer;
import com.show.showticketingservice.tool.annotation.DatabaseSource;
import com.show.showticketingservice.tool.util.datasource.RoutingDataSourceManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DatabaseSourceAspect {

    @Before("@annotation(com.show.showticketingservice.tool.annotation.DatabaseSource)")
    public void setDatabaseSource(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DatabaseServer source = method.getAnnotation(DatabaseSource.class).value();

        RoutingDataSourceManager.setDataSource(source);
    }

}
