package com.example.springbootshirodemo.realm;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

//启动时，先运行configuration注解的类
@Configuration
public class ShiroConfiguration {
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager){
        System.out.println("shiroFilter");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);

        bean.setLoginUrl("/login");
        bean.setSuccessUrl("/index");
        bean.setUnauthorizedUrl("/unauthorized");

        //请求拦截 key-访问请路径  value-使用的拦截方式
        LinkedHashMap<String, String> filterChainDefinittionMap = new LinkedHashMap<>();
        filterChainDefinittionMap.put("/index","authc");//需要通过验证
        filterChainDefinittionMap.put("/login","anon");//不需要验证
        filterChainDefinittionMap.put("/loginUser","anon");//不需要验证
        filterChainDefinittionMap.put("/admin","roles[admin]");//只允许admin用户
        filterChainDefinittionMap.put("/add","perms[add]");//只允许权限又add的用户
        filterChainDefinittionMap.put("/druid/**", "anon");// 数据库监控
        filterChainDefinittionMap.put("/**","user");//需要验证--已经登录的用户
        bean.setFilterChainDefinitionMap(filterChainDefinittionMap);
        return bean;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm){
        System.out.println("securityManager");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    @Bean("authRealm")
    public AuthRealm authRealm(@Qualifier("credentialMatcher") CredentialMatcher matcher){
        System.out.println("authRealm");
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCacheManager(new MemoryConstrainedCacheManager());//启用缓存
        authRealm.setCredentialsMatcher(matcher);
        return  authRealm;
    }

    @Bean("credentialMatcher")
    public CredentialMatcher credentialMatcher(){
        System.out.println("Credentialmatcher");
        return new CredentialMatcher();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager){
        System.out.println("authorizationAttributeSourceAdvisor");
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        System.out.println("defaultAdvisorAutoProxyCreator");
        DefaultAdvisorAutoProxyCreator creater = new DefaultAdvisorAutoProxyCreator();
        creater.setProxyTargetClass(true);
        return creater;
    }
}
