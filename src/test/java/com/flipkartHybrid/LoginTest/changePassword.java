package com.flipkartHybrid.LoginTest;

import java.util.Hashtable;

import org.testng.SkipException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.flipkartHybrid.Keywords;
import com.flipkartHybrid.Base.BaseTest;
import com.flipkartHybrid.Utility.Constants;
import com.flipkartHybrid.Utility.DataUtil;

import com.flipkartHybrid.Utility.Xls_Reader;

import com.relevantcodes.extentreports.LogStatus;

public class changePassword extends BaseTest {

	@BeforeTest
	public void Init() {
		xls = new Xls_Reader(Constants.SUITEA_Path);
		testcaseName = "changePassword";
	}

	@Test(dataProvider = "getData")
	public void changePasswo(Hashtable<String, String> data) throws InterruptedException {
		// System.out.println("Values from data table "+data.get("Username")+"
		// "+data.get("Runmode")+" "+data.get("Email"));
		test = rep.startTest(testcaseName);
		test.log(LogStatus.INFO, "Starting " + testcaseName + " Test");

		if (DataUtil.isTestCaseRunnable(xls, testcaseName) || data.get("Runmode").equals("N")) {
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}

		key = new Keywords(test);
		key.executeKeywords(testcaseName, xls, data);
		// test.log(LogStatus.INFO, "Executing "+testcaseName+" Test");
		// test.log(LogStatus.PASS, "test excuted succesfully");
		// key.getfailure().reportFailure("XXXXX");

	}

}
