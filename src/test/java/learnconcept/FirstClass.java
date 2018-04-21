package learnconcept;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class FirstClass {
  @Test(dataProvider = "dp")
  public void f(Integer n, String s) {
	  System.out.println("This is Test Method in First Class");
  }
  @BeforeMethod
  public void beforeMethod() {
	  System.out.println("This is beforeMethod Method in First Class");
  }

  @AfterMethod
  public void afterMethod() {
	  System.out.println("This is afterMethod Method in First Class");
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
	  System.out.println("This is beforeClass Method in First Class");
  }

  @AfterClass
  public void afterClass() {
	  System.out.println("This is afterClass Method in First Class");
  }

  @BeforeTest
  public void beforeTest() {
	  System.out.println("This is beforeTest Method in First Class");
  }

  @AfterTest
  public void afterTest() {
	  System.out.println("This is afterTest Method in First Class");
  }

  @BeforeSuite
  public void beforeSuite() {
	  System.out.println("This is beforeSuite Method in First Class");
  }

  @AfterSuite
  public void afterSuite() {
	  System.out.println("This is afterSuite Method in First Class");
  }

}
