package com.callfire.api11.client.integration;

import com.callfire.api11.client.CfApi11Client;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class AbstractIntegrationTest {

    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.com.callfire", "DEBUG");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache", "DEBUG");
    }

    @Rule
    public ExpectedException ex = ExpectedException.none();
    protected CfApi11Client client;

    public AbstractIntegrationTest() {
        client = new CfApi11Client("", "");
    }

}
