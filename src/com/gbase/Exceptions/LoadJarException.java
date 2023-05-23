package com.gbase.Exceptions;

public class LoadJarException extends Exception {
    public LoadJarException() {
        System.out.println("加载驱动失败,请检查驱动类名是否正确");
    }
}
