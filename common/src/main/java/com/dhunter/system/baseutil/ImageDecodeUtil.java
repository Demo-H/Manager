package com.dhunter.system.baseutil;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

/**
 * Created by gohonglin on 2017/7/27.
 *
 * 如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
 */

public class ImageDecodeUtil {

    public 	static final int PIXELS_LEVEL_NORMAL = 1;
    public 	static final int PIXELS_LEVEL_HIGH = 2;
    private static final int PIXELS_LEVEL_ACCORDING_VIEW_SIZE =4;
    private static final int PIXELS_NORMAL_INSAMPLESIZE = 2;
    private static final int PIXELS_HIGH_INSAMPLESIZE = 1;


    public static Bitmap decodeResource(Context context, int id, Bitmap.Config rgb, int sampleSize)
    {
        Bitmap bm =null;
        Resources res = context.getResources();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = rgb;
        opt.inSampleSize = sampleSize;
        try{
            bm = BitmapFactory.decodeResource(res,id,opt);
        }catch(OutOfMemoryError e)
        {
            e.printStackTrace();
            System.gc();
            opt.inSampleSize = opt.inSampleSize*2;
            bm = BitmapFactory.decodeResource(res,id,opt);
        }
        return bm;
    }

        /*
        id: 图片资源的drawableId
        * return:drawable对象，返回的drawable对象的图片清晰度比较一般，但是比较省内存
        * */
    public static Drawable getDrawable(Context context, int id)
    {
        return getDrawable(context,id,Bitmap.Config.RGB_565,2);
    }

    /*
    * id:图片资源的drawableId
    * rgb: 生成drawable对象所使用的rgb值，目前这里支持使用RGB_565和ARGB_8888
    * samplesize:生成drawable对象所使用的BitmapFactory.Options的Options.inSampleSize
    * return:drawable对象，返回的drawable对象的图片清晰度根据配置的sampleSize生成
    * */
    public static Drawable getDrawable(Context context,int id,Bitmap.Config rgb,int sampleSize)
    {
        Drawable drawable = new BitmapDrawable(decodeResource(context,id,rgb,sampleSize));
        return drawable;
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * 功能：用于设置对图片清晰度要求没那么高的view的背景
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setViewBackground(Context context, View view, int drawableId)
    {
        setViewBackground(context,view,drawableId,PIXELS_NORMAL_INSAMPLESIZE);
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * pixelLevel：对设置的背景要求的像素级别，如果对图片清晰度要求没那么高的话，设置成PIXELS_LEVEL_NORMAL，如果要求比较高的话，设置成PIXELS_LEVEL_HIGH
    * 功能：设置view的背景，并且根据pixelLevel来生成相应清晰度的图片
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setViewBackground(Context context, View view, int drawableId, int pixelLevel)
    {
        switch(pixelLevel)
        {
            case PIXELS_LEVEL_HIGH:
                setViewBackground(context,view,drawableId,false,PIXELS_HIGH_INSAMPLESIZE);
                break;
            case PIXELS_LEVEL_NORMAL:
            default:
                setViewBackground(context,view,drawableId,false,PIXELS_NORMAL_INSAMPLESIZE);
                break;
        }
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * useRGB8888：是否使用ARGB8888来生成图片
    * 功能：设置view的背景，并且根据useRGB8888来生成相应的图片,如果为true的话，会以ARGB888和inSampleSize为1来生成图片，比较占用内存
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setViewBackground(Context context, View view, int drawableId, boolean useRGB8888)
    {
        if(useRGB8888)
            setViewBackground(context,view,drawableId,true,PIXELS_HIGH_INSAMPLESIZE);
        else
            setViewBackground(context,view,drawableId,false,PIXELS_NORMAL_INSAMPLESIZE);
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * viewHeight：view的高度
    * viewWidth：view 的宽度
    * 功能：根据view的高度和宽度，算出view的背景的inSampleSize值。如果不想浪费内存，又对图片清晰度有一定要求的话，建议使用此函数
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setViewBackground(Context context, View view, int drawableId, int viewHeight, int viewWidth)
    {
        if(view == null || context ==null)
            return;

        int inSamplesize = getBitmapInSampleSize(context, drawableId, viewHeight, viewWidth);
        setViewBackground(context,view,drawableId,false,inSamplesize);
    }


    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * useRGB8888：是否使用ARGB8888来生成图片
    * inSamplesize：生成drawable对象所使用的BitmapFactory.Options的Options.inSampleSize
    * 功能：设置view背景
    * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void setViewBackground(Context context, View view, int drawableId, boolean useRGB8888, int inSamplesize)
    {
        if(view == null || context ==null)
            return;
        if(!useRGB8888)
            view.setBackground(getDrawable(context, drawableId,Bitmap.Config.RGB_565,inSamplesize));
        else
            view.setBackground(getDrawable(context, drawableId,Bitmap.Config.ARGB_8888,inSamplesize));

    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * 功能：用于异步设置对图片清晰度要求没那么高的view的背景
    * */
    public static void setViewBackgroundNoUiThread(final Context context, final View view,final int drawableId)
    {
        setViewBackgroundNoUiThread(context,view,drawableId,false,PIXELS_NORMAL_INSAMPLESIZE);
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * pixelLevel：对设置的背景要求的像素级别，如果对图片清晰度要求没那么高的话，设置成PIXELS_LEVEL_NORMAL，如果要求比较高的话，设置成PIXELS_LEVEL_HIGH
    * 功能：异步设置view的背景，并且根据pixelLevel来生成相应清晰度的图片
    * */
    public static void setViewBackgroundNoUiThread(Context context, View view,int drawableId,int pixelLevel)
    {
        switch(pixelLevel)
        {
            case PIXELS_LEVEL_HIGH:
                setViewBackgroundNoUiThread(context, view, drawableId, false, PIXELS_HIGH_INSAMPLESIZE);
                break;
            case PIXELS_LEVEL_NORMAL:
            default:
                setViewBackgroundNoUiThread(context,view,drawableId,false,PIXELS_NORMAL_INSAMPLESIZE);
                break;
        }
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * useRGB8888：是否使用ARGB8888来生成图片
    * 功能：异步设置view的背景，并且根据useRGB8888来生成相应的图片,如果为true的话，会以ARGB888和inSampleSize为1来生成图片，比较占用内存
    * */
    public static void setViewBackgroundNoUiThread(final Context context, final View view,final int drawableId,boolean useRGB888)
    {
        if(useRGB888)
            setViewBackgroundNoUiThread(context, view, drawableId, true, PIXELS_HIGH_INSAMPLESIZE);
        else
            setViewBackgroundNoUiThread(context, view, drawableId, false, PIXELS_NORMAL_INSAMPLESIZE);
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * viewHeight：view的高度
    * viewWidth：view 的宽度
    * 功能：异步设置背景，根据view的高度和宽度，算出view的背景的inSampleSize值。如果不想浪费内存，又对图片清晰度有一定要求的话，建议使用此函数
    * */
    public static void setViewBackgroundNoUiThread(final Context context, final View view,final int drawableId,final int viewHeight,final int viewWidth)
    {
        if(view == null || context ==null)
            return;
        ThreadUtil.PostToRealtimeThread(new Runnable() {
            @Override
            public void run() {
                final int inSamplesize = getBitmapInSampleSize(context,drawableId,viewHeight,viewWidth);
                final Drawable drawable = getDrawable(context, drawableId, Bitmap.Config.RGB_565, inSamplesize);
                ThreadUtil.postToUIThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        view.setBackground(drawable);
                    }
                });
            }
        });
    }

    /*
    如果view的尺寸是wrap_content的话  ，请不要使用这里的setBackground，不然会导致view尺寸有变化
    * view:需要设置背景的view
    * drawableId：用于设置背景的图片的资源ID。
    * useRGB8888：是否使用ARGB8888来生成图片
    * inSamplesize：生成drawable对象所使用的BitmapFactory.Options的Options.inSampleSize
    * 功能：异步设置view背景
    * */
    public static void setViewBackgroundNoUiThread(final Context context, final View view,final int drawableId,final boolean useRGB8888,final int inSamplesize)
    {
        if(view == null || context ==null)
            return;
        ThreadUtil.PostToRealtimeThread(new Runnable() {
            @Override
            public void run() {
                final Drawable drawable;
                if(!useRGB8888)
                    drawable = getDrawable(context, drawableId, Bitmap.Config.RGB_565, inSamplesize);
                else
                    drawable = getDrawable(context, drawableId, Bitmap.Config.ARGB_8888,inSamplesize);
                ThreadUtil.postToUIThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void run() {
                        view.setBackground(drawable);
                    }
                });
            }
        });
    }

    private static int getBitmapInSampleSize(Context context,int drawabled,int viewHeight,int viewWidth)
    {
        Resources res = context.getResources();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,drawabled,opt);
        int BitmapHeight = opt.outHeight;
        int BitmapWidth = opt.outWidth;
        int inSampleSize = 1;
        if(BitmapHeight>viewHeight ||BitmapWidth> viewWidth)
        {
            int heightRatio = Math.round((float)BitmapHeight/(float)viewHeight);
            int widthRatio = Math.round((float)BitmapWidth/(float)viewWidth);
            int sampleSize = heightRatio < widthRatio?heightRatio:widthRatio;
        }
        return inSampleSize;
    }

}
