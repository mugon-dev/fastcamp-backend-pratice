package org.example.mvc;

import java.util.HashMap;
import java.util.Map;
import org.example.mvc.Controller.Controller;
import org.example.mvc.Controller.ForwardController;
import org.example.mvc.Controller.HomeController;
import org.example.mvc.Controller.RequestMethod;
import org.example.mvc.Controller.UserCreateController;
import org.example.mvc.Controller.UserListController;

public class RequestMappingHandlerMapping implements HandlerMapping {

    // key : [urlPath] value : [controller]
    private final Map<HandlerKey, Controller> mappings = new HashMap<>();

    // 어떠한 경로도 설정하지 않으면
    void init() {
        mappings.put(new HandlerKey(RequestMethod.GET, "/"), new HomeController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/users"), new UserListController());
        mappings.put(new HandlerKey(RequestMethod.POST, "/users"), new UserCreateController());
        mappings.put(new HandlerKey(RequestMethod.GET, "/user/form"),
            new ForwardController("/user/form.jsp"));

    }

    // url로 컨트롤러 찾기
    public Controller findHandler(HandlerKey handlerKey) {
        return mappings.get(handlerKey);
    }

}
