package com.example.springbootshirodemo.controller;

import com.example.springbootshirodemo.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * loginUser
     */
    @RequestMapping("/loginUser")
    public String loginUser(HttpServletRequest request, HttpSession session){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user = (User) subject.getPrincipal();
            session.setAttribute("user",user);
            return "index";
        }catch (Exception e){
            return "login";
        }
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }
}
