package org.moon.test;

public class SingletonDemo {

    private static SingletonDemo instance ;

    private static String lock = "lock";

    private SingletonDemo(){}

    public static SingletonDemo getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

}
