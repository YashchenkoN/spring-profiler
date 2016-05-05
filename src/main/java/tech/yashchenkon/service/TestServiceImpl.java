package tech.yashchenkon.service;

import org.springframework.stereotype.Component;
import tech.yashchenkon.annotation.Profiling;

/**
 * @author Nikolay Yashchenko
 */
@Component
@Profiling
public class TestServiceImpl implements TestService {

    public void method() {
        System.out.println("METHOD");
    }
}
