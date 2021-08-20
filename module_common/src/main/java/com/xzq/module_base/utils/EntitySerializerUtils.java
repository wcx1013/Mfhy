package com.xzq.module_base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 序列化工具类
 * Created by Alex on 2015/8/24.
 */
public class EntitySerializerUtils {

    private static class SerializerHolder {
        private static final Gson INSTANCE = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer())
                .setDateFormat(DateFormat.LONG).create();
    }

    protected static Gson getSerializer() {
        return SerializerHolder.INSTANCE;
    }

    /**
     * 实体对象序列化
     *
     * @param obj 需要序列化的实体对象
     * @return 格式化的字符串
     */
    public static String serializerEntity(Object obj) {
        return getSerializer().toJson(obj);
    }

    /**
     * 范型对象序列化
     * @param obj 需要序列化的实体对象
     * @param type 范型类型
     * @return 格式化的字符串
     */
    public static String serializerType(Object obj, Type type) {
        return getSerializer().toJson(obj, type);
    }

    /**
     * 集合对象序列化
     *
     * @param list 需要序列化的集合对象
     * @return 格式化的字符串
     */
    public static String serializerList(List<?> list) {
        return serializerType(list, new TypeToken<List<?>>() {
        }.getType());
    }

    /**
     * Map对象序列化
     * @param map 需要序列化的Map对象
     * @return 格式化的字符串
     */
    public static String serializerMap(Map<?,?> map) {
        return serializerType(map, new TypeToken<Map<?, ?>>() {
        }.getType());
    }

    /**
     * 反序列实体对象
     * @param json 字符串
     * @param targetCls 实体类型
     * @param <T> 目标类型
     * @return 实体对象
     */
    public static <T> T deserializerEntity(String json, Class<T> targetCls) throws Exception {
        return getSerializer().fromJson(json, targetCls);
    }

    /**
     * 反序列范型对象
     * @param json 字符串
     * @param type 范型类型
     * @param <T> 目标类型
     * @return 实体对象
     */
    public static <T> T deserializerType(String json, Type type) throws Exception {
        return getSerializer().fromJson(json, type);
    }

    /**
     * 反序列范型对象
     * @param json 字符串
     * @param <T> 目标类型
     * @return 实体对象
     */
    public static <T> T deserializerType(String json, TypeToken<T> token) throws Exception {
        return deserializerType(json, token.getType());
    }

    /**
     * 日期序列化
     * 日期序列化方式主要由服务端决定
     * @author Alex
     *
     */
    public static class DateDeserializer implements JsonDeserializer<Date> {

        /**
         * 日期反序列化
         */
        public Date deserialize(JsonElement jsonElement, Type type,
                                JsonDeserializationContext context) throws JsonParseException {
            return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
        }

    }
}

