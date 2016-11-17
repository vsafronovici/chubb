package com.chubb.storage;

import java.util.Map;

/**
 * Created by vsafronovici on 10/24/2016.
 */
public interface Storage {

    Map<String, String> getCsvData();
    Map<String, Object> getContextData();

}
