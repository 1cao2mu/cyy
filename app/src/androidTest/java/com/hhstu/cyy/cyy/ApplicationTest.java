package com.hhstu.cyy.cyy;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public void testName() throws Exception {

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public ApplicationTest(Class<Application> applicationClass) {
        super(applicationClass);
    }

    @Override
    public int countTestCases() {
        return super.countTestCases();
    }

    public ApplicationTest() {
        super(Application.class);
    }
}