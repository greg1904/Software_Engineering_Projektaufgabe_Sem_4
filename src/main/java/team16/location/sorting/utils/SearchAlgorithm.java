package team16.location.sorting.utils;

import team16.Application;
import team16.base.Configuration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class SearchAlgorithm implements ISearchAlgorithm {
    private String currentSearchAlgorithmPath;
    private Object algorithmInstance;

    public SearchAlgorithm(String currentSearchAlgorithmPath) {
        this.currentSearchAlgorithmPath = currentSearchAlgorithmPath;
    }

    public static Object build(String archivePath) {
        Object archiveInstance = null;

        try {
            URL[] urls = {new File(archivePath).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
            Class<?> archiveClass = Class.forName("Application", true, urlClassLoader);

            archiveInstance = archiveClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archiveInstance;
    }

    @Override
    public boolean checkPackage(String stringOfContents) {
        if (!this.currentSearchAlgorithmPath.equals(Configuration.instance.searchAlgorithmJarPath) || algorithmInstance == null) {
            algorithmInstance = build(Configuration.instance.searchAlgorithmJarPath);
        }
        return invokeSearchMethod(stringOfContents);
    }

    private boolean invokeSearchMethod(String content) {
        try {
            Method searchMethod = algorithmInstance.getClass().getDeclaredMethod("checkPackage", String.class);
            return (boolean) searchMethod.invoke(algorithmInstance, content);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("An Invokation Error has occured.");
    }
}
