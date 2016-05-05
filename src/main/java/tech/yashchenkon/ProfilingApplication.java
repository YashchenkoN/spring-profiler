package tech.yashchenkon;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.yashchenkon.bpp.ProfilingAnnotationBeanPostProcessor;
import tech.yashchenkon.service.TestService;

@Configuration
@ComponentScan(basePackageClasses = ProfilingApplication.class)
public class ProfilingApplication {

	@Bean
	public BeanPostProcessor profilingBeanPostProcessor() throws Exception {
		return new ProfilingAnnotationBeanPostProcessor();
	}

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProfilingApplication.class);
		TestService bean = applicationContext.getBean(TestService.class);
		while (true) {
			Thread.sleep(1000);
			bean.method();
		}
	}
}
