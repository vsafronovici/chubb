package com.chubb.rest.adapter;

import com.chubb.AbstractGenericTest;
import com.chubb.app.Config;
import com.chubb.exception.ActionException;
import com.chubb.exception.ChubbExceptionReason;
import com.chubb.exception.ValidationException;
import com.chubb.jorney.Verb;
import com.chubb.rest.adapter.connection.Connection;
import com.chubb.rest.adapter.connection.ConnectionFactory;
import com.chubb.rest.adapter.request.Request;
import com.chubb.rest.adapter.response.Response;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ichistruga on 10/11/2016.
 */
public class RestResponseValidatorTest extends AbstractGenericTest {


    @Test
    public void validateAll() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testRestResponseValidator.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }
        testRunner.runTest(journeyFile);
    }

    @Test
    public void validateByTemplateFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testRestResponseValidatorFailsByTemplate.csv");
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

        assertEquals(Verb.ValidateRestResponseByTemplate, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByTemplateFailsWrongNodeStructure() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testRestResponseValidatorFailsByTemplate2.csv");
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

        assertEquals(Verb.ValidateRestResponseByTemplate, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByTemplateSoftlyFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testRestResponseValidatorFailsByTemplateSoftly.csv");
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

        assertEquals(Verb.ValidateRestResponseByTemplateSoftly, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByChildNodesTemplateFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testRestResponseValidatorFailsByChildNodes.csv");
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

        assertEquals(Verb.ValidateRestResponseByChildNodesTemplate, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByChildNodesTemplateSoftlyFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testRestResponseValidatorFailsByChildNodesSoftly.csv");
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

        assertEquals(Verb.ValidateRestResponseByChildNodesTemplateSoftly, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByPathFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testRestResponseValidatorFailsByPath.csv");
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

        assertEquals(Verb.ValidateRestResponseByPath, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }


}
