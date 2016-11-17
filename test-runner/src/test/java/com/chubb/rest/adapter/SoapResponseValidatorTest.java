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

import static org.junit.Assert.assertEquals;

/**
 * Created by ichistruga on 10/11/2016.
 */
public class SoapResponseValidatorTest extends AbstractGenericTest {


    @Test
    public void validateAll() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testSoapResponseValidator.csv");
        if (!journeyFile.exists()) {
            throw new FileNotFoundException(journeyFile.getAbsolutePath());
        }
        testRunner.runTest(journeyFile);
    }

    @Test
    public void validateByTemplateFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testSoapResponseValidatorFailsByTemplate.csv");
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

        assertEquals(Verb.ValidateSoapResponseByTemplate, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }


    @Test
    public void validateByTemplateSoftlyFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testSoapResponseValidatorFailsByTemplateSoftly.csv");
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

        assertEquals(Verb.ValidateSoapResponseByTemplateSoftly, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByChildNodesTemplateFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testSoapResponseValidatorFailsByChildNodes.csv");
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

        assertEquals(Verb.ValidateSoapResponseByChildNodesTemplate, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByChildNodesTemplateSoftlyFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testSoapResponseValidatorFailsByChildNodesSoftly.csv");
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

        assertEquals(Verb.ValidateSoapResponseByChildNodesTemplateSoftly, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }

    @Test
    public void validateByPathFails() throws FileNotFoundException {
        File journeyFile = FileUtils.getFile(Config.getJourneyFilesDir(), "testSoapResponseValidatorFailsByPath.csv");
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

        assertEquals(Verb.ValidateSoapResponseByPath, expectedException.getVerb());
        assertEquals(ChubbExceptionReason.VALIDATION, expectedException.getReason());
    }


}
