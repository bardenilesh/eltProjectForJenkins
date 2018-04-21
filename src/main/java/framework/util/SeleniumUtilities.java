package framework.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import org.eclipse.core.internal.utils.Convert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
//import org.testng.log4testng.Logger;






//import com.uprr.app.cqf.beanfactory.ConfigBeanFactory;

public class SeleniumUtilities {
  int i = 1;
  
  
  
  static Logger log = Logger.getLogger(SeleniumUtilities.class.getName());

  static String myCurrentDir = System.getProperty("user.dir");
  
  public String multipleValueDelimiter = ",";

  private Actions action;

  public static String browser = null;

  public static boolean runOnJenkins = false;

//  public ConfigBeanFactory seleniumBean;

//  public SeleniumUtilities seleniumUtilities;

  public static String remoteIp;

  public static String remoteIP;

  public static WebDriver webDriver;

  public static String appUrl;

  public static String userName;

  public static String password;

  public static String homePageTitle;

  public static String loginPageTitle;
  
  public static String tibcoServer;
  
  public boolean acceptNextAlert = true;
  
  static SoftAssert as = new SoftAssert();

  public static WebDriver getRunningBrowser() {
    log.info("Initial Method");
    System.out.println("Working directory is : "+myCurrentDir);
    Global.workDir=myCurrentDir;
    String propsLocation = Global.workDir+"\\src\\test\\resources\\test.properties";
    Properties prop = new Properties();
    try {
      prop.load(new FileInputStream(propsLocation));
      browser = prop.getProperty("BROWSER").trim();
      runOnJenkins = Boolean.valueOf(prop.getProperty("RUN_ON_JENKINS"));
      remoteIP = prop.getProperty("REMOTEIP");
      appUrl = prop.getProperty("URL");
      Global.baseURL=appUrl;
      userName = prop.getProperty("USERNAME");
      password = prop.getProperty("PASSWORD");
      homePageTitle = prop.getProperty("homePageTitle");
      loginPageTitle = prop.getProperty("loginPageTitle");
      if(appUrl.contains("xdev")){      
        tibcoServer = prop.getProperty("tibcoDevServerNCredentials");
      }
      if(appUrl.contains("xtest")){      
        tibcoServer = prop.getProperty("tibcoTestServerNCredentials");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    try {
      if (runOnJenkins) {
        if (browser.trim().equalsIgnoreCase("chrome")) {
          log.info("http://" + remoteIp + ":5566/wd/hub");
          webDriver = new RemoteWebDriver(new URL("http://" + remoteIp + ":5566/wd/hub"), DesiredCapabilities.chrome());
        }
        if (browser.trim().equalsIgnoreCase("firefox") || browser.trim().equals("firefox")) {
          log.info("http://" + appUrl );
          webDriver = new RemoteWebDriver(new URL("http://" + appUrl ), DesiredCapabilities.firefox());
        }
        if (browser.trim().equalsIgnoreCase("IE")) {
          log.info("http://" + remoteIp + ":5566/wd/hub");
          webDriver = new RemoteWebDriver(new URL("http://" + remoteIp + ":5566/wd/hub"),
              DesiredCapabilities.internetExplorer());
        }

      } else {
        if (browser.equalsIgnoreCase("chrome")) {
          System.setProperty("webdriver.chrome.driver", Global.workDir+"\\drivers\\chromedriver.exe");
          webDriver = new ChromeDriver();
        }
        if (browser.equalsIgnoreCase("mozilla") || browser.trim().equalsIgnoreCase("firefox")) {
          System.setProperty("webdriver.gecko.driver", Global.workDir+"\\drivers\\geckodriver32.exe");
          webDriver = new FirefoxDriver();
        }
        if (browser.equalsIgnoreCase("IE")) {
          System.setProperty("webdriver.ie.driver", Global.workDir+"\\drivers\\IEDriverServer.exe");
          DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
          capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
          webDriver = new InternetExplorerDriver(capabilities);
        }
      }
      Global.drv=webDriver;
      Global.as=as;
      Global.wait= new WebDriverWait(webDriver, 30);
      webDriver.manage().window().maximize();
      webDriver.get(appUrl);
    } catch (Exception ex) {
      ex.printStackTrace();
      log.info("Some Error with the SeleniumSMUtil Method");
    }
    return webDriver;
  }

  public List<Object> initialize()
  {
    Actions action = Global.action;
    WebDriverWait wait = Global.wait;
    WebDriver driver = Global.drv;
    List<Object> lst = new ArrayList<Object>();
    lst.add(action);
    lst.add(wait);
    lst.add(driver);
    return lst;
  }
  public void closeRecentlyOpenedWindow() {
    String parentWindow = webDriver.getWindowHandle();
    Set<String> handles = webDriver.getWindowHandles();
    for (String windowHandle : handles) {
      if (!windowHandle.equals(parentWindow)) {
        webDriver.switchTo().window(windowHandle);
        // <!--Perform your operation here for new window-->
        webDriver.close(); // closing child window
        webDriver.switchTo().window(parentWindow); // cntrl to parent window
      }
    }
  }
  
  public void takeScreenShot() throws IOException {
    try {
      webDriver=Global.drv;
      File Snap1 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
      String destinationFile = Global.workDir + "/ErrorScreenshot" + "/"
          + "Execution_Step" + i + ".jpg";
      FileUtils.copyFile(Snap1, new File(destinationFile));
      i++;
    } catch (WebDriverException e) {
      log.error("");
    }
  }

  public By locatorValue(String locatorType, String value) {
    By by;
    switch (locatorType.toLowerCase()) {
      case "id":
        by = By.id(value);
        break;
      case "name":
        by = By.name(value);
        break;
      case "xpath":
        by = By.xpath(value);
        break;
      case "css":
        by = By.cssSelector(value);
        break;
      case "link":
        by = By.linkText(value);
        break;
      case "partiallink":
        by = By.partialLinkText(value);
        break;
      case "class":
      by= By.className(value);
      break;
      default:
        by = null;
        break;
    }
    return by;
  }

  public WebElement findElement(String locatorType, String value) throws NoSuchElementException, IOException {
    WebElement field = null;
    try {
//      webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      field = webDriver.findElement(locatorValue(locatorType, value));
//      takeScreenShot();
    } catch (NoSuchElementException ex) {
      log.warn("No element with locatorType: " + locatorType + " And Value " + value + " was NOT found.");
    }
    return field;
  }
  
  public WebElement findElement(By by) throws NoSuchElementException, IOException {
	    WebElement field = null;
	    try {
//	      webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	      field = webDriver.findElement(by);
//	      takeScreenShot();
	    } catch (NoSuchElementException ex) {
	      log.warn("Element does not found");
	    }
	    return field;
	  }

  public List<WebElement> findElements(String locatorType, String value) throws NoSuchElementException {
    List<WebElement> allData = null;
    try {
      webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      allData = webDriver.findElements(locatorValue(locatorType, value));
    } catch (NoSuchElementException ex) {
      log.warn("No element with locatorType: " + locatorType + " And Value " + value + " was NOT found.");
    }
    return allData;
  }

  public void enterText(String locatorType, String value, String text){

    try {
      WebElement element = findElement(locatorType, value);
      element.clear();
      wait(100);
      element.sendKeys(text);
      wait(100);
      element=null;
    } catch (NoSuchElementException e) {
      log.warn("No Element Found to enter text" + locatorType + ":" + value);
    } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
     log.warn("No Element Found to enter text" + locatorType + ":" + value);
  }
  }
  
  public void enterText(WebElement element, String text){

	    try {
	      element.clear();
	      wait(100);
	      element.sendKeys(text);
	      wait(100);
	      element=null;
	    } catch (NoSuchElementException e) {
	    	 log.warn("Element does not found");
	    	e.printStackTrace();
	    }
	  }
  
  public void enterText(String locatorType, String value, Keys text){

      try {
        WebElement element = findElement(locatorType, value);
        wait(100);
        element.sendKeys(text);
        wait(100);
        element=null;
      } catch (NoSuchElementException e) {
        log.warn("No Element Found to enter text" + locatorType + ":" + value);
      } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
       log.warn("No Element Found to enter text" + locatorType + ":" + value);
    }
    }
  public void clickOnElement(String locatorType, String value) throws Exception {
    try {

      WebElement element = findElement(locatorType, value);
      element.click();
      wait(1000);
    } catch (NoSuchElementException e) {
      log.warn("No Element Found to click with: " + locatorType + "= " + value);

    }
  }
  
  public void clickOnElement(WebElement element) throws Exception {
	    try {
	      element.click();
	      wait(1000);
	    } catch (Exception e) {
	      log.warn("No Element Found ");

	    }
	  }

  public String getTextFromWebElement(String locatorType, String value) throws Exception {
    WebElement element = null;
    String gettext = null;
    try {
      element = findElement(locatorType, value);

      gettext = element.getText();
      log.info("Element text is: " + gettext);

    } catch (NoSuchElementException e) {
      log.warn("No element with name " + element + "was found.");
    }
    return gettext;
  }

  public void fluentWaitElementToBeVisible(By locator) {
    log.info("Waiting for Element to be Visible");
    new WebDriverWait(webDriver, 30).until(ExpectedConditions.visibilityOfElementLocated(locator));
  }
  
  public void fluentWaitElementToBeInvisible(By locator) {
      log.info("Waiting for Element to be Invisible");
      new WebDriverWait(webDriver, 30).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
  public void fluentWaitElementToBeClickable(By locator) {
      log.info("Waiting for Element to be Clickable");
      new WebDriverWait(webDriver, 30).until(ExpectedConditions.elementToBeClickable(locator));
    }
  public void waitUntilElementBecomesClickble(String locatorType, String value, Integer seconds) {

    By locator;
    try {
      locator = locatorValue(locatorType, value);
      WebDriverWait wait = new WebDriverWait(webDriver, seconds);
      wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(webDriver.findElement(locator))));
    } catch (NoSuchElementException ex) {
      log.warn("No element with name " + locatorType + " : " + value + "was found.");
    }

  }

  public void WaitforElement(String locatorType, String value) {

    try {
      By locator;
      locator = locatorValue(locatorType, value);
      fluentWaitElementToBeVisible(locator);

    } catch (NoSuchElementException ex) {
      log.warn("No element with name " + locatorType + " : " + value + "was found.");
    }
  }
  
  public void WaitforElementToBeInvisible(String locatorType, String value) {

    try {
      By locator;
      locator = locatorValue(locatorType, value);
      fluentWaitElementToBeInvisible(locator);

    } catch (NoSuchElementException ex) {
      log.warn("No element with name " + locatorType + " : " + value + "was found.");
    }
  }

  public String getElementAttribute(String locatorType, String value, String name) {
    String attribute = null;
    try {
      log.info("Element with Attribute Name: " + name);

      attribute = findElement(locatorType, value).getAttribute(name);

    } catch (Exception ex) {
      log.warn("No element with name " + locatorType + " : " + value + "was found.");

    }
    return attribute;
  }
  
  public boolean isAttributePresent(String locatorType, String value, String attributeName) {
    String attribute = null;
    try {
      log.info("Element with Attribute Name: " + attributeName);

      attribute = findElement(locatorType, value).getAttribute(attributeName);

    } catch (Exception ex) {
      log.warn("No element with name " + locatorType + " : " + value + "was found.");

    }
    if(attribute!=null)
    {
      return true;
    }
    else{
      return false;
    }
  }
  
  public void setClipboardData(String string) {
     StringSelection stringSelection = new StringSelection(string);
     Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
  }
  public void paste_Function()
  {
    try {
      Robot robot = new Robot();
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_V);
      robot.keyRelease(KeyEvent.VK_V);
      robot.keyRelease(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_ENTER);
      robot.keyRelease(KeyEvent.VK_ENTER);
    } catch (AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  public boolean pageLaunchSuccess(final String pageTitle) {
    boolean flag = false;
    try {
      if (pageTitle.equals(webDriver.getTitle())) {
        log.info("Launched the expected page: Title as " + webDriver.getTitle());

        flag = true;
      } else {
        log.warn("Incorrect webpage launched!!! \nExpected Title: " + pageTitle + ". Actual Title: "
            + webDriver.getTitle());

      }
    } catch (Exception ex) {
      log.warn("Page did NOT launch successfully ");

    }
    return flag;

  }

  public void assertElementPresent(String locatorType, String value, String message) throws Exception {
    try {
      log.info("Assert Element Present with locatorType: " + locatorType + "and value: " + value);
      WebElement element = findElement(locatorType, value);
      Assert.assertTrue(element.isDisplayed());

    } catch (NoSuchElementException e) {
      log.warn("Element is NOT present" + locatorType + ":" + value);
      log.warn(e.getMessage());
      Assert.fail(message + ", Element is NOT present " + locatorType + ":" + value);

    }
  }

  public void assertElementPresent(String locatorType, String value) throws Exception {
    try {
      log.info("Assert Element Present with locatorType: " + locatorType + "and value: " + value);
      WebElement element = findElement(locatorType, value);
      Assert.assertTrue(element.isDisplayed());

    } catch (NoSuchElementException e) {
      log.info("Element is NOT present" + locatorType + ":" + value);
      log.info(e.getMessage());
      Assert.fail("Element is NOT present " + locatorType + ":" + value);

    }
  }

  public boolean assertElementIsNotPresent(String locatorType, String value) throws Exception {
    boolean isElementPresent = false;
    try {
      log.info("Assert Element with locatorType: " + locatorType + "and value: " + value + " is not present");
      findElement(locatorType, value);
      isElementPresent = true;
    } catch (NoSuchElementException e) {
      log.warn("Element is NOT present" + locatorType + ":" + value + ", So returning true as per the check");
      isElementPresent = true;
    }
    return isElementPresent;

  }

  public void verifyResultGridValues(String locatorType, String value, int columnNumber, String values)
      throws IOException {

    List<String> expectedValues = Arrays.asList(values.split(multipleValueDelimiter));

    try {
      List<String> actualValues = new ArrayList<String>();
      WebElement grid = findElement(locatorType, value);
      List<WebElement> rows = grid.findElements(By.tagName("tr"));

      for (int i = 0; i < rows.size(); i++) {
        String column = rows.get(i).findElements(By.tagName("td")).get(columnNumber - 1).getText();
        actualValues.add(column);
      }
      Assert.assertEquals(actualValues, expectedValues, "Values are not matching");

    } catch (NoSuchElementException e) {
      log.error("The Element with locator Type: " + locatorType + ":" + value + "with Text: " + "was NOT found.");

      Assert.fail("The Element with locator Type: " + locatorType + ":" + value + "with Text: " + "was NOT found.\n"
          + e.getLocalizedMessage());
    }
  }

  public void assertElementText(String locatorType, String value, String text) throws Exception {

    try {
      String actualTextOnElement = findElement(locatorType, value).getText();

//      log.info("Actual Text on Element: " + actualTextOnElement);
      Assert.assertEquals(actualTextOnElement, text, "Assertion Failed - " + "Expected: " + text + " Actual: "
          + actualTextOnElement);
    } catch (NoSuchElementException ex) {
      log.error("The Element with locator Type: " + locatorType + ":" + value + "with Text: " + "was NOT found.");
      Assert.fail("The Element with locator Type: " + locatorType + ":" + value + "with Text: " + "was NOT found.");
    }
  }

  public boolean assertElementContainsText(final String locatorType, String value, String text) throws Exception {

    boolean flag = false;
    try {
      String actualTextOnElement = findElement(locatorType, value).getText();

//      log.info("Actual Text on Element: " + actualTextOnElement);
      if (actualTextOnElement.contains(text)) {
//        log.info("Actual Matches expected.");
        flag = true;
      } else {
//        log.warn("Actual does NOT Matches expected.");
//        log.warn("Actual Text on Element" + actualTextOnElement);
//        log.warn("Expected Text on Element" + text);

      }
    } catch (NoSuchElementException ex) {
      log.error("The Element with locator Type: " + locatorType + ": " + " with Value: " + value + " was NOT found."+ex.getMessage());
//      log.warn("Expected Text on Element" + text);
//      log.warn(ex.getMessage());

    }

    return flag;
  }

  public void switchToFrame(String frame) {
    try {
//      log.info("Popup/Frame Name or ID is" + frame);
      webDriver.switchTo().frame(frame);

    } catch (Exception e) {
      log.error(frame+" Popup/Frame is NOT found");
    }
  }

  public void refreshPage() {
    webDriver.navigate().refresh();
  }

  public String getPageTitle() {
    String title = null;
    try {
      title = webDriver.getTitle();
//      log.info("Page Title is: " + title);
    } catch (Exception e) {
      log.error("Could NOT find page with Title" + title);
    }
    return title;
  }

  public enum AlertType {
    ACCEPT, OK, DISMISS, CANCEL;

    public static AlertType find(String action) {
      for (AlertType at : AlertType.values()) {
        if (at.name().equalsIgnoreCase(action)) {
          return at;
        }
      }
      return ACCEPT;
    }

  };
  public void timeOut(long val)
  {
    webDriver.manage().timeouts().implicitlyWait(val, TimeUnit.SECONDS);
  }
  
  public void clickAlert(String action) {
    wait(1000);
    Alert alert = webDriver.switchTo().alert();
    AlertType alertType = AlertType.find(action);
    switch (alertType) {
      case ACCEPT:
      case OK:
        alert.accept();
        break;
      case DISMISS:
      case CANCEL:
        alert.dismiss();
        break;
      default:
        wait(1000);
    }
  }

  public void switchToActiveElement() {
    try {
      webDriver.switchTo().activeElement();

    } catch (Exception e) {
      log.error("Could NOT switch to active element");
    }
  }

  public String switchToAlert(String command) {
    try {
      Alert alert = webDriver.switchTo().alert();

      if (command.equalsIgnoreCase("OK")) {
        String text = alert.getText();
        alert.accept();
        return text;
      } else if (command.equalsIgnoreCase("CANCEL")) {
        String text = alert.getText();
        alert.dismiss();
        return text;
      } else {
        return null;
      }
    } catch (Exception e) {
      log.error("Could NOT switch to alert");
    }
    return null;
  }

  public boolean assertAlertText(String text) {
    boolean flag = false;
    try {

      String alertText = webDriver.switchTo().alert().getText();
      log.info("Actual Text on Alert: " + alertText);
      if (alertText.contains(text)) {
//        log.info("Actual Matches expected.");
        flag = true;
      } else {
        log.error("Actual does NOT Matches expected.");
      }
    } catch (NoSuchElementException ex) {
      log.error("The Alert with Text: " + text + "was NOT found.");

    }

    return flag;

  }

  public void wait(int timeInMiliSeconds) {
    try {
      Thread.sleep(timeInMiliSeconds);
    } catch (InterruptedException e) {
      log.error("Could NOT execute wait method");
    }
  }

  public boolean mouseHoverAndClickOnElement(String locatorType, String value) throws Exception {
    boolean mouseHover = false;

    WebElement myElement = findElement(locatorType, value);

    action = new Actions(webDriver);

    action.moveToElement(myElement).build().perform();
    action.click();

    if (null != myElement) {
      mouseHover = true;
//      log.error("Mouse hovered successfully on " + myElement.getText());

    }

    return mouseHover;

  }
  
  public boolean mouseHoverAndClickOnElement(WebElement myElement) throws Exception {
	    boolean mouseHover = false;
	    action = new Actions(webDriver);
	    action.moveToElement(myElement).click().perform();
	    if (null != myElement) {
	      mouseHover = true;
//	      log.error("Mouse hovered successfully on " + myElement.getText());

	    }

	    return mouseHover;

	  }

  public void clearAndEnterText(String locatorType, String value, String text) throws Exception {
    WebElement element = null;
    try {
      element = findElement(locatorType, value);
      element.click();
      element.clear();
      wait(1000);
      element.sendKeys(text);

//      log.info("Value set: " + text);

    } catch (NoSuchElementException ex) {
      log.error("No element with LOCATORTYPE: " + locatorType + " And VALUE: " + value + " was found.");

    }
  }

  public void selectTextFromDropDown(String locatorType, String value, String text) throws Exception {
    try {
      WebElement element = findElement(locatorType, value);
      Select sel = new Select(element);
      sel.selectByVisibleText(text);
      wait(1000);
    } catch (NoSuchElementException ex) {
      log.error("No element with LOCATORTYPE: " + locatorType + " And VALUE: " + value + " was found.");
      ex.printStackTrace();
    }
  }
  
  public String getSelectedTextFromDropDown(String locatorType, String value) throws Exception {
    try {
      WebElement element = findElement(locatorType, value);
      Select sel = new Select(element);
      String text = sel.getFirstSelectedOption().getText();
      wait(1000);
      return text;
    } catch (NoSuchElementException ex) {
      log.error("No element with LOCATORTYPE: " + locatorType + " And VALUE: " + value + " was found.");
      ex.printStackTrace();
      return "";
    }  
  }

  public void assertElementIsEnabled(String locatorType, String value, boolean bool) {
    try {
//      log.info("ELEMENT WITH: " + locatorType + ": " + value + "ENABLE CONDITION" + bool);
      WebElement element = findElement(locatorType, value);

      if (bool == false) {

        Assert.assertFalse(element.isEnabled());
      }
      if (bool == true) {
        if (element.isEnabled()) {
          Assert.assertTrue(element.isEnabled());
        } else {
          new WebDriverWait(webDriver, 15).until(ExpectedConditions.elementToBeClickable(element));
          Assert.assertTrue(element.isEnabled());

        }
      }
    } catch (Exception e) {

      log.error("ELEMENT WITH: " + locatorType + ": " + value + "ENABLE CONDITION " + bool + " IS FALSE");

    }
  }

  public void assertElementIsSelected(String locatorType, String value, boolean bool) {
    try {
//      log.info("ELEMENT WITH: " + locatorType + ":" + value + "SELECTED CONDITION" + bool);
      WebElement element = findElement(locatorType, value);
      if (bool == true) {

        if (element.isSelected()) {
          Assert.assertTrue(element.isSelected());
        } else {
          new WebDriverWait(webDriver, 15).until(ExpectedConditions.elementToBeSelected(element));
          Assert.assertTrue(element.isSelected());
        }
      } else if (bool == false) {
        Assert.assertFalse(element.isSelected());
      }
    } catch (Exception e) {
      log.error("ELEMENT WITH: " + locatorType + ":" + value + "SELECTED CONDITION" + bool + "IS FALSE");

    }
  }

  public String currentSystemDate() {
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();
    String currentDate = dateFormat.format(date);
    return currentDate;
  }

  public String currentSystemDatePlus(int days) {
    String currentDate = null;
    try {
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); // dd/MM/yyyy
      Date date = new Date();
      currentDate = dateFormat.format(date);
      Calendar c = Calendar.getInstance();
      c.setTime(dateFormat.parse(currentDate));
      c.add(Calendar.DATE, days); // number of days to add
      currentDate = dateFormat.format(c.getTime());
      return currentDate;
    } catch (Exception e) {
      log.error("Unable to add date"+e.getMessage());
      e.printStackTrace();
    }
    return currentDate;
  }
  
  public String currentSystemDateMinus(int days) {
    String currentDate = null;
    try {
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); // dd/MM/yyyy
      Date date = new Date();
      currentDate = dateFormat.format(date);
      Calendar c = Calendar.getInstance();
      c.setTime(dateFormat.parse(currentDate));
      c.add(Calendar.DATE, -days); // number of days to add
      currentDate = dateFormat.format(c.getTime());
      return currentDate;
    } catch (Exception e) {
      log.error("Unable to subtract date"+e.getMessage());
      e.printStackTrace();
    }
    return currentDate;
  }
  
  public String getYesterdaysDate() throws Exception {
    String yestDate = null;
    try{
    yestDate=currentSystemDateMinus(1);
      return yestDate;
    } catch (Exception e) {
      log.error("Unable to get yesterday's date"+e.getMessage());
      e.printStackTrace();
      return yestDate;
    }
  }
  
  public String currentSystemTime() {
    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    Date date = new Date();
    String currentDate = dateFormat.format(date);
    return currentDate;
  }
  
  public String currentSystemTimePlus(int mins) {
    String currentDate = null;
    try {
      DateFormat dateFormat = new SimpleDateFormat("HH:mm"); // dd/MM/yyyy
      Date date = new Date();
      currentDate = dateFormat.format(date);
      Calendar c = Calendar.getInstance();
      c.setTime(dateFormat.parse(currentDate));
      c.add(Calendar.MINUTE, mins); // number of days to add
      currentDate = dateFormat.format(c.getTime());
      return currentDate;
    } catch (Exception e) {
      log.error("Unable to add time "+e.getMessage());
      e.printStackTrace();
    }
    return currentDate;
  }
  
  public String currentSystemTimeMinus(int mins) {
    String currentDate = null;
    try {
      DateFormat dateFormat = new SimpleDateFormat("HH:mm"); // dd/MM/yyyy
      Date date = new Date();
      currentDate = dateFormat.format(date);
      Calendar c = Calendar.getInstance();
      c.setTime(dateFormat.parse(currentDate));
      c.add(Calendar.MINUTE, -mins); // number of days to add
      currentDate = dateFormat.format(c.getTime());
      return currentDate;
    } catch (Exception e) {
      log.error("Unable to subtract time "+e.getMessage());
      e.printStackTrace();
    }
    return currentDate;
  }
  
  public boolean checkMatchAttr(By by, String compareWith, String fieldName) {
    boolean isCorrect = false;
    if (webDriver.findElement(by).getAttribute("value").trim()
        .equalsIgnoreCase(compareWith.trim())) {
      log.info(fieldName + " is correctly shown");
      System.out.println(fieldName + " is correctly shown");
      isCorrect = true;
    } else {
      log.error(fieldName + " is not shown correctly");
      System.out.println(fieldName + " is not shown correctly");
      isCorrect = false;
    }
    System.out.println(fieldName + " "
        + webDriver.findElement(by).getAttribute("value"));
    return isCorrect;
  }

  public boolean checkPartialMatchAttr(By by, String compareWith,
      String fieldName) {
    boolean isCorrect = false;
    if (compareWith
        .trim()
        .toLowerCase()
        .contains(
            webDriver.findElement(by).getAttribute("value").trim()
                .toLowerCase())) {
      log.info(fieldName + " is correctly shown");
      System.out.println(fieldName + " is correctly shown");
      isCorrect = true;
    } else {
      log.error(fieldName + " is not shown correctly");
      System.out.println(fieldName + " is not shown correctly");
      isCorrect = false;
    }
    System.out.println(fieldName + " "
        + webDriver.findElement(by).getAttribute("value"));
    return isCorrect;
  }

  public boolean checkMatchText(By by, String compareWith, String fieldName) {
    boolean isCorrect = false;
    if (webDriver.findElement(by).getText().trim()
        .equalsIgnoreCase(compareWith.trim())) {
      log.info(fieldName + " is correctly shown");
      System.out.println(fieldName + " is correctly shown");
      isCorrect = true;
    } else {
      log.error(fieldName + " is not shown correctly");
      System.out.println(fieldName + " is not shown correctly");
      isCorrect = false;
    }
    System.out.println(fieldName + " "
        + webDriver.findElement(by).getText());
    return isCorrect;
  }

  public boolean checkPartialMatchText(By by, String compareWith,
      String fieldName) {
    boolean isCorrect = false;
    if (compareWith
        .trim()
        .toLowerCase()
        .contains(
            webDriver.findElement(by).getText().trim()
                .toLowerCase())) {
      log.info(fieldName + " is correctly shown");
      System.out.println(fieldName + " is correctly shown");
      isCorrect = true;
    } else {
      log.error(fieldName + " is not shown correctly");
      System.out.println(fieldName + " is not shown correctly");
      isCorrect = false;
    }
    System.out.println(fieldName + " "
        + webDriver.findElement(by).getText());
    return isCorrect;
  }

  public boolean isEqual(String string1, String string2, String fieldName) {
    boolean isCorrect = false;
    if (string1.trim().toLowerCase().equals(string2.trim().toLowerCase())) {
      System.out.println(fieldName + " is correctly shown");
      log.info(fieldName + " is correctly shown");
      isCorrect = true;
    } else {
      log.error(fieldName + " is not shown correctly");
      System.out.println(fieldName + " is not shown correctly");
      isCorrect = false;
    }
    return isCorrect;
  }

  public boolean isElementPresent(By by) {
    try {
      webDriver.findElement(by);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isAlertPresent() {
    try {
      webDriver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  public String readField(WebElement el) {
    List<WebElement> options = el.findElements(By.tagName("option"));
    for (WebElement option : options) {
      if (option.isSelected()) {
        return option.getText();
      }
    }
    return null;
  }
  
  public List<String> getDropDownOptions(WebElement el)
  {
    List<WebElement> opt = new ArrayList<WebElement>();
    opt=new Select(el).getOptions();
    List <String> optionText = new ArrayList<String>();
    for (int i = 0; i < opt.size(); i++) {
      optionText.add(opt.get(i).getText());
    }
    return optionText;
  }
  
  public void pleaseWait() {
    try {
      Thread.sleep(100);
      for (int x = 0; x < 100; x++) {
        if (isElementPresent(By.id("ricolaPleaseWait"))) {
          if (!webDriver.findElement(By.id("ricolaPleaseWait")).isDisplayed()) {
            break;
          } else {
            Thread.sleep(100);
          }
        }
      }
    } catch (Exception e) {
      log.error("Unable to wait");
      e.printStackTrace();
    }
  }
  
  public void pleaseWaitTillLoadingIsDone() {
    try {
      Thread.sleep(200);
      WebDriverWait wait = new WebDriverWait(webDriver, 200);
      timeOut(2);
      if(isElementPresent(By.xpath("//*[@id='preloader']/img"))){
      wait.until(ExpectedConditions.stalenessOf(webDriver.findElement(By.xpath("//*[@id='preloader']/img"))));
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      log.error("Element is still available in DOM "+e.getMessage());
      e.printStackTrace();
      }
//    WebDriverWait wait = new WebDriverWait(webDriver, 100); wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("rc-loading-animation")));
//    System.out.print("Text box text4 is now invisible");
  }
  
  public void pleaseWaitTillLoadingIsDone1() {
    try {
      Thread.sleep(100);
      for (int x = 0; x < 200; x++) {
        if (isElementPresent(By.className("rc-loading-animation"))) {
          if (!webDriver.findElement(By.className("rc-loading-animation")).isDisplayed()) {
            break;
          } else {
            Thread.sleep(100);
          }
        }
      }
    } catch (Exception e) {
      log.error("Unable to wait till loading is done");
        e.printStackTrace();
      }
//    WebDriverWait wait = new WebDriverWait(webDriver, 100); wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("rc-loading-animation")));
//    System.out.print("Text box text4 is now invisible");
  }
  
  public boolean compareDataIgnoreCase(String expected, String actual){
    if(actual.toLowerCase().contains(expected.toLowerCase()))
    {
      log.info("comparison is successful between "+expected+ " & "+actual);
      return true;
    }
    else
    {
      log.error("comparison is failed between "+expected+ " & "+actual);
      return false;
    }
  }
  
  public String compareData(boolean bool){
    String flagText="";
    if(bool)
    {
      flagText="Yes";
    }else{
      flagText="No";
    }
    return flagText;
  }
  public boolean compareData(String expected, String actual){
    if(expected.equals(actual))
    {
      log.info("comparison is successful between "+expected+ " & "+actual);
      return true;
    }
    else
    {
      log.error("comparison is failed between "+expected+ " & "+actual);
      return false;
    }
  }
  
  public void elementVerification(By by, String element){
    if(isElementPresent(by))
    {
      System.out.println(element+" is available on page");
    }
    else
    {
      log.error(element+" is not available on page");
      System.out.println(element+" is not available on page");
    }
  }
  public void clickOnCalender(WebElement cal)
  {
    try {
      action= new Actions(webDriver);
      int x_location = cal.getLocation().getX();
      int width = cal.getSize().getWidth();
      System.out.println("Width " + width);
      System.out.println("clicked x at " + x_location+(width-2));
      action.moveToElement(cal, width - 2, 0).click().perform();
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      log.error("Unable to click on calender");
      e.printStackTrace();
    }
  }
  
  public void setYesterdaysDateInWicket() throws Exception {
//    clickOnCalender(findElement("css", "div.ricCalendarInputWrapper.ricInlineWrapper"));
//    Thread.sleep(1000);
    WebElement caldate = findElement("class","ricCalendarDays");
    if(getTodaysDay()==1)
    {
    clickOnElement("css", "th.ricCalendarMonthPrev");
    Thread.sleep(1000);
    caldate.findElement(By.xpath("//td[text()='28']")).click();
    }
    else {
      int day=getTodaysDay()-1;
      caldate.findElement(By.xpath("//td[text()='"+day+"']")).click();
    }
    
    Thread.sleep(2000);
  }
  
  public String getTodaysDate() throws Exception {
    String day, month, year;
      day=Integer.toString(getTodaysDay());
      month=Integer.toString(getTodaysMonth());
      year=Integer.toString(getTodaysYear());
     String finalDate = month+"/"+day+"/"+year;
    return dateFormatter(finalDate);
  }
  
  
  public String dateFormatter(String date)
  {
    String dutyDate=date;
    String[] splDutyDate = dutyDate.split("/");
    for(int i=0;i<(splDutyDate.length-1);i++)
    {
//      System.out.println(splDutyDate[i].length());
      if(splDutyDate[i].length()==1)
      {
//        System.out.println("value ="+splDutyDate[i]);
        splDutyDate[i]="0"+splDutyDate[i];
//        System.out.println("value ="+splDutyDate[i]);
      }
//      System.out.println(splDutyDate[i]);
    }
    if (splDutyDate[2].length() == 2) {
//      System.out.println("Year = "+splDutyDate[2]);
      splDutyDate[2]="20"+splDutyDate[2];
//      System.out.println("Year = "+splDutyDate[2]);
    }
    System.out.println(splDutyDate[1]+"/"+splDutyDate[0]+"/"+splDutyDate[2]);
    return splDutyDate[0]+"/"+splDutyDate[1]+"/"+splDutyDate[2];
  }
  
  public static int getTodaysDay()
  {   Calendar cal = Calendar.getInstance();
      int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
      return dayOfMonth;
  }
  
  public static int getTodaysMonth()
  {   Calendar cal = Calendar.getInstance();
      int dayOfMonth = (cal.get(Calendar.MONTH)+1);
      return dayOfMonth;
  }
  
  public static int getTodaysYear()
  {   Calendar cal = Calendar.getInstance();
      int dayOfMonth = cal.get(Calendar.YEAR);
      return dayOfMonth;
  }
  
  public void selectDateFromCalendar(WebElement cal, String date)
  {
    try {
      action= new Actions(webDriver);
      WebDriverWait wait = new WebDriverWait(webDriver, 10);
      int x_location = cal.getLocation().getX();
      int width = cal.getSize().getWidth();
      System.out.println("Width " + width);
      System.out.println("clicked x at " + x_location+(width-2));
      action.moveToElement(cal, width - 2, 0).click().perform();
      Thread.sleep(1000);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ricCalendarWrapper")));
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      log.error("Unable to select date from calender");
      e.printStackTrace();
    }
  }
  
  public void compareValuesFromList(List<String> knownVal, List<String> el, String fieldName)
  {
    log.info("Checking for drop down values for "+fieldName);
    if(knownVal.size()==el.size())
    {
      for (int i = 0; i < el.size(); i++) {
        if(knownVal.get(i).equalsIgnoreCase(el.get(i))){
          log.info("Value matched for "+knownVal.get(i));
          System.out.println("Value matched for "+knownVal.get(i));
      }else{
        log.error("Value does not matched for "+knownVal.get(i));
        System.out.println("Values does not match for "+knownVal.get(i));
        }
      }
    }
      else{
        log.error("Values count is mismatched");
        System.out.println("Values count is mismatched");
      }
  }
  
  public void compareRandomValuesFromList(List<String> mandField, List<String> valArrayText){
    for (int i = 0; i < valArrayText.size(); i++) {
      boolean tempBool = false;
      for (int j = 0; j < mandField.size(); j++) {
        if(valArrayText.get(i).equalsIgnoreCase(mandField.get(j)))
        {
          log.info("Mandatory field validation is working for "+valArrayText.get(i));
          tempBool=true;
          break;
        }
      }
      Global.as.assertTrue(tempBool, "Mandatory validation is not working for "+valArrayText.get(i));
      if(!tempBool)
      {
        log.error("Mandatory validation is not working for "+valArrayText.get(i));
      }
    }
  }
  public String closeAlertAndGetItsText() {
    try {
      Alert alert = webDriver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }

  public void quitBrowser() {
    webDriver.quit();
  }

}

