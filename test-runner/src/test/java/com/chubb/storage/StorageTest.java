package com.chubb.storage;

import com.chubb.AbstractGenericTest;
import com.chubb.app.runner.TestRunner;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by vsafronovici on 10/24/2016.
 */
@Ignore
public class StorageTest extends AbstractGenericTest {

    private static final Logger log = LoggerFactory.getLogger(StorageTest.class);

    private static final Map<String, Map<String, String>> csvThreadExpectedStorage = new LinkedHashMap<>(20);
    private static final Map<String, Map<String, Object>> ctxThreadExpectedStorage = new LinkedHashMap(20);

    @BeforeClass
    public static void init2() {
        for (int i = 0; i < 200; i++) {
            String threadName = "thx" + i;
            Map<String, String> csvData = new HashMap<>(5);
            Map<String, Object> ctxData = new HashMap<>(5);
            for (int j = 0; j < 5; j++) {
                csvData.put("csvKey-" + threadName + "_" + j, "csvValue-" + threadName + "_" + j);
                ctxData.put("ctxKey-" + threadName + "_" + j, "ctxValue-" + threadName + "_" + j);
            }

            csvThreadExpectedStorage.put(threadName, csvData);
            ctxThreadExpectedStorage.put(threadName, ctxData);

        }

    }


    @Autowired
    private Storage storage;

    @Autowired
    private TestRunner testRunner;

    //@Test
    public void threadLocal() throws FileNotFoundException {



        final PrintStream stream = new PrintStream(new FileOutputStream("E:\\projects\\Chubb\\out-stream\\thread-local.txt", false));

        ExecutorService executorService = Executors.newFixedThreadPool(200);

        csvThreadExpectedStorage.forEach((s, stringObjectMap) -> {

            executorService.execute(new Thread(s) {

                @Override
                public void run() {

                    Map<String, String> expectedCsv = csvThreadExpectedStorage.get(getName());
                    Map<String, Object> expectedCtx = ctxThreadExpectedStorage.get(getName());


                    expectedCsv.forEach((s, o) -> {
                        storage.getCsvData().put(s, o);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    expectedCtx.forEach((s, o) -> {
                        storage.getContextData().put(s, o);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                    Map<String, String> resultCsv = storage.getCsvData();
                    Map<String, Object> resultCtx = storage.getContextData();


                    if (!expectedCsv.equals(resultCsv)) {
                        stream.println(getName() + "; " + expectedCsv.size() + ":" + resultCsv.size() + "; " + expectedCsv + "----" + resultCsv);
                    }
                    if (!expectedCtx.equals(resultCtx)) {
                        stream.println(getName() + "; " + expectedCtx.size() + ":" + resultCtx.size() + "; " + expectedCtx + "----" + resultCtx);
                    }


                    //assertTrue(storage.getCsvData().equals(csvThreadExpectedStorage.get(getName())));
                    //assertTrue(storage.getContextData().equals(ctxThreadExpectedStorage.get(getName())));



                }
            });


        });

        executorService.shutdown();

        // Wait until all threads are finish
        while (!executorService.isTerminated()) {

        }

        /*try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdown();
        }*/






    }

    @Test
    public void springThreadScope() throws FileNotFoundException {


        //PrintStream stream = new PrintStream(new FileOutputStream("E:\\projects\\Chubb\\out-stream\\spring.txt", false));
        PrintStream stream = System.out;

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        csvThreadExpectedStorage.forEach((s, stringObjectMap) -> {

            executorService.execute(new Thread(s) {

                @Override
                public void run() {

                    Map<String, String> expectedCsv = csvThreadExpectedStorage.get(getName());
                    Map<String, Object> expectedCtx = ctxThreadExpectedStorage.get(getName());


                    expectedCsv.forEach((s, o) -> {
                        testRunner.getContext().getStorage().getCsvData().put(s, o);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    expectedCtx.forEach((s, o) -> {
                        testRunner.getContext().getStorage().getContextData().put(s, o);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                    Map<String, String> resultCsv = testRunner.getContext().getStorage().getCsvData();
                    Map<String, Object> resultCtx = testRunner.getContext().getStorage().getContextData();

                    if (!expectedCsv.equals(resultCsv)) {
                        stream.println(getName() + "; " + expectedCsv.size() + ":" + resultCsv.size() + "; " + expectedCsv + "----" + resultCsv);
                    }
                    if (!expectedCtx.equals(resultCtx)) {
                        stream.println(getName() + "; " + expectedCtx.size() + ":" + resultCtx.size() + "; " + expectedCtx + "----" + resultCtx);
                    }

                    //assertTrue(storage.getCsvData().equals(csvThreadExpectedStorage.get(getName())));
                    //assertTrue(storage.getContextData().equals(ctxThreadExpectedStorage.get(getName())));



                }
            });


        });

        executorService.shutdown();

        // Wait until all threads are finish
        int wait = 0;
        while (!executorService.isTerminated()) {
            //stream.println("wait:" + ++wait);
        }

        /*try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executorService.shutdown();
        }*/

    }



}
