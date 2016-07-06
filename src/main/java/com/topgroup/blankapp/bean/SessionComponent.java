package com.topgroup.blankapp.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SessionComponent {

	String value() default "";

}
