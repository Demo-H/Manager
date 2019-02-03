package com.dhunter.common.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.dhunter.common.config.GlobalConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dhunter on 2018/6/25.
 */

@Singleton
public class SharePreferenceHelper{
    private Context context;
    private static final String TAG = "SharePreferenceHelper";
    @Inject
    public SharePreferenceHelper(Context context) {
        this.context = context;
    }

    private SharedPreferences.Editor getSharePreferenceEditor(String fileName) {
        return getSharedPreferences(fileName).edit();
    }

    private SharedPreferences getSharedPreferences(String fileName){
        return context.getSharedPreferences(TextUtils.isEmpty(fileName) ? GlobalConfig.SHARE_PREFERENCE_FILE_NAME : fileName, Context.MODE_PRIVATE);
    }

    /*
     * 存数据
     */
    public void saveData(Map<String ,String> maps){
        this.saveData(maps,null);
    }


    public void saveData(Map<String ,String> maps,String fileName){
        if(null == maps || maps.size() == 0)return;
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        for(Map.Entry<String, String> map:maps.entrySet()){
            String key = map.getKey();
            String value = map.getValue();
            Log.d(TAG, "存数据==key is "+key+",value is "+value);
            editor.putString(key, value);
        }
        editor.commit();
    }

    public void saveData(String key,String value){
        this.saveData(key,value,null);
    }

    public void saveData(String key,String value,String fileName){
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        editor.putString(key, value);
        editor.commit();
    }

    public void saveObjectData(String key, Object obj) {
        HashMap<String ,Object> map = new HashMap<>();
        map.put(key ,obj);
        this.setObjectData(map, null);
    }

    public void saveObjectData(String key, Object obj, String fileName) {
        HashMap<String ,Object> map = new HashMap<>();
        map.put(key ,obj);
        this.setObjectData(map, fileName);
    }

    public void setObjectData(HashMap<String ,Object> values, String fileName) {
        SharedPreferences.Editor editor = getSharePreferenceEditor(fileName);
        Set<String> keys = values.keySet();
        for(String tempKey :keys){
            Object value = values.get(tempKey);
            String type;
            if(value != null && value.getClass() != null) {
                type = value.getClass().getSimpleName();
            } else {
                type = "String";
            }

            if("String".equals(type)){
                editor.putString(tempKey, (String)value);
            }
            else if("Integer".equals(type)){
                editor.putInt(tempKey, (Integer)value);
            }
            else if("Boolean".equals(type)){
                editor.putBoolean(tempKey, (Boolean)value);
            }
            else if("Float".equals(type)){
                editor.putFloat(tempKey, (Float)value);
            }
            else if("Long".equals(type)){
                editor.putLong(tempKey, (Long)value);
            }
        }
        editor.commit();
    }

    /**
     * 根据defaultValue的类型返回相应的类型值,默认用的sp的文件名字为DEFAULT_FILE_NAME
     * @param key
     * @param defaultValue
     * @return
     */
    public Object getObjectData(String key, Object defaultValue) {
        return getObjectParam(key, defaultValue, null);
    }

    public Object getObjectData(String key, Object defaultValue, String fileName) {
        return getObjectParam(key, defaultValue, fileName);
    }

    public Object getObjectParam(String key, Object defaultValue, String fileName){
        SharedPreferences sp = getSharedPreferences(fileName);
        String type;
        if(defaultValue != null && defaultValue.getClass() != null) {
            type = defaultValue.getClass().getSimpleName();
        } else {
            type = "String";
        }

        if("String".equals(type)){
            return sp.getString(key, (String)defaultValue);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultValue);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultValue);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultValue);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultValue);
        }
        return null;
    }

    public String getValue(String key){
        if(null==key)
            return null;
        return this.getValue(key ,null);
    }

    public String getValue(String key,String fileName){
        if(null==key)
            return null;
        return getSharedPreferences(fileName).getString(key, null);
    }


    /*
     * 读数据，返回一个Map<String, String>
     */
    public Map<String, String> readData(){
        return this.readData(null);
    }

    public Map<String, String> readData(String fileName){
        return (Map<String, String>)getSharedPreferences(fileName).getAll();
    }

    public void remove(String key) {
        remove(null, key);
    }

    public void remove(String fileName, String key) {
        if (!getSharedPreferences(fileName).contains(key))
            return;
        getSharedPreferences(fileName).edit().remove(key).apply();
    }

    /*
     * 根据文件名删除文件里的数据
     */
    public void deletePreference(){
        this.deletePreference(null);
    }

    public void deletePreference(String fileName){
        getSharedPreferences(fileName).getAll().clear();
        getSharePreferenceEditor(fileName).clear().commit();
    }
}