package org.example.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String handleRequest(HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        return "home";
    }
}
