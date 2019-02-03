package com.tupperware.mgt.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by dhunter on 2018/6/27.
 */

public class ObjectUtil {
    public static <A, B> B modelA2B(A modelA, Class<B> bClass) {
        try {
            Gson gson = new Gson();
            String gsonA = gson.toJson(modelA);
            B instanceB = gson.fromJson(gsonA, bClass);
            return instanceB;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * json转实例
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static  <T> T fromJson(String json , Type type){
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化json
     * @param object
     * @return
     */
    public static String jsonFormatter(Object object){
        if(object == null ){
            return "";
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJsonString = gson.toJson(object);
        return prettyJsonString;
    }
}
