package com.banyou.app.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;

public class Convert {
    public static Gson create() {
        return Convert.ViewHolder.gson;
    }

    public static String toJson(Object o) {
        return create().toJson(o);
    }

    public static class ViewHolder {
        private static Gson gson = new Gson();
    }

    public static <T> T fromJson(JsonReader reader, Type type) {
        return create().fromJson(reader, type);
    }

    public static <T> T fromJson(String result, Class<T> clazz) {
        return create().fromJson(result, clazz);
    }
}
