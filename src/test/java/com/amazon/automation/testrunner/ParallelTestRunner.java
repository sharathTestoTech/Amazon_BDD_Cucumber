package com.amazon.automation.testrunner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
@CucumberOptions(
		plugin = {"pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"timeline:test-output-thread/",
				"rerun:target/failedrerun.txt",
				"json:target/cucumber-reportreport.json"},
		monochrome = true,
		publish = true,
				glue = {"com/amazon/automation/parallel", "com/amazon/automation/AppHooks"},
		features = {"src/test/resources/parallel/Login.feature","src/test/resources/parallel"}
		)


public class ParallelTestRunner extends AbstractTestNGCucumberTests{
	@Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
	
	}
