package com.gbase.exceptions;

public class AuthorizationException extends Exception{
    public AuthorizationException(){
        System.out.println("登录失败");
    }
}
