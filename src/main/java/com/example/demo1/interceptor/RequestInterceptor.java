package com.example.demo1.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private String getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        else {
            return "anonymous";
        }
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String username = getAuthenticatedUser();
        System.out.println("PREHANDLE: username:" + username + ", Method:"+ request.getMethod()
                + ", requestURL:" + request.getRequestURL() + ", Time:" + LocalDateTime.now());
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String username = getAuthenticatedUser();
        if(ex!=null){
            System.out.println("AfterCompletion: username:" + username + ", requestURL:" + request.getRequestURL()
                    + ", exception :" + ex.getMessage() + ", Time:" + LocalDateTime.now());
        }
        else
        {
            System.out.println("AfterCompletion: username:" + username + ", Status:" +response.getStatus() + ", requestURL:"
                    + request.getRequestURL() + ", Completed Time:" + LocalDateTime.now());
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  {
        String username = getAuthenticatedUser();
        System.out.println("POSTHANDLE: Username:" + username +  ", URL:" + request.getRequestURL() + ", Status :"
                + response.getStatus() + ", Time:" + LocalDateTime.now());
    }

}
