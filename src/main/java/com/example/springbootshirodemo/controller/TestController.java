package com.example.springbootshirodemo.controller;

import com.example.springbootshirodemo.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin success";
    }

    @RequestMapping("/add")
    @ResponseBody
    public String add(){
        return "edit success";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            subject.logout();
        }
        return "login";
    }

}
