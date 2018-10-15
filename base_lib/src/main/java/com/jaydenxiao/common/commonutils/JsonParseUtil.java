package com.jaydenxiao.common.commonutils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2017/12/2.
 *
 * @desc json解析工具   对json解析的二次封装
 */

public class JsonParseUtil {
    private static Gson mGson;
    private static JsonParseUtil jsonParse;

    public static JsonParseUtil getIntance() {

        if (jsonParse == null) {
            synchronized (JsonParseUtil.class) {
                if (jsonParse == null) {

                    jsonParse = new JsonParseUtil();
                }
            }
        }

        return jsonParse;
    }

    private static final TypeAdapter<String> TYPE_STRING_ADAPTER = new TypeAdapter<String>() {
        @Override
        public void write(JsonWriter out, String value) throws IOException {

            if (value == null) {

                out.value("");
                return;
            }
            out.value(value);
        }

        @Override
        public String read(JsonReader in) throws IOException {

            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return "";
            }
            return in.nextString();
        }
    };

    private static final TypeAdapter<Long> TYPE_LONG_ADAPTER = new TypeAdapter<Long>() {
        @Override
        public void write(JsonWriter out, Long value) throws IOException {

            if (value == null) {

                out.value(0L);
                return;
            }
            out.value(value);
        }

        @Override
        public Long read(JsonReader in) throws IOException {

            if (in.peek() == JsonToken.NULL) {

                in.nextNull();
                return 0L;
            }
            return in.nextLong();
        }
    };

    private static final TypeAdapter<Integer> TYPE_INTEGER_ADAPTER = new TypeAdapter<Integer>() {
        @Override
        public void write(JsonWriter out, Integer value) throws IOException {

            if (value == null) {
                out.value(0);
                return;
            }
            out.value(value);
        }

        @Override
        public Integer read(JsonReader in) throws IOException {

            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return 0;
            }
            return in.nextInt();
        }
    };

    private static final TypeAdapter<Boolean> TYPE_BOLEAN_ADAPTER = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {

            if (value == null) {

                out.value(false);
                return;
            }

            out.value(value);
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {

                in.nextNull();
                return false;
            }
            return in.nextBoolean();
        }
    };

    public JsonParseUtil() {
        mGson = new GsonBuilder()
                .registerTypeAdapter(String.class, TYPE_STRING_ADAPTER)
                .registerTypeAdapter(Long.class, TYPE_LONG_ADAPTER)
                .registerTypeAdapter(Boolean.class, TYPE_BOLEAN_ADAPTER)
                .registerTypeAdapter(Integer.class, TYPE_INTEGER_ADAPTER)
                .create();
    }

    /**
     * @param src :将要被转化的对象
     * @return :转化后的JSON串
     * @MethodName : toJson
     * @Description : 将对象转为JSON串，此方法能够满足大部分需求
     */
    public String toJson(Object src) {
        if (null == src) {
            return mGson.toJson(JsonNull.INSTANCE);
        }
        try {
            return mGson.toJson(src);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     * @param classOfT
     * @return
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，但此方法不可用来转带泛型的集合
     */
    public <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return mGson.fromJson(json, (Type) classOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param json
     * @param typeOfT
     * @return
     * @MethodName : fromJson
     * @Description : 用来将JSON串转为对象，此方法可用来转带泛型的集合，如：Type为 new
     * TypeToken<List<T>>(){}.getType()
     * ，其它类也可以用此方法调用，就是将List<T>替换为你想要转成的类
     */
    public <T> List<T> fromJson(String json, Type typeOfT) {
        try {
            return mGson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取json中的某个值
     *
     * @param json
     * @param key
     * @return
     */
    public String getValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取json中的list值
     *
     * @param json
     * @return
     */
    public String getListValue(String json) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString("list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Double getDoubleValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getDouble(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIntValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getStringValue(String json, String key) {
        try {
            JSONObject object = new JSONObject(json);
            return object.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
