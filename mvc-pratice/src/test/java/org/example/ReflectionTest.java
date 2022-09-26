package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.annotation.Controller;
import org.example.annotation.Service;
import org.example.model.User;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Controller 애노테이션이 설정돼 있는 모든 클래스를 찾아서 출력
 */
public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    private static Set<Class<?>> getTypesAnnotatedWith(
        List<Class<? extends Annotation>> annotations) {
        Reflections reflections = new Reflections("org.example");
        Set<Class<?>> beans = new HashSet<>();
        annotations.forEach(
            annotation -> beans.addAll(reflections.getTypesAnnotatedWith(annotation)));
        return beans;
    }

    @Test
    void controllerScan() {
        Set<Class<?>> beans = getTypesAnnotatedWith(List.of(Controller.class, Service.class));
        logger.debug("beans: [{}]", beans);
    }

    @Test
    void showClass() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
        logger.debug("User all declared fields: [{}]",
            Arrays.stream(clazz.getDeclaredFields()).collect(
                Collectors.toList()));
        logger.debug("User all declared constructors: [{}]",
            Arrays.stream(clazz.getDeclaredConstructors()).collect(
                Collectors.toList()));
        logger.debug("User all declared methods: [{}]",
            Arrays.stream(clazz.getDeclaredMethods()).collect(
                Collectors.toList()));
    }

    // 힙 영역에 로드돼 있는 클래스 타입의 객체 가져오기
    @Test
    void load() throws ClassNotFoundException {
        // 1번
        Class<User> clazz = User.class;

        // 2번
        User user = new User("asdfasd", "asdfasdf");
        Class<? extends User> clazz2 = user.getClass();

        // 3번
        Class<?> clazz3 = Class.forName("org.example.model.User");

        assertThat(clazz == clazz2).isTrue();
        assertThat(clazz == clazz3).isTrue();
        assertThat(clazz3 == clazz2).isTrue();

    }
}
