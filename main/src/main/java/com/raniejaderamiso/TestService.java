package com.raniejaderamiso;

import javax.inject.Inject;

/**
 * @author Ranie Jade Ramiso
 */
public class TestService {
    private final TestDao testDao;

    @Inject
    public TestService(TestDao testDao) {
        this.testDao = testDao;
    }
}
