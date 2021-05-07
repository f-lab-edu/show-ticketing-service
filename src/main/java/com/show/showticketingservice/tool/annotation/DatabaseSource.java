package com.show.showticketingservice.tool.annotation;

import com.show.showticketingservice.model.enumerations.DatabaseServer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseSource {

    DatabaseServer value() default DatabaseServer.MASTER;

}
