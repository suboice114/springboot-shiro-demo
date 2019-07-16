package com.example.springbootshirodemo.realm;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CredentialMatcher extends SimpleCredentialsMatcher {


    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        System.out.println("doCredentialsMatch:"+token);

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String password = new String(usernamePasswordToken.getPassword());
        String dbPassword =  (String)info.getCredentials();

        System.out.println("doCredentialsMatch:"+password.toString()+" "+dbPassword);

        return this.equals(password,dbPassword);
    }
}
