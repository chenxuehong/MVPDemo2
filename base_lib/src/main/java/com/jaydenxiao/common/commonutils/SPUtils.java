package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jaydenxiao.common.ui.LibApplication;
import com.jaydenxiao.common.commonutils.security.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 对SharedPreference文件中的各种类型的数据进行存取操作
 */
public class SPUtils {

    private static final String DEFAULT_SP_NAME = "default_sp_name";
    private static SharedPreferences sp;

    private SPUtils() {
        sp = getSharedPreferences(LibApplication.Companion.getBaseApplication(), SPUtils.DEFAULT_SP_NAME);
    }

    private static SPUtils mSPUtil;

    public static SPUtils getIntance() {

        if (mSPUtil == null) {
            synchronized (SPUtils.class) {
                if (mSPUtil == null) {
                    mSPUtil = new SPUtils();
                }
            }
        }
        return mSPUtil;
    }

    /**
     * 获取一个sp
     *
     * @param context
     * @param name
     * @return
     */
    private SharedPreferences getSharedPreferences(Context context, String name) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public void setSharedIntData(Context context, String key, int value) {

        sp.edit().putInt(key, value).commit();
    }

    public int getSharedIntData(Context context, String key) {

        return sp.getInt(key, 0);
    }

    public void setSharedlongData(Context context, String key, long value) {

        sp.edit().putLong(key, value).commit();
    }

    public long getSharedlongData(Context context, String key) {

        return sp.getLong(key, 0l);
    }

    public void setSharedFloatData(Context context, String key,
                                   float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public Float getSharedFloatData(Context context, String key) {

        return sp.getFloat(key, 0f);
    }

    public void setSharedBooleanData(Context context, String key,
                                     boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public Boolean getSharedBooleanData(Context context, String key) {

        return sp.getBoolean(key, false);
    }

    public void setSharedStringData(Context context, String key, String value) {

        sp.edit().putString(key, value).commit();
    }

    public String getSharedStringData(Context context, String key) {

        return sp.getString(key, "");
    }

    public void clearData() {

        sp.edit().clear().commit();
    }

    /**
     * 保存对象到SP
     *
     * @param keyName
     * @param t
     * @param <T>
     */
    public <T> void saveObj2SP(String keyName, T t) {

        ByteArrayOutputStream bos;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            byte[] bytes = bos.toByteArray();
            String ObjStr = Base64.encode(bytes);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(keyName, ObjStr);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从SP中获取保存对象
     *
     * @param keyName
     * @param <T>
     * @return
     */
    public <T extends Object> T getObjFromSP(String keyName) {

        byte[] bytes = Base64.decode(sp.getString(keyName, ""));
        ByteArrayInputStream bis;
        ObjectInputStream ois = null;
        T obj = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }
}