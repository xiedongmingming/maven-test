package org.xiem.com.jetty002.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ���崦��bean��IDע�� ����IDǰ׺����ID���� IDǰ׺��Ĭ��ֵ"com.dz"
 * 
 * @author y
 */
@Target(ElementType.TYPE) // ������
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HBID {
	public String id();

	public String catalog() default "com.dz";
}