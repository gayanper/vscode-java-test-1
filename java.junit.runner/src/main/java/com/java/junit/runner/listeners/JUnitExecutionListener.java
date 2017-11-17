package com.java.junit.runner.listeners;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

/**
 * Overridden JUnit run listener {@link RunListener}. The listener responds to the events during a
 * test run.
 */
public class JUnitExecutionListener extends RunListener {
    private CustomizedJUnitTestListener delegate;

    private String currentSuite;

    public JUnitExecutionListener(CustomizedJUnitTestListener delegate) {
        this.delegate = delegate;
        currentSuite = "";
    }

    @Override
    public void testRunStarted(Description description) throws Exception {
        delegate.testRunStarted();
    }

    @Override
    public void testRunFinished(Result result) throws Exception {
        if (!currentSuite.isEmpty()) {
            delegate.testSuiteFinished(currentSuite);
        }

        delegate.testRunFinished(result);
    }

    @Override
    public void testStarted(Description description) throws Exception {
        updateCurrentSuite(description);
        delegate.testStarted(description);
    }

    @Override
    public void testFinished(Description description) throws Exception {
        delegate.testFinished(description);
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        delegate.testFailure(failure);
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        delegate.testFailure(failure);
    }

    @Override
    public void testIgnored(Description description) throws Exception {
        updateCurrentSuite(description);
        delegate.testIgnored(description);
    }

    private void updateCurrentSuite(Description description) {
        if (currentSuite.isEmpty()) {
            currentSuite = description.getClassName();
            delegate.testSuiteStarted(description);
        } else if (!currentSuite.equals(description.getClassName())) {
            delegate.testSuiteFinished(currentSuite);
            currentSuite = description.getClassName();
            delegate.testSuiteStarted(description);
        }
    }
}
