package org.xiem.com.jetty002.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义处理bean的ID注解 包含ID前缀包，ID名称 ID前缀包默认值"com.dz"
 * 
 * @author y
 */
@Target(ElementType.TYPE) // 放在哪
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HBID {
	public String id();

	public String catalog() default "com.dz";
}