package com.flipkartHybrid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.flipkartHybrid.Utility.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class GenericKeywords {
	WebDriver driver;
	public Properties prop;
	ExtentTest test;
	
	public GenericKeywords(ExtentTest test) {
		this.test=test;
		prop = new Properties();
		try {
			FileInputStream fi = new FileInputStream(Constants.Property_Path);
		//	System.out.println(Constants.Property_Path);
			prop.load(fi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String openBrowser(String Browser) {
		//System.out.println(Browser);
		test.log(LogStatus.INFO, "Opening Browser");
		if (Browser.equals("Mozilla"))
		{
			System.setProperty("webdriver.gecko.driver", Constants.GeckoDriver_Path);
			driver = new FirefoxDriver();
		} else if (Browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", Constants.ChromeDriver_Path);
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return Constants.Pass;
		
	}

	public String Navigate(String URL) {
		System.out.println(prop.getProperty(URL));
		test.log(LogStatus.INFO, "Navigating URL "+prop.getProperty(URL));
		driver.get(prop.getProperty(URL));
		return Constants.Pass;
	}

	public String input(String locatorKey, String dataInput) {

		test.log(LogStatus.INFO, "Typing in "+prop.getProperty(locatorKey)+" "+dataInput);
		WebElement e = getElementType(locatorKey);
		e.sendKeys(dataInput);
		return Constants.Pass;
	}

	public String click(String locatorKey) {

		test.log(LogStatus.INFO, "Clicking on "+prop.getProperty(locatorKey));
		WebElement e = getElementType(locatorKey);
		e.click();
		return Constants.Pass;
	}

	public String quit() {
		test.log(LogStatus.INFO, "Quit Browser");
	//	driver.quit();
		return Constants.Pass;
	}

	/**********************Validity Function
	 * @throws InterruptedException ***********************************/
	public String verifyText(String locatorKey, String ExpectedText) throws InterruptedException
	{
		Thread.sleep(5000);
		WebElement e = getElementType(locatorKey);
		String ActualText = e.getText();
		if(ActualText.equals(ExpectedText))
			return Constants.Pass;
		else
		return Constants.Fail+"ActualText("+ActualText+") And ExpectedValue("+ExpectedText+") are not equqal";
	}
	
	public String verifyElementPresent(String locatorKey)
	{
		boolean result =isElementPresent(locatorKey);
		if(result)
			return Constants.Pass;
		else
			return Constants.Fail+" Could not find Element "+locatorKey;
	}
	public String verifyElementNotPresent(String locatorKey)
	{
		boolean result =isElementPresent(locatorKey);
		if(!result)
			return Constants.Pass;
		else
			return Constants.Fail+" Could find Element "+locatorKey;
	}
	
	/**********************Utility Function***********************************/
	public WebElement getElementType(String locatorKey) {

		WebElement ele = null;
		try {
			if (locatorKey.endsWith("_Id")) {
				ele = driver.findElement(By.id(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_xPath")) {
				ele = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_Name")) {
				ele = driver.findElement(By.name(prop.getProperty(locatorKey)));
			} else if (locatorKey.endsWith("_className")) {
				ele = driver.findElement(By.className(prop.getProperty(locatorKey)));
			}
		} catch (Exception e) {
			reportFailure("Failure in element Extration " + locatorKey);
			test.log(LogStatus.FAIL, "Failure in element Extration " + locatorKey);
			Assert.fail("Failure in element Extration " + locatorKey);
		}
		return ele;
	}
	public boolean isElementPresent(String locatorKey)
	{
		List<WebElement> ele = null;
		if (locatorKey.endsWith("_Id")) {
			ele = driver.findElements(By.id(prop.getProperty(locatorKey)));
		} else if (locatorKey.endsWith("_xPath")) {
			ele = driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		} else if (locatorKey.endsWith("_Name")) {
			ele = driver.findElements(By.name(prop.getProperty(locatorKey)));
		} else if (locatorKey.endsWith("_className")) {
			ele = driver.findElements(By.className(prop.getProperty(locatorKey)));
		}
		
		if(ele.size()==0)
			return false;
		else
			return true;
	}
	
	/**********************Reporting Function***********************************/
	public void reportFailure(String FailureMessage)
	{
		takeScreenShot();
		test.log(LogStatus.FAIL, FailureMessage);
		
	}
	public void takeScreenShot()
	{
		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_")+".png";
		String Path = Constants.ScreenShot_Path+fileName;
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(srcFile, new File(Path));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.log(LogStatus.INFO, test.addScreenCapture(Path));
	}
}
