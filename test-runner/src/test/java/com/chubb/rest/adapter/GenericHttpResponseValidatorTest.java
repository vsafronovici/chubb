package com.chubb.rest.adapter;

import com.chubb.AbstractGenericTest;
import com.chubb.app.Config;
import com.chubb.exception.ActionException;
import com.chubb.exception.ChubbExceptionReason;
import com.chubb.jorney.Verb;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by ichistruga on 10/11/2016.
 */
public class GenericHttpResponseValidatorTest extends AbstractGenericTest {


    @Test
    public void validateAll() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testGenericHttpResponseValidator.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }
        testRunner.runTest(journeyFile);
    }

    @Test
    public void validateStatusCodeFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testGenericHttpResponseValidatorFailsStatusCode.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }

        ActionException expectedException = null;
        try {
            testRunner.runTest(journeyFile);
        } catch (ActionException e) {
            expectedException = e;
            System.out.println(e.getMessage());
        }

        assertEquals(Verb.ValidateResponseHttpStatusCode, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }


    @Test
    public void validateHeaderValuesFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testGenericHttpResponseValidatorFailsHeaderValues.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }

        ActionException expectedException = null;
        try {
            testRunner.runTest(journeyFile);
        } catch (ActionException e) {
            expectedException = e;
            System.out.println(e.getMessage());
        }

        assertEquals(Verb.ValidateResponseHttpHeaderValues, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateResponseHttpStatusAndHeaderByTemplateFailsHeader() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testGenericHttpResponseValidatorByTemplateFailsHeader.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }

        ActionException expectedException = null;
        try {
            testRunner.runTest(journeyFile);
        } catch (ActionException e) {
            expectedException = e;
            System.out.println(e.getMessage());
        }

        assertEquals(Verb.ValidateResponseHttpStatusAndHeaderByTemplate, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());

    }

    @Test
    public void validateResponseHttpStatusAndHeaderByTemplateFailsStatus() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testGenericHttpResponseValidatorByTemplateFailsStatus.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }

        ActionException expectedException = null;
        try {
            testRunner.runTest(journeyFile);
        } catch (ActionException e) {
            expectedException = e;
            System.out.println(e.getMessage());
        }

        assertEquals(Verb.ValidateResponseHttpStatusAndHeaderByTemplate, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());

    }

    @Test
    public void validateResponseHttpStatusAndHeaderByTemplateSoftlyFailsHeader() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testGenericHttpResponseValidatorByTemplateSoftlyFailsHeader.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }

        ActionException expectedException = null;
        try {
            testRunner.runTest(journeyFile);
        } catch (ActionException e) {
            expectedException = e;
            System.out.println(e.getMessage());
        }

        assertEquals(Verb.ValidateResponseHttpStatusAndHeaderByTemplateSoftly, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());

    }

    @Test
    public void validateResponseHttpStatusAndHeaderByTemplateSoftlyFailsStatus() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testGenericHttpResponseValidatorByTemplateSoftlyFailsStatus.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }

        ActionException expectedException = null;
        try {
            testRunner.runTest(journeyFile);
        } catch (ActionException e) {
            expectedException = e;
            System.out.println(e.getMessage());
        }

        assertEquals(Verb.ValidateResponseHttpStatusAndHeaderByTemplateSoftly, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());

    }

}
