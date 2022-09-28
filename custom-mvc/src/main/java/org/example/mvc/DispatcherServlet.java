package org.example.mvc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.mvc.Controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.View;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping hm;

    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;


    @Override
    public void init() throws ServletException {
        RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
        requestMappingHandlerMapping.init();
        hm = requestMappingHandlerMapping;
        handlerAdapters = List.of(new SimpleControllerHandlerAdapter());
        viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        log.info("[DispatcherServlet] service started");

        try {
            // 요청이 들어오면 핸들러를 선택
            Object handler = hm.findHandler(
                new HandlerKey(RequestMethod.valueOf(req.getMethod()), req.getRequestURI()));

            // 해당 핸들러를 지원해주는 어댑터를 찾음
            HandlerAdapter handlerAdapter = handlerAdapters.stream()
                .filter(ha -> ha.supports(handler))
                .findFirst()
                .orElseThrow(
                    () -> new ServletException("No adapter for handler [" + handler + "]"));

            ModelAndView modelAndView = handlerAdapter.handle(req, resp, handler);

//            String viewName = handler.handleRequest(req, resp);
//            RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
//            requestDispatcher.forward(req, resp);
            // view resolver로 리팩토링
            // forward vs redirect
            for (ViewResolver viewResolver : viewResolvers) {
                // view resolver를 선택
                View view = viewResolver.resolveView(modelAndView.getViewName());
                view.render(modelAndView.getModel(), req, resp);
            }
        } catch (Exception e) {
            log.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
