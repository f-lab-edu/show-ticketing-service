package com.show.showticketingservice.tool.annotation;

import com.show.showticketingservice.model.enumerations.AccessRoles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAuthenticationNecessary {

    AccessRoles role() default AccessRoles.ALL_USER;

}
