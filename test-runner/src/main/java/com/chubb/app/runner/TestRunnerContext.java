package com.chubb.app.runner;

import com.chubb.storage.Storage;
import com.chubb.storage.StorageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vsafronovici on 10/24/2016.
 */
@Service
public class TestRunnerContext {


    @Autowired
    private Storage storage;


    private final ThreadLocal<ThreadLocalContainer> threadLocalContainer = new ThreadLocal<ThreadLocalContainer>() {

        @Override protected ThreadLocalContainer initialValue() {
            return new ThreadLocalContainer();
        }

    };

    private ThreadLocalContainer getContainer() {
        return threadLocalContainer.get();
    }



    public Storage getStorage() {
        return storage;
    }


}
