
package com.telus.runners;

import com.test.cucumber.AbstractTestNGCucumberTests;

import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
		features = "src/test/resources/features"
		,glue = {"com.telus.wnp.stepsdef"}
		//,tags = "@Smoke1 or @Smoke2",
		,tags = "@Smoke2 and @Smoke5",
		plugin = {"pretty","com.test.cucumber.ExtentCucumberAdapter:",
				//"com.telus.cucumber.plugin.ReportPortalCucumberPlugin",
				"rerun:target/failedrerun.txt"},
	    
		monochrome = true,
		publish = true
		
		)
public class AppCucumberRunner extends AbstractTestNGCucumberTests {
	

	
}