package com.chubb.rest.adapter.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ichistruga on 10/24/2016.
 */
public class StringMatcherUtilsTest {

    @Test
    public void matchFileName_Test_1() {
        String fileName = "file.json[9]";
        String assertValue = "file.json";

        String result = StringMatcherUtils.matchFileName(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void matchFileName_Test_2() {
        String fileName = "file.json";
        String assertValue = "file.json";

        String result = StringMatcherUtils.matchFileName(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void matchFileName_Test_3() {
        String fileName = "file.[9]json";
        String assertValue = "file.json";

        String result = StringMatcherUtils.matchFileName(fileName);

        Assert.assertNotEquals(result, assertValue);

    }

    @Test
    public void matchFileName_Test_4() {
        String fileName = "file.jsonfile.data[9]";
        String assertValue = "file.jsonfile";

        String result = StringMatcherUtils.matchFileName(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void matchIndex_Test_1() {
        String fileName = "file.jsonfile.data[9]";
        String assertValue = "[9]";

        String result = StringMatcherUtils.matchIndex(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void matchIndex_Test_2() {
        String fileName = "[9]";
        String assertValue = "[9]";

        String result = StringMatcherUtils.matchIndex(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void matchIndex_Test_3() {
        String fileName = "[344449]";
        String assertValue = "[344449]";

        String result = StringMatcherUtils.matchIndex(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void matchIndex_Test_4() {
        String fileName = "[r9]";
        // String assertValue = "[9]";

        String result = StringMatcherUtils.matchIndex(fileName);

        Assert.assertTrue(result == null);

    }

    @Test
    public void matchIndex_Test_5() {
        String fileName = "fff[9]fff";
        String assertValue = "[9]";

        String result = StringMatcherUtils.matchIndex(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void replaceIndexBoundaries_Test_1() {
        String fileName = "fff[9]fff";
        String assertValue = "fff9fff";

        String result = StringMatcherUtils.replaceIndexBoundaries(fileName);

        Assert.assertEquals(result, assertValue);

    }

    @Test
    public void replaceIndexBoundaries_Test_2() {
        String fileName = "fff[69]fff";
        String assertValue = "fff69fff";

        String result = StringMatcherUtils.replaceIndexBoundaries(fileName);

        Assert.assertEquals(result, assertValue);

    }



}
