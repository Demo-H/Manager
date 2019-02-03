package com.dhunter.system.baseutil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by gohonglin on 2017/7/27.
 */

public class ReflectUtil {

    public static Class getClass(String className){

        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Field getField(String className, String fieldName){
        return getField(getClass(className),fieldName);
    }

    public static Field getField(Class<?>clazz,String fieldName){
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    public static void invokeClassMethod(Object object,String methodName,Object... methodParams){
        try {
            Class<?> clazz = object.getClass();
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void changedPrivateData(Object object,String privateDataName,Object newValue){
        Field field = getField(object.getClass(),privateDataName);
        field.setAccessible(true);

        try {
            field.set(object,newValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
