package tech.yashchenkon.bpp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import tech.yashchenkon.annotation.Profiling;
import tech.yashchenkon.utils.profiling.ProfilingSwitcherImpl;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikolay Yashchenko
 */
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final Log logger = LogFactory.getLog(this.getClass());
    private Map<String, Class> beans = new HashMap<>();
    private ProfilingSwitcherImpl profilingSwitcher = new ProfilingSwitcherImpl();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(profilingSwitcher, new ObjectName("profiling", "name", "switcher"));
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
                    objectClass.getClassLoader(), objectClass.getInterfaces(), (proxy, method, args) -> {
                        if (profilingSwitcher.isEnabled()) {
                            logger.debug("PROFILING...Method: " + method.getName());
                            long before = System.nanoTime();
                            Object result = method.invoke(object, args);
                            long after = System.nanoTime();
                            logger.debug("TIME: " + (after - before) + " ns");
                            logger.debug("PROFILING DONE");
                            return result;
                        } else {
                            return method.invoke(object, args);
                        }
                    }
            );
        }
        return object;
    }
}
