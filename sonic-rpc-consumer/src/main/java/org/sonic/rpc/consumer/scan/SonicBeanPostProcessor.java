package org.sonic.rpc.consumer.scan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.annotation.SReference;
import org.sonic.rpc.core.proxy.ConsumerProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/***
 * 修改注入IOC的逻辑,将SReference注解的属性用代理类替换
 * @author bao
 * @date 2017年8月18日 下午6:40:15
 */
@Component
public class SonicBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		LogCore.BASE.info("beanName={}, bean={}", beanName, bean.getClass());
		// 强制检索属性有无SReference
		if (hasAnnotation(bean.getClass().getAnnotations(), SReference.class.getName())) {
			Class<?> beanClass = bean.getClass();
			do {
				Field[] fields = beanClass.getDeclaredFields();
				for (Field field : fields) {
					setField(bean, field);
				}
			} while ((beanClass = beanClass.getSuperclass()) != null);
		} else {
			processMyInject(bean);
		}
		return bean;
	}

	private void processMyInject(Object bean) {
		Class<?> beanClass = bean.getClass();
		do {
			Field[] fields = beanClass.getDeclaredFields();
			for (Field field : fields) {
				if (!hasAnnotation(field.getAnnotations(), SReference.class.getName())) {
					continue;
				}
				setField(bean, field);
			}
		} while ((beanClass = beanClass.getSuperclass()) != null);
	}

	private void setField(Object bean, Field field) {
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			// field.set(bean, applicationContext.getBean(field.getType()));
			field.set(bean, applicationContext.getBean(ConsumerProxyFactory.class).create(field.getType()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean hasAnnotation(Annotation[] annotations, String annotationName) {
		if (annotations == null) {
			return false;
		}
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().equals(annotationName)) {
				return true;
			}
		}
		return false;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
