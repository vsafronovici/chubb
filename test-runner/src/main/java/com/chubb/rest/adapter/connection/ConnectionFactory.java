package com.chubb.rest.adapter.connection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ichistruga on 10/10/2016.
 */


public final class ConnectionFactory {

    private static final Map<String, Connection> pool = Collections.synchronizedMap(new HashMap<>());

    private ConnectionFactory() {

        }

    public static Connection getConnection(String server){

        Connection connection =  pool.get(server);
        if (connection == null) {
            connection = new Connection(server);
            pool.put(server, connection);
        }
        return connection;
    }

}
