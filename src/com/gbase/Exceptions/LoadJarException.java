package com.gbase.Exceptions;

public class LoadJarException extends Exception {
    public LoadJarException() {
        System.err.println("加载驱动失败,请检查驱动类名是否正确以及jar文件已正确放置在jar目录下");
    }
}
