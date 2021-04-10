package team16.location.sorting.utils;

import team16.Application;
import team16.base.Configuration;
import team16.data.datainstances.packages.Package;
import team16.data.utils.ISearchAlgorithm;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class SearchAlgorithm implements ISearchAlgorithm {
//    BOYERMOORE(new BoyerMooreSearchAlgorithm()), RABINKARP(new RabinKarpSearchAlgorithm());

    private String currentSearchAlgorithmPath;
    private Object algorithmInstance;

    public SearchAlgorithm(String currentSearchAlgorithmPath) {
        this.currentSearchAlgorithmPath = currentSearchAlgorithmPath;
    }

    @Override
    public boolean checkPackage(Package packet) {
        if(this.currentSearchAlgorithmPath.equals(Configuration.instance.searchAlgorithmJarPath) && algorithmInstance != null){
            return invokeSearchMethod(packet.getContentAsString());
        }else{
            algorithmInstance = build(Configuration.instance.searchAlgorithmJarPath);
            return invokeSearchMethod(packet.getContentAsString());
        }
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

    public static Object build(String archivePath){
        Object archiveInstance = null;

        try {
            System.out.println("Loading file: " + archivePath);
            URL[] urls = {new File(archivePath).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, Application.class.getClassLoader());
            Class<?> archiveClass = Class.forName("Application", true, urlClassLoader);

            archiveInstance = archiveClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archiveInstance;
    }
}
