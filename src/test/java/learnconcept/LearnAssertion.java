package learnconcept;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import framework.util.SeleniumUtilities;
import phptravels.pages.homePage;

import org.testng.annotations.BeforeClass;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LearnAssertion extends SeleniumUtilities{
	WebDriver driver;
	@Test
	public void mouseHover(){
		driver= getRunningBrowser();
		Actions action = new Actions(driver);
		action.dragAndDrop(homePage.input_Location(driver), homePage.btn_Login(driver)).perform();
	}
	/*@Test 
	public void Screenshot() throws Exception{
		driver= getRunningBrowser();
		pleaseWaitTillLoadingIsDone();
		wait(2000);
		if(1>2){
			System.out.println("True");
		}else{
		takeScreenShot();}
		clickOnElement(findElement(homePage.link_MyAccoun1));
		clickOnElement(findElement(homePage.link_Login1));
		if(1<2){
			System.out.println("True");
		}else{
		takeScreenShot();}
		if(1>2){
			System.out.println("True");
		}else{
		takeScreenShot();}
		driver.quit();
	}*/
	
	/*@Test
	public void softAssert()
	{
		SoftAssert sa = new SoftAssert();
		sa.assertEquals("2", "1", "It is correct");
		sa.assertTrue(false, "It is false");
		sa.assertFalse(true,"It is true");
		sa.assertAll();
	}*/
 /* @Test
  public void f() {
	  driver= getRunningBrowser();
		driver.navigate().to("http://google.com");
		timeOut(30);
		System.out.println(driver.getTitle());
		Assert.assertEquals("Google", driver.getTitle(),"Title is not matching");
	  System.out.println("Started");
	  Assert.assertEquals(1, 1);
	  System.out.println("After AssertEqual");
	  Assert.assertNotEquals(1, 2);
	  System.out.println("After AsssertNotEqual");
	  Assert.assertTrue(2>1, "it is correct");
	  System.out.println("After AssertTrue");
	  Assert.assertFalse(2<1, "it is incorrect");
	  System.out.println("After AssertFalse");
	  Assert.fail("Check if it fails");
	  System.out.println("After AssertFail");
  }
 /* @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }*/

}
