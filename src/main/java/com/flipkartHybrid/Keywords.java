package com.flipkartHybrid;

import java.util.Hashtable;

import org.testng.Assert;

import com.flipkartHybrid.Utility.Constants;
import com.flipkartHybrid.Utility.Xls_Reader;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords {
	ExtentTest test;
	public ApplicationKeywords app;

	public Keywords(ExtentTest test) {
		this.test = test;
	}

	public void executeKeywords(String testcaseName, Xls_Reader xls, Hashtable<String, String> data)
			throws InterruptedException {

		String SheetName = Constants.Keyword_Sheet;
		app = new ApplicationKeywords(test);
		int row = xls.getRowCount(SheetName);

		for (int rNum = 2; rNum <= row; rNum++) {
			String Tcid = xls.getCellData(Constants.Keyword_Sheet, Constants.TCID_Col, rNum);
			if (Tcid.equals(testcaseName)) {
				String keyword = xls.getCellData(Constants.Keyword_Sheet, Constants.Keyword_Col, rNum);
				String Object = xls.getCellData(Constants.Keyword_Sheet, Constants.Object_Col, rNum);
				String Key = xls.getCellData(Constants.Keyword_Sheet, Constants.Data_Col, rNum);
				String Data = data.get(Key);
				System.out.println(Tcid + " " + keyword + " " + Object + " " + Data);
				test.log(LogStatus.INFO, Tcid + " " + keyword + " " + Object + " " + Data);
				String result = "";
				if (keyword.equals("openBrowser"))
					result = app.openBrowser(Data);
				else if (keyword.equals("Navigate"))
					result = app.Navigate(Object);
				else if (keyword.equals("click"))
					result = app.click(Object);
				else if (keyword.equals("input"))
					result = app.input(Object, Data);
				else if (keyword.equals("quit"))
					result = app.quit();
				else if (keyword.equals("verifyElementPresent"))
					result = app.verifyElementPresent(Object);
				else if (keyword.equals("verifyText"))
					result = app.verifyText(Object, Data);
				else if (keyword.equals("filpkartLogin"))
					result = app.filpkartLogin(data);
				else if (keyword.equals("verifyFilpkartLogin"))
					result = app.verifyFilpkartLogin(data.get("ExpectedResult"));
				else if (keyword.equals("defaultLogin"))
					result = app.defaultLogin();
				else if (keyword.equals("filterMobilleandValidate"))
					result = app.filterMobilleandValidate(data);

				if (!result.equals(Constants.Pass)) {
					app.reportFailure(result);
					Assert.fail(result);
				}
			}

		}
	}

	public GenericKeywords getfailure() {
		return app;
	}
}
