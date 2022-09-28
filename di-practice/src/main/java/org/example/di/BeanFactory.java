package org.example.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class BeanFactory {

    private final Set<Class<?>> preInstantiatedClazz;
    private final Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory(Set<Class<?>> preInstantiatedClazz) {
        this.preInstantiatedClazz = preInstantiatedClazz;
        initalize();
    }

    private void initalize() {
        for (Class<?> clazz : preInstantiatedClazz) {
            Object instance = createInstance(clazz);
            beans.put(clazz, instance);
        }
    }

    private Object createInstance(Class<?> clazz) {
        // 생성자
        Constructor<?> constructor = findConstructor(clazz);

        // 파라미터
        List<Object> parameters = new ArrayList<>();
        for (Class<?> typeClass : constructor.getParameterTypes()) {
            parameters.add(getParameterByClass(typeClass));
        }
        // 인스턴스 생성
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private Constructor<?> findConstructor(Class<?> clazz) {
        // 해당 클래스의 Inject 어노테이션이 붙은 constructor만 가져옴
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (Objects.nonNull(constructor)) {
            return constructor;
        }
        return clazz.getConstructors()[0];
    }

    private Object getParameterByClass(Class<?> typeClass) {
        Object instanceBean = getBean(typeClass);
        if (Objects.nonNull(instanceBean)) {
            return instanceBean;
        }
        // 재귀함수
        // 해당 클래스 생성하기 위해 다른 클래스가 먼저 생성될 필요가 있음
        // 생성자로 다른 클래스를 받기 때문
        // 재귀함수로 다른 클래스 생성할때까지 계속 돔
        return createInstance(typeClass);
    }

    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }
}
