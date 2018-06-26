package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class Tc47SMSGSmoke {
	
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
	  //setting the chrome driver location
	System.setProperty("webdriver.chrome.driver","E:\\test\\chromedriver.exe");	
    driver = new ChromeDriver();
    baseUrl = "http://localhost:8080/47SMSG/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTc47SMSGSmoke() throws Exception {
    driver.get("http://localhost:8080/47SMSG/");
    driver.findElement(By.id("user_name")).click();
    driver.findElement(By.id("user_name")).clear();
    //type logging user name
    driver.findElement(By.id("user_name")).sendKeys("NewUser");
    driver.findElement(By.id("btnlog")).click();
    try {
    	//verify the logged user
      assertTrue(isElementPresent(By.xpath("//sapn[text()='NewUser']")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.xpath("//span[text()='1000']")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertTrue(isElementPresent(By.xpath("//span[text()='1']")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
   
    driver.findElement(By.xpath("//input[@id='amount']")).click();
    Thread.sleep(10000);
    
    driver.findElement(By.xpath("//input[@id='amount']")).sendKeys("1");
    
    driver.findElement(By.id("buy_button")).click();
    try {
      assertTrue(isElementPresent(By.xpath("//span[text()='926']")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    driver.findElement(By.id("sell_button")).click();
    try {
      assertTrue(isElementPresent(By.xpath("//span[text()='1000']")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
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
}
