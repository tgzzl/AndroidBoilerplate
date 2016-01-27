package com.optilink.android.boilerplate.flux.action;

import android.support.v4.util.ArrayMap;

public class ActionImpl implements Action<ArrayMap<String, Object>> {

    private String type;
    private ArrayMap<String, Object> dataMap;

    public ActionImpl(String type, Object... params) {
        this.type = type;
        this.dataMap = new ArrayMap<>();
        put(params);
    }

    private void put(Object... params) {
        try {
            int i = 0;
            while (i < params.length) {
                String key = (String) params[i++];
                Object value = params[i++];
                dataMap.put(key, value);
            }
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Do you forgot the key?");
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
