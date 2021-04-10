package team16.configuration;

import team16.storage.packet.Package;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
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
            URL[] urls = {new File(archivePath).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, SearchAlgorithm.class.getClassLoader());
            Class<?> archiveClass = Class.forName("Application", true, urlClassLoader);

            archiveInstance = archiveClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return archiveInstance;
    }
}
