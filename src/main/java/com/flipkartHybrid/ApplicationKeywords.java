package com.flipkartHybrid;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.flipkartHybrid.Utility.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ApplicationKeywords extends GenericKeywords {
	public ApplicationKeywords(ExtentTest test) {
		super(test);
		
	}

	public String Login(String UserName, String Password)
	{
		getElementType("LoginLink_xPath").click();
		getElementType("EMail_xPath").sendKeys(UserName);
		getElementType("Password_xPath").sendKeys(Password);
		getElementType("LoginButton_xPath").click();
		return Constants.Pass;
	}

	public String filpkartLogin(Hashtable<String, String> data) {
		test.log(LogStatus.INFO, "Logging with "+data.get("Email")+" and "+data.get("Password"));
		return Login(data.get("Email"),data.get("Password"));
		
	}
	
	public String defaultLogin()
	{
		test.log(LogStatus.INFO, "Default Logging with floret.classifiedads5@gmail.com and floret.classifiedads5");
		return Login("floret.classifiedads5@gmail.com","floret.classifiedads5");
	}

	public String verifyFilpkartLogin(String ExpectedResult) {
		test.log(LogStatus.INFO, "Validating Login Details");
		boolean result = isElementPresent("VerifyAcoount_xPath");
		System.out.println(result);
		String ActualResult="";
		if(result)
			ActualResult="Success";
		else
			ActualResult="Failure";
		if(!ExpectedResult.equals(ActualResult))
		{
			return "Failed - Got an Actual Result as "+ActualResult;
		}
		return Constants.Pass;
	}

	public String filterMobilleandValidate(Hashtable<String, String> data) throws InterruptedException {
		String BrandName = data.get("MobileBrand");
		System.out.println(BrandName);
	//	Thread.sleep(5000);
		getElementType(BrandName).click();
		//Validate Names
		Thread.sleep(5000);
		List<WebElement> Mobiles = driver.findElements(By.xpath("//div[@class='_3wU53n']"));
		System.out.println(Mobiles.size());
		for(int i=0;i<Mobiles.size();i++)
		{
			System.out.println(Mobiles.get(i).getText());
			if(!Mobiles.get(i).getText().contains("Samsung"))
			{
				return Constants.Fail+" Found the Mobile "+Mobiles.get(i).getText();
			}
		}
		//Validate Price
		String PriceRange = data.get("PriceFilter");
		System.out.println(PriceRange);
		Thread.sleep(5000);
		//div[contains(text(),"Rs. 15000 - Rs. 29999")]
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@title='"+PriceRange+"']")));	
		driver.findElement(By.xpath("//div[@title='"+PriceRange+"']")).click();
		String[] PriceRangeSplit= PriceRange.split(" ");
		String PriceLow = PriceRangeSplit[1];
		String PriceMax = PriceRangeSplit[4];
		int PriceLow1 = Integer.parseInt(PriceLow);
		int PriceMax1 = Integer.parseInt(PriceMax);
		System.out.println(PriceLow1+" "+PriceMax1);
		Thread.sleep(5000);
		List<WebElement>PriceLis = driver.findElements(By.xpath("//div[@class='_1uv9Cb']/div[1]"));
		System.out.println(PriceLis.size());
		for(int i=0;i<PriceLis.size();i++)
		{
			Mobiles = driver.findElements(By.xpath("//div[@class='_3wU53n']"));
			String PriceList = PriceLis.get(i).getText().substring(1).replace(",", "");
			System.out.println(PriceList+" "+Mobiles.get(i).getText());
			int priceToCompare = Integer.parseInt(PriceList);
			if((priceToCompare>PriceMax1)||(priceToCompare<PriceLow1))
			{
				return Constants.Fail+" Price of the Product is Greater Or Lesser than Filtered Price  "+priceToCompare+" "+Mobiles.get(i).getText();
			}
		}
		return Constants.Pass;
	}
	
}
