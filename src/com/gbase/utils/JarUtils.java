package com.gbase.utils;

import com.gbase.Exceptions.LoadJarException;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Driver;
import java.util.*;

/**
 * @ClassName: JarUtils.class
 * @Description: 本类封装了加载驱动过程中若干个关键方法。
 */
public class JarUtils {
    /**
     * @return List<String>，返回jar包加载路径下的所有jar包名
     * @Description: 本方法用于查找jar包加载路径下所有的jar包，并将包名存于list中
     */
    public static List<String> getAllJars() {
        List<String> list = new ArrayList<>();
        File libDir = new File(ConnectionUtils.EXTERNAL_JAR_PATH);
        File[] files = libDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && (file.getName().endsWith(".jar"))) {
                    list.add(file.getName());
                }
            }
        }
        return list;
    }

    /**
     * @param jarName 要进行加载的jar包文件名
     * @Description: 本方法用于加载不在classpath中的某一个jar包
     */
    public static void loadJar(String jarName) throws LoadJarException {
        String jarPath = ConnectionUtils.EXTERNAL_JAR_PATH + jarName;
        File jarFile = new File(jarPath);
        // 从URLClassLoader类中获取类所在文件夹的方法，jar也可以认为是一个文件夹
        Method method = null;
        try {
            method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        } catch (NoSuchMethodException | SecurityException e1) {
            e1.printStackTrace();
        }
        //获取方法的访问权限以便写回
        boolean accessible = method.isAccessible();
        try {
            method.setAccessible(true);
            // 获取系统类加载器
            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            URL url = jarFile.toURI().toURL();
            method.invoke(classLoader, url);
        } catch (Exception e) {
            throw new LoadJarException();
        } finally {
            method.setAccessible(accessible);
        }
    }

    /**
     *
     * @return Map<String, String> HashMap中key为数据库产品名，value为驱动类名
     */
    public static Map<String, String> getJDBCDriverContents() {
        Map<String, String> map = new HashMap<>();
        File directory = new File(ConnectionUtils.EXTERNAL_JAR_PATH);
        // 获取目录下的所有JAR文件
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
        if (files != null) {
            try {
                // 创建一个URLClassLoader来加载JAR文件
                URL[] urls = new URL[files.length];
                for (int i = 0; i < files.length; i++) {
                    urls[i] = files[i].toURI().toURL();
                }
                URLClassLoader classLoader = new URLClassLoader(urls);
                // 使用URLClassLoader加载驱动类
                ServiceLoader<Driver> drivers = ServiceLoader.load(java.sql.Driver.class, classLoader);
                // 遍历每个驱动类并获取其内容
                for (java.sql.Driver driver : drivers) {
                    String value = driver.toString().split("@")[0];
                    String key = value.split("\\.")[1];
                    map.put(key, value);
                }
            } catch (Exception e) {
                // 处理异常
                e.printStackTrace();
            }
        }
        return map;
    }
}
