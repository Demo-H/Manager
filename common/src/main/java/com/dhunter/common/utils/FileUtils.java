package com.dhunter.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;


/**
 * Created by dhunter on 2018/6/26.
 */

public class FileUtils {
    public static final String TAG = "FileUtils";
    public static final String APP_NAME = "TupManager";
    public static final String DIR_PUBLLIC_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_NAME;
    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    private static String DIR_LOG = DIR_PUBLLIC_ROOT + File.separator + "log";
    private static String DIR_TEMP = DIR_PUBLLIC_ROOT + File.separator + "temp";
    private static String DIR_CRASH_LOG = DIR_PUBLLIC_ROOT + File.separator + "crash_log";
    private static String DIR_DOWNLOAD = DIR_PUBLLIC_ROOT + File.separator + "download";
    private static String DIR_PHOTO_CRASH = DIR_PUBLLIC_ROOT + File.separator + "photos";
    private static String DIR_CACHE = DIR_PUBLLIC_ROOT + File.separator + "cache";
    private static String DIR_VIDEO = DIR_PUBLLIC_ROOT + File.separator + "video";
    private static String DIR_APP_XML = DIR_PUBLLIC_ROOT + File.separator + "app_xmls";

    /**
     * 存储路径方式
     */
    public enum PathType {
        SDCARD,                /**存储在SDCard中 **/
        LOG,                   /**存储在SDCard目录下的log中 **/
        TEMP,                  /**存储在SDCard目录下的temp中 **/
        CRASH_LOG,             /**存储在SDCard目录下的crash_log中 **/
        DOWNLOAD,              /**存储在SDCard目录下的download中 **/
        APP_PHOTOS,            /**存储在软件目录下的photos中 **/
        CACHE,                 /** 存储在软件Cache中**/
        MEDIA_DIR,            /**存储在多媒体文件夹下video **/
        APP_XML               /** 存储在软件DATA目录下的app_xmls中 **/
    }

    /**
     * 判断是否有SD卡
     * @return 是否有SD卡
     */
    public static boolean sdcardAvailable() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    public static void initSdcardDirs(Context context) {
        if(sdcardAvailable()) {
            initSdcardDirs(context, DIR_PUBLLIC_ROOT, APP_NAME);
        }
    }

    public static void initSdcardDirs(Context context, String sdcardPath, String appName) {
        Log.d(TAG, "init SDcard Dirs");
        createDir(sdcardPath);
        createDir(DIR_LOG);
        createDir(DIR_TEMP);
        createDir(DIR_CRASH_LOG);
        createDir(DIR_DOWNLOAD);
        createDir(DIR_PHOTO_CRASH);
        createDir(DIR_CACHE);
        createDir(DIR_VIDEO);
        createDir(DIR_APP_XML);
    }

    /**
     * 创建指定路径的文件夹
     * @param path 路径
     * @return 通过路径创建的File对象
     * @throws SecurityException 安全异常
     */
    public static File createDir(String path) throws SecurityException {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * 创建文件 若该文件存在删除该文件再创建，若不存在则创建
     *
     * @param file 需要创建的文件
     * @return 是否创建成功
     * @throws IOException IO异常
     */
    public static boolean createFile(File file) throws IOException {
        if (file.exists()) {
            deleteFile(file);
        }
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        return file.createNewFile();
    }

    /**
     * 创建文件 若该文件不存在则创建，若存在则不用处理
     *
     * @param file 需要创建的文件
     * @return 是否创建成功
     * @throws IOException IO异常
     */
    public static boolean createFileNotExist(File file) throws IOException {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            return file.createNewFile();
        } else {
            return false;
        }

    }

    /**
     * 判断文件是否存在
     *
     * @param strFile
     * @return 是否存在
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 通过pathType在使用时确定该文件是否存在
     * @param pathType
     */
    public static void fileIsExistsbyType(PathType pathType) {
        switch (pathType) {
            case SDCARD:
                createDir(DIR_PUBLLIC_ROOT);
                break;
            case LOG:
                createDir(DIR_LOG);
                break;
            case TEMP:
                createDir(DIR_TEMP);
                break;
            case CRASH_LOG:
                createDir(DIR_CRASH_LOG);
                break;
            case DOWNLOAD:
                createDir(DIR_DOWNLOAD);
                break;
            case APP_PHOTOS:
                createDir(DIR_PHOTO_CRASH);
                break;
            case CACHE:
                createDir(DIR_CACHE);
                break;
            case MEDIA_DIR:
                createDir(DIR_VIDEO);
                break;
            case APP_XML:
                createDir(DIR_APP_XML);
                break;
        }
    }

    /**
     * 删除指定文件
     *
     * @param file 需要删除的文件
     * @return
     */
    public static final boolean deleteFile(File file) {
        boolean result = true;
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    result &= deleteFile(children[i]);
                }
            }
        }
        result &= file.delete();
        return result;
    }

    /**
     * 删除文件夹或文件下面的文件
     *
     * @param f 需要删除的文件或文件夹
     */
    public static void deletE(File f) {
        File[] ff;
        int length;
        if (f.isFile()) {
            f.delete();
            return;
        } else if (f.isDirectory()) {
            ff = f.listFiles();
            if (ff != null) {
                length = ff.length;
                int i = 0;
                while (length != 0) {
                    deletE(ff[i]);
                    i++;
                    length--;
                }
                if (length == 0) {
                    f.delete();
                    return;
                }
            }
        }
    }


    /**
     * @param TimeInMillis    指定时间的时间戳，最好不要为当前获取的时间
     * @param f               需要清理子文件的文件夹或需要删除的文件
     * @param haveIgnore      是否有要忽略都文件
     * @param ignoreFilsNames 要忽略文件的开头字符串,haveIgnore为true时有效
     * @作用: 删除最后编辑时间在指定时间之前的文件或文件夹下面的文件(不包括文件夹)
     * @author: zcc
     */
    public static void deleteFilesByTime(final Long TimeInMillis, File f, final boolean haveIgnore, String... ignoreFilsNames) {
        if (TimeInMillis == null || f == null || ignoreFilsNames == null) {
            Log.d(TAG, "deleteFilesByTime() 参数为空！！！");
            return;
        }

        File[] ff;
        int length;
        if (f.isFile()) {
            if (f.lastModified() < TimeInMillis) {
                boolean isIgnoreFile = false;
                if (haveIgnore) {
                    for (int i = 0; i < ignoreFilsNames.length; i++) {
                        if (f.getName().startsWith(ignoreFilsNames[i])) {
                            isIgnoreFile = true;
                            break;
                        }
                    }
                } else {
                    isIgnoreFile = true;
                }
                if (!isIgnoreFile) {
                    Log.e(TAG, "delete file -->file.lastModified()=" + f.lastModified() + "TimeInMillis=" + TimeInMillis + ",file.getName()=" + f.getName());
                    f.delete();
                }
            }
            return;
        } else if (f.isDirectory()) {
            ff = f.listFiles();
            if (ff != null) {
                length = ff.length;
                int i = 0;
                while (length != 0) {
                    deleteFilesByTime(TimeInMillis, ff[i], haveIgnore, ignoreFilsNames);
                    i++;
                    length--;
                }
            }
        }
    }

    /**
     * 获得文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        int tmp = filePath.lastIndexOf(File.separatorChar);
        String fileName = filePath.substring(tmp + 1);
        return fileName;
    }

    /**
     * 获取cache文件保存网络缓存数据
     * @return
     */
    public static File getcacheDirectory() {
        File file = new File(DIR_CACHE, "NetCache");
        if(!file.exists()) {
            boolean b = file.mkdirs();
            Log.e("file", "文件不存在  创建文件    "+b);
        }else{
            Log.e("file", "文件存在");
        }
        return file;
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     *
     * @param filePath
     * @param bytes
     * @return
     */
    public static boolean writeBytesToFile(String filePath, byte[] bytes) {
        return writeBytesToFile(filePath, bytes, false);
    }

    /**
     * 写字节数组到文件，文件父目录如果不存在，会自动创建
     *
     * @param filePath
     * @param bytes
     * @param isAppend
     * @return
     */
    public static boolean writeBytesToFile(String filePath, byte[] bytes, boolean isAppend) {
        if (TextUtils.isEmpty(filePath) || bytes == null) {
            return false;
        }
        File file = new File(filePath);

        boolean isWriteOk = false;
        byte[] buffer = null;
        int count = 0;
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            if (!file.exists()) {
                createNewFileAndParentDir(file);
            }
            if (file.exists()) {
                bos = new BufferedOutputStream(new FileOutputStream(file, isAppend), DEFAULT_BUFFER_SIZE);
                bais = new ByteArrayInputStream(bytes);
                bis = new BufferedInputStream(bais, DEFAULT_BUFFER_SIZE);
                buffer = new byte[DEFAULT_BUFFER_SIZE];
                int len = 0;
                while ((len = bis.read(buffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                    bos.write(buffer, 0, len);
                    count += len;
                }
                bos.flush();
            }
            isWriteOk = bytes.length == count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                    bos = null;
                }
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                if (bais != null) {
                    bais.close();
                    bais = null;
                }
                buffer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "writeByteArrayToFile.file = " + file + ", bytes.length = " + (bytes == null ? 0 : bytes.length) + ", isAppend = " + isAppend + ", isWriteOk = " + isWriteOk);
        return isWriteOk;
    }

    /**
     * 按行读取文本文件
     *
     * @param is
     * @return
     * @throws Exception
     */
    public static String readTextFromFile(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * 创建文件父目录
     *
     * @param file
     * @return 是否创建成功
     */
    public static boolean createParentDir(File file) {
        boolean isMkdirs = true;
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                isMkdirs = dir.mkdirs();
                Log.v(TAG, "createParentDir.dir = " + dir + ", isMkdirs = " + isMkdirs);
            }
        }
        return isMkdirs;
    }

    /**
     * 创建文件及其父目录
     *
     * @param file
     * @return
     */
    public static boolean createNewFileAndParentDir(File file) {
        boolean isCreateNewFileOk = true;
        isCreateNewFileOk = createParentDir(file);
        //创建父目录失败，直接返回false，不再创建子文件
        if (isCreateNewFileOk) {
            if (!file.exists()) {
                try {
                    isCreateNewFileOk = file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    isCreateNewFileOk = false;
                }
            }
        }
        Log.d(TAG, "createFileAndParentDir.file = " + file + ", isCreateNewFileOk = " + isCreateNewFileOk);
        return isCreateNewFileOk;
    }

    /**
     * 拷贝文件 将文件A拷贝到文件B fileChannel方式
     *
     * @param fileNameA 原文件
     * @param fileNameB 目标文件
     * @return 是否拷贝成功
     */
    public static boolean copyTo(String fileNameA, String fileNameB) {
        File s = new File(fileNameA);
        File t = new File(fileNameB);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        boolean result = false;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fi != null)
                    fi.close();
                if (in != null)
                    in.close();
                if (fo != null)
                    fo.close();
                if (out != null)
                    out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String saveBitmap(Bitmap mBitmap,String fileName) {
        String savePath;
        File filePic;
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            savePath = DIR_PUBLLIC_ROOT+"/uploadpic/";
//        } else {
//            savePath = context.getApplicationContext().getFilesDir()
//                    .getAbsolutePath()
//                    + IN_PATH;
//        }
        savePath = DIR_PUBLLIC_ROOT+"/uploadpic/";
        try {
            filePic = new File(savePath +fileName);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

}
