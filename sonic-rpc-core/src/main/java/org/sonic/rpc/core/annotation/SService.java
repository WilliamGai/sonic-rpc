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

	Class<?> interfaceClass() default void.class;

	String interfaceName() default "";

	String version() default "";

	String group() default "";

	String path() default "";

	boolean export() default false;

	String token() default "";

	boolean deprecated() default false;

	boolean dynamic() default false;

	String accesslog() default "";

	int executes() default 0;

	boolean register() default false;

	int weight() default 0;

	String document() default "";

	int delay() default 0;

	String local() default "";

	String stub() default "";

	String cluster() default "";

	String proxy() default "";

	int connections() default 0;

	int callbacks() default 0;

	String onconnect() default "";

	String ondisconnect() default "";

	String owner() default "";

	String layer() default "";

	int retries() default 0;

	String loadbalance() default "";

	boolean async() default false;

	int actives() default 0;

	boolean sent() default false;

	String mock() default "";

	String validation() default "";

	int timeout() default 0;

	String cache() default "";

	String[] filter() default {};

	String[] listener() default {};

	String[] parameters() default {};

	String application() default "";

	String module() default "";

	String provider() default "";

	String[] protocol() default {};

	String monitor() default "";

	String[] registry() default {};

}