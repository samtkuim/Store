package com.sam.store.web;

import com.sam.store.pojo.Customer;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginCheckFilter", urlPatterns = {"*.jsp", "/controller"})
public class LoginCheckFilter implements Filter {



    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        Customer customer = (Customer) request1.getSession().getAttribute("customer");

        String action = request1.getParameter("action");

        if(customer == null && !"login".equals(action) && !"reg_init".equals(action) && !"reg".equals(action)){
            request1.getRequestDispatcher("login.jsp").forward(request1, response1);
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
