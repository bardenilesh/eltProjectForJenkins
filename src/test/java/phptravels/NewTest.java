package phptravels;

import org.testng.annotations.Test;

import framework.util.ExcelUtility;
import framework.util.Global;
import framework.util.SeleniumUtilities;
import phptravels.pages.homePage;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class NewTest extends SeleniumUtilities{
	ExcelUtility oExcel = new ExcelUtility();
  WebDriver driver;
	@Test
  public void f() {
  }
  @BeforeClass
  public void beforeClass() {
	  driver=getRunningBrowser();
  }

  @AfterClass
	public void afterClass() {
		try {
			Global.dataFile = Global.workDir + "\\testData\\data.xls";
//			clickOnElement(homePage.link_MyAccount(driver));
//			clickOnElement(homePage.link_Login(driver));
//			pleaseWaitTillLoadingIsDone();
//			enterText(homePage.input_UserName(driver), oExcel.get("username"));
//			enterText(homePage.input_Password(driver), oExcel.get("password"));
//			clickOnElement(homePage.btn_Login(driver));
//			pleaseWaitTillLoadingIsDone();
			mouseHoverAndClickOnElement(homePage.link_MyAccount(driver));
			mouseHoverAndClickOnElement(homePage.link_Login(driver));
			pleaseWaitTillLoadingIsDone();
			Assert.assertFalse(isElementPresent(By.name("username")),"Successfully opened login page");
			wait(4000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
