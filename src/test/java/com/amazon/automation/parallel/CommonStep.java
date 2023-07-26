package com.amazon.automation.parallel;


import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CommonStep {
	   
    /*Helps to generate the logs in test report.
     * */
     
	 static Scenario scenario;
	public static String scenarioName;
	
	@Before
	public void setUp(Scenario scenario){
		CommonStep.scenario=scenario;
		 scenarioName=scenario.getName();
	}
    
	 public static String getScenarioName() {
			return scenarioName;
		}

}
