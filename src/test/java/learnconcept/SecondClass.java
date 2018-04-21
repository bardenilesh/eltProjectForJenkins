package learnconcept;

import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class SecondClass {
	Logger log = Logger.getLogger(SecondClass.class);
	
  @Test(dataProvider = "dp")
  public void f(Integer n, String s) {
	  System.out.println("This is Test Method in Second Class");
	  log.warn("This is Test Method in Second Class");
  }
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("This is beforeMethod Method in Second Class");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("This is afterMethod Method in Second Class");
  }


  @DataProvider
  public Object[][] dp() {
    return new Object[][] {
      new Object[] { 1, "a" },
      new Object[] { 2, "b" },
    };
  }
  @BeforeClass
  public void beforeClass() {
	  System.out.println("This is beforeClass Method in Second Class");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("This is afterClass Method in Second Class");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("This is beforeTest Method in Second Class");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("This is afterTest Method in Second Class");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("This is beforeSuite Method in Second Class");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("This is afterSuite Method in Second Class");
  }

}
