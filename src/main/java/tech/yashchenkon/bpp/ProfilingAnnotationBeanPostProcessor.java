package tech.yashchenkon.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import tech.yashchenkon.annotation.Profiling;
import tech.yashchenkon.utils.profiling.ProfilingInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikolay Yashchenko
 */
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> beans = new HashMap<>();
    private InvocationHandler profilerInvocationHandler;

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        profilerInvocationHandler = new ProfilingInvocationHandler();
    }

    @Override
    public Object postProcessBeforeInitialization(Object object, String beanName) throws BeansException {
        Class<?> objectClass = object.getClass();
        if (objectClass.isAnnotationPresent(Profiling.class)) {
            beans.put(beanName, objectClass);
        }
        return object;
    }

    @Override
    public Object postProcessAfterInitialization(Object object, String beanName) throws BeansException {
        Class<?> objectClass = beans.get(beanName);
        if (objectClass != null) {
            return Proxy.newProxyInstance(
                    objectClass.getClassLoader(), objectClass.getInterfaces(), profilerInvocationHandler::invoke
            );
        }
        return object;
    }
}
