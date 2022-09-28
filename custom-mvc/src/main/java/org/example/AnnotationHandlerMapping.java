package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.example.mvc.AnnotationHandler;
import org.example.mvc.Controller.RequestMethod;
import org.example.mvc.HandlerKey;
import org.example.mvc.HandlerMapping;
import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.reflections.Reflections;

public class AnnotationHandlerMapping implements HandlerMapping {

    private final Map<HandlerKey, AnnotationHandler> handlers = new HashMap<>();
    private final Object[] basePackage;

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    // annotation을 찾기위해 reflection 사용
    public void initialize() {
        Reflections reflections = new Reflections(basePackage);
        // Controller 어노테이션 붙은 클래스 찾기
        Set<Class<?>> clazzesAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);
        clazzesAnnotatedWith.forEach(
            // 해당 클래스의 메소드 중에
            clazz -> Arrays.stream(clazz.getDeclaredMethods()).forEach(
                // RequestMapping 어노테이션이 붙은 메소드 찾기
                declaredMethod -> {
                    RequestMapping requestMapping = declaredMethod.getDeclaredAnnotation(
                        RequestMapping.class);

                    Arrays.stream(getRequestMethods(requestMapping))
                        .forEach(requestMethod -> handlers.put(
                            new HandlerKey(requestMethod, requestMapping.value()),
                            new AnnotationHandler(clazz, declaredMethod)
                        ));
                }
            )
        );


    }

    // RequestMapping의 request method 추출
    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        return requestMapping.method();
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        // 핸들러를 찾아서 넘김
        return handlers.get(handlerKey);
    }
}
