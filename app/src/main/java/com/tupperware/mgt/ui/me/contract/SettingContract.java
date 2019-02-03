package com.tupperware.mgt.ui.me.contract;

import android.graphics.Bitmap;

/**
 * Created by umt041 on 2019/1/11.
 */
public class SettingContract {

    public  interface  View{
        void exitSuccess();
        void changePswSuccess();
        void updateImageCode(Bitmap bitmap);
        void toast(String msg);
        void hideDialog();
    }


    public interface Presenter{
        void exitApp();
        void changePswAction(String psw,String newPsw,String code);
        void getImageCode();
    }
}
