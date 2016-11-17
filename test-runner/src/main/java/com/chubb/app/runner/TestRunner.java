package com.chubb.app.runner;

import com.chubb.app.Config;
import com.chubb.exception.ChubbException;
import com.chubb.exception.SevereException;
import com.chubb.jorney.JourneyParser;
import com.chubb.reporting.ReportGenerator;
import com.chubb.util.FileUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vsafronovici on 10/24/2016.
 */
@Service
public class TestRunner {

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private TestRunnerContext context;

    @Autowired
    private JourneyParser parser;

    public TestRunnerContext getContext() {
        return context;
    }

    public void runAllTests() throws IOException, TemplateException {

        final long startTime = System.currentTimeMillis();

        File[] journeyFiles = Config.getJourneyFilesDir().listFiles(File::isFile);
        if (journeyFiles.length > 0) {

            ExecutorService executorService = Executors.newFixedThreadPool(journeyFiles.length);

            for (File journeyFile : journeyFiles) {
                executorService.execute(new Thread(journeyFile.getName()) {
                    @Override
                    public void run() {
                        try {
                            Thread.currentThread().setName(this.getName());
                            runTest(journeyFile);
                        } catch (ChubbException e) {
                            //TODO log exception
                            System.out.println(String.format("Exception while running test for journey file: '%s'", journeyFile.getName()));
                            e.printStackTrace();
                        } catch (Exception e) {
                            //TODO log exception
                            System.out.println(String.format("Exception while running test for journey file: '%s'", journeyFile.getName()));
                            e.printStackTrace();
                        } finally {
                        }
                    }
                });
            }

            executorService.shutdown();
            // Wait until all threads are finished
            while (!executorService.isTerminated()) {
            }

            final long endTime = System.currentTimeMillis();
            System.out.println(String.format("======================= %d journey files executed in %d milliseconds", journeyFiles.length, endTime - startTime));

            File logFile = FileUtil.getFileFromDir(new File(Config.getWorkDir()), "/logs/mylog.log");
            try {
                reportGenerator.generate(logFile, endTime - startTime);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new SevereException(String.format("There are no journey files (.csv) inside %s") + Config.getJourneyFilesDir().getAbsolutePath());
        }
    }

    public void runTest(File journeyFile) throws ChubbException {
        parser.parse(journeyFile);
    }


}


