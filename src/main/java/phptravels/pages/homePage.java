package phptravels.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.util.SeleniumUtilities;

public class homePage extends SeleniumUtilities{
	private static WebElement element = null;
	public static  By link_MyAccoun1=By.xpath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a/i");
	public static  By link_Login1=By.cssSelector(".open > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)");
	
//	public WebElement linkMyAccount2=findElement(By.xpath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a/i"));

	public static WebElement link_MyAccount(WebDriver driver) {
		element = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]/ul/li[1]/a/i"));
		return element;
	}
	
	public static WebElement link_Login(WebDriver driver) {
		element = driver.findElement(By.cssSelector(".open > ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)"));
		return element;
	}
	
	public static WebElement input_UserName(WebDriver driver) {
		element = driver.findElement(By.name("username"));
		return element;
	}
	
	public static WebElement input_Password(WebDriver driver) {
		element = driver.findElement(By.name("password"));
		return element;
	}
	
	public static WebElement btn_Login(WebDriver driver) {
		element = driver.findElement(By.xpath("//button[text()='Login']"));
		return element;
	}
	
	public static WebElement link_Location(WebDriver driver){
		element = driver.findElement(By.xpath("//span[contains(text(),'Search by Hotel or City Name')]/.."));
		return element;
	}
	
	public static WebElement input_Location(WebDriver driver){
		element = driver.findElement(By.xpath("//*[@id='select2-drop']/div/input"));
		return element;
	}
	
	public static WebElement rslt_Location(WebDriver driver){
		element = driver.findElement(By.xpath("//span[contains(text(),'Pune')]"));
		return element;
	}
	
	public static WebElement link_Check_In(WebDriver driver){
		element = driver.findElement(By.xpath("//input[@name='checkin']"));
		return element;
	}
	
	public static WebElement link_Flights(WebDriver driver){
		element = driver.findElement(By.xpath("//*[@id=\"offcanvas-menu\"]/ul/li[2]/a"));
		return element;
	}


}
