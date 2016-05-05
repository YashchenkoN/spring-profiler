package tech.yashchenkon.utils.profiling;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Nikolay Yashchenko
 */
public class ProfilingInvocationHandler implements InvocationHandler {

    private ProfilingSwitcherImpl profilingSwitcher = new ProfilingSwitcherImpl();

    public ProfilingInvocationHandler() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(profilingSwitcher, new ObjectName("profiling", "name", "switcher"));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (profilingSwitcher.isEnabled()) {
            // todo remove evil System.out.println
            System.out.println("PROFILING...");
            long before = System.nanoTime();
            Object result = method.invoke(proxy, args);
            long after = System.nanoTime();
            System.out.println("TIME: " + (after - before) + " ns");
            System.out.println("PROFILING DONE");
            return result;
        } else {
            return method.invoke(proxy, args);
        }
    }
}
