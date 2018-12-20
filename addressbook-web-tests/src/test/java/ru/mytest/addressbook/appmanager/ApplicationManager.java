package ru.mytest.addressbook.appmanager;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class ApplicationManager {
  private final Properties properties;
  public WebDriver driver;
  public ContactHelper contactHelper;
  public NavigationHelper navigationHelper;
  public GroupHelper groupHelper;
  public SessionHelper sessionHelper;
  public boolean acceptNextAlert = true;
  public String baseUrl;
  public StringBuffer verificationErrors = new StringBuffer();
  private String browser;
  private DbHelper dbHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

    dbHelper = new DbHelper();

    if ("".equals(properties.getProperty("selenium.server"))) { //for remote start of browser on selenium server, stand-alone
      if (browser.equals(BrowserType.FIREFOX)) {
        driver = new FirefoxDriver();
      } else if (browser.equals(BrowserType.CHROME)) {
        driver = new ChromeDriver();
      } else if (browser.equals(BrowserType.IE)) {
        driver = new InternetExplorerDriver();
      }
    } else {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setBrowserName(browser);
      capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "win10")));
      driver = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
    }
    baseUrl = "https://www.katalon.com/";
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    driver.get(properties.getProperty("web.baseUrl"));
    groupHelper = new GroupHelper(this);
    navigationHelper = new NavigationHelper(this);
    contactHelper = new ContactHelper(this);
    sessionHelper = new SessionHelper(this);
    sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
  }

  public void stop() {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  public ContactHelper contact() {
    return contactHelper;
  }

  public DbHelper db() {
    return dbHelper;
  }

}
