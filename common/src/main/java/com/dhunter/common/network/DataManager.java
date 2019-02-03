package com.dhunter.common.network;

import android.content.Context;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by dhunter on 2018/6/25.
 */

@Singleton
public class DataManager {

    private HttpHelper httpHelper;

    private SharePreferenceHelper sharePreferenceHelper;

    private DataBaseHelper dataBaseHelper;

    private Context context;


    @Inject
    public DataManager(Context context , HttpHelper httpHelper , SharePreferenceHelper sharePreferenceHelper
            , DataBaseHelper dataBaseHelper) {
        this.context = context;
        this.httpHelper = httpHelper;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.dataBaseHelper = dataBaseHelper;
    }



    public <S> S getService(Class<S> serviceClass){
        return httpHelper.getService(serviceClass);
    }

    public void setHeader(boolean isSkipToken) {
        httpHelper.setHeader(isSkipToken);
    }

    public void saveSPData(String key , String value){
        sharePreferenceHelper.saveData(key , value);
    }

    public void saveSPMapData(Map<String,String> map){
        sharePreferenceHelper.saveData(map);
    }

    public void saveSPObjectData(String key, Object obj) {
        sharePreferenceHelper.saveObjectData(key, obj);
    }

    public void saveSPObjectData(String key, Object obj, String fileName) {
        sharePreferenceHelper.saveObjectData(key, obj, fileName);
    }

    public String getSPData(String key){
        return sharePreferenceHelper.getValue(key);
    }

    public Object getSpObjectData(String key, Object defaultValue) {
        return sharePreferenceHelper.getObjectData(key, defaultValue);
    }

    public Object getSpObjectData(String key, Object defaultValue,String fileName) {
        return sharePreferenceHelper.getObjectData(key, defaultValue, fileName);
    }

    public void remove(String key) {
        sharePreferenceHelper.remove(key);
    }

    public void deleteSPData(){
        sharePreferenceHelper.deletePreference();
    }

    public Map<String ,String> getSPMapData(){
        return sharePreferenceHelper.readData();
    }


    public Context getContext() {
        return context;
    }

}
