package org.sonic.rpc.provider.scan;

import java.util.Set;

import org.sonic.rpc.core.LogCore;
import org.sonic.rpc.core.annotation.SService;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

public class SonicScanner extends ClassPathBeanDefinitionScanner {

	public SonicScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public void registerDefaultFilters() {
		this.addIncludeFilter(new AnnotationTypeFilter(SService.class));
	}

	@Override
	public int scan(String... basePackages) {
		return super.scan(basePackages);
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		LogCore.BASE.info("all mine is{}", beanDefinitions);
		// for (BeanDefinitionHolder holder : beanDefinitions) {
		// GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
		// definition.getPropertyValues().add("innerClassName", definition.getBeanClassName());
		// definition.setBeanClass(FactoryBeanTest.class);
		// }
		return beanDefinitions;
	}

	@Override
	public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return super.isCandidateComponent(beanDefinition)
		            && beanDefinition.getMetadata().hasAnnotation(SService.class.getName());
	}
}
