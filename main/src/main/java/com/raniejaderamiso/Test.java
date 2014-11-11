package com.raniejaderamiso;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

/**
 * @author Ranie Jade Ramiso
 */
public class Test {
    private final TestService testService;

    @Inject
    public Test(TestService testService) {
        this.testService = testService;
    }

    public static void main(String args[]) {
        final ObjectGraph objectGraph = ObjectGraph.create(new TestModule());

        final Test test = objectGraph.get(Test.class);

        System.out.println(BuilderConfig.APP_ID);
    }

    @Module(
        injects = {
            Test.class
        }
    )
    static class TestModule {
        @Provides
        @Singleton
        TestDao providesTestDao() {
            return new TestDao();
        }
    }
}
