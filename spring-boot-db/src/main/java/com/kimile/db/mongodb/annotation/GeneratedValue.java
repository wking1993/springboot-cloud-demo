package com.kimile.db.mongodb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注意：自增主键ID的类型不要定义成包装类
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface GeneratedValue {
	
}
