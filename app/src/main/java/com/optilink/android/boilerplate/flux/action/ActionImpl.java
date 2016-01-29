package com.optilink.android.boilerplate.flux.action;

import android.support.v4.util.ArrayMap;

public class ActionImpl implements Action<ArrayMap<String, Object>> {

    private String type;
    private ArrayMap<String, Object> dataMap;

    public ActionImpl(String type, Object... params) {
        this.type = type;
        put(params);
    }

    private void put(Object... params) {
        int length = params.length;
        // key-value must correspond
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Do you forgot the key?");
        }

        int i = 0;
        this.dataMap = new ArrayMap<>(length / 2);
        while (i < length) {
            String key = (String) params[i++];
            Object value = params[i++];
            dataMap.put(key, value);
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public ArrayMap<String, Object> getData() {
        return dataMap;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "type='" + type + '\'' +
                ", dataMap=" + dataMap +
                '}';
    }
}
