package com.muhaitain.commonlibraty.utils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Muhaitian on 2017/12/19.
 */

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static String toJSONString(Object object) {
        try {
            String json = JSON.toJSONString(object);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Object> parseArray(String content, Type[] types) {
        try {
            return JSON.parseArray(content, types);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String content, Class<T> clazz) {
        try {
            T result = JSON.parseObject(content, clazz);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseObject(String content, Type clazz) {
        try {
            T result = JSON.parseObject(content, clazz);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
