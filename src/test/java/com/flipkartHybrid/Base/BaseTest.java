package com.flipkartHybrid.Base;

import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;

import com.flipkartHybrid.Keywords;
import com.flipkartHybrid.Utility.DataUtil;
import com.flipkartHybrid.Utility.ExtentManager;
import com.flipkartHybrid.Utility.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseTest {

	public ExtentReports rep = ExtentManager.getInstance();
	public ExtentTest test;
	public Keywords key;
	public Xls_Reader xls;
	public String testcaseName;

	@AfterTest
	public void afterTest()
	{
		if(test!=null)
		{
		rep.endTest(test);
		rep.flush();
		}
		if(key!=null)
		{
			key.app.quit();
		}
	}
	@DataProvider
	public Object[][] getData() {

		return DataUtil.getData(xls, testcaseName);
	}
	
}
