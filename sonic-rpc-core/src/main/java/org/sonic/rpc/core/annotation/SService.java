package org.sonic.rpc.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SService
 * 如果添加@Component 这个注解就可以帮我们的注释能够被Spring解析并放到IOC容器中<br>
 * 但是这个包不希望依赖Spring,也希望别的IOC框架可以自己实现注入。因此在提供者的地方需要实现SService接口的注入
 * @export 服务的发布
 * @author bao
 * @date 2017年8月10日 下午2:53:00
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@Inherited
public @interface SService {
}