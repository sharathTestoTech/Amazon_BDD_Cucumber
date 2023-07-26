package com.amazon.automation.testrunner;


import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = {"src/test/resources/parallel"},
glue = {"com/amazon/automation/parallel", "com/amazon/automation/AppHooks"},
monochrome = true,
publish = true,
plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"rerun:target/failedrerun.txt",
		"timeline:test-output-thread/","pretty:target/json-report.json"
})


public class MyTestRunner_Class extends AbstractTestNGCucumberTests{
	@Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
	
	}
