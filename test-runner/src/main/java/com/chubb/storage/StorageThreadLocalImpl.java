package com.chubb.storage;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vsafronovici on 10/21/2016.
 */
@Service
public class StorageThreadLocalImpl implements Storage {

    private final ThreadLocal<Map<String, String>> csvDataThreadLocal = new ThreadLocal<Map<String, String>>() {

        @Override protected Map<String, String> initialValue() {
            return new HashMap<>();
        }

    };

    private final ThreadLocal<Map<String, Object>> contextDataThreadLocal = new ThreadLocal<Map<String, Object>>() {

        @Override protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }

    };



    @Override
    public Map<String, String> getCsvData() {
        return csvDataThreadLocal.get();
    }

    @Override
    public Map<String, Object> getContextData() {
        return contextDataThreadLocal.get();
    }
}
