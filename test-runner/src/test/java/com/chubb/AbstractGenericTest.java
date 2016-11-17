package com.chubb;

import com.chubb.app.Config;
import com.chubb.app.runner.TestRunner;
import com.chubb.rest.adapter.client.RestJsonClient;
import com.chubb.rest.adapter.client.SoapClient;
import com.chubb.rest.adapter.request.RequestFactoryREST;
import com.chubb.rest.adapter.request.RequestFactorySOAP;
import com.chubb.rest.adapter.response.GenericHttpResponseValidator;
import com.chubb.rest.adapter.response.RestResponseValidator;
import com.chubb.util.FileUtil;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * Created by vsafronovici on 10/17/2016.
 */
@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = Application.class, initializers = ConfigFileApplicationContextInitializer.class)
@SpringBootTest(classes = com.chubb.app.Application.class)
@ActiveProfiles("test")
public abstract class AbstractGenericTest {

    @Autowired
    protected RequestFactoryREST requestFactoryREST;

    @Autowired
    protected RequestFactorySOAP requestFactorySOAP;

    @Autowired
    protected RestJsonClient restJsonClient;

    @Autowired
    protected SoapClient soapClient;

    @Autowired
    protected GenericHttpResponseValidator genericHttpResponseValidator;

    @Autowired
    protected RestResponseValidator restResponseValidator;

    @Autowired
    protected TestRunner testRunner;


    @BeforeClass
    public static void init() {
        Config.init();
    }


    public static String readResourceToString(String resourcePath) throws IOException {
        File resource = new File(AbstractGenericTest.class.getResource(resourcePath).getFile());
        return FileUtil.readFileToString(resource);
    }


}
