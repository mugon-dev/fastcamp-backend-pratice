package org.example.servlet;

import org.example.calculate.Calculator;
import org.example.calculate.PositiveNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/calculate")
//public class CalculatorServlet extends Servlet {
public class CalculatorServlet extends GenericServlet {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorServlet.class);
//    private ServletConfig servletConfig;
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        logger.info("init");
//        this.servletConfig = config;
//    }
//
//    @Override
//    public ServletConfig getServletConfig() {
//        return this.servletConfig;
//    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        logger.info("service");
        int operand1 = Integer.parseInt(req.getParameter("operand1"));
        int operand2 = Integer.parseInt(req.getParameter("operand2"));
        String operator = req.getParameter("operator");
        int result = Calculator.calculate(new PositiveNumber(operand1),operator,new PositiveNumber(operand2));
        PrintWriter writer = res.getWriter();
        writer.println(result);
    }

//    @Override
//    public String getServletInfo() {
//        return null;
//    }
//
//    @Override
//    public void destroy() {
//
//    }
}
