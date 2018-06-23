package com.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ModelBind {
	
	/**
	 * 表名
	 * 
	 * 2016年1月3日 下午3:29:26
	 * flyfox 330627517@qq.com
	 * @return
	 */
	String table();

	/**
	 * 主键
	 * 
	 * 2016年1月3日 下午3:29:21
	 * flyfox 330627517@qq.com
	 * @return
	 */
	String key() default "id";
	
	/**
	 * 数据库名称
	 * 
	 * 2016年1月3日 下午3:29:10
	 * flyfox 330627517@qq.com
	 * @return
	 */
	String configName()  default "main";
}
