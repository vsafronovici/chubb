package com.chubb.storage;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vsafronovici on 10/21/2016.
 */
@Deprecated
public class StorageImpl implements Storage {


    private final Map<String, String> csvData = new HashMap<>();
    private final Map<String, Object> contextData = new HashMap<>();


    public Map<String, String> getCsvData() {
        return csvData;
    }

    public Map<String, Object> getContextData() {
        return contextData;
    }
}
