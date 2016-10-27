package com.epam.uitest.surrounding;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private boolean takeScreeshot = true;
    private int screenShotCount = 0;
    protected final String URL_START_PAGE= "https://jdi-framework.github.io/tests/index.htm";
    protected WebDriver driver;
    protected String browser;
    //protected String browser = "firefox";

    public void verify(Object a, Object b){
        try {
            Assert.assertEquals(a, b);
        }
        catch (AssertionError e){
            if(takeScreeshot)
            {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                try {
                    FileUtils.copyFile(scrFile, new File("C:\\tmp\\screenshot"+screenShotCount+".png"));
                    screenShotCount++;
                } catch (IOException e1) {
                    System.out.println(e1);
                }
            }
            throw new AssertionError(e);
        }
    }

    public void openSite(String URL){
        driver.navigate().to(URL);
    }

    @BeforeSuite
    public void baseBrowserSetup(){
        File file;
        file = new File("C:/Windows/System32/geckodriver.exe");
        System.setProperty("webdriver.gecko.driver", file.getAbsolutePath());
        file = new File("C:/Windows/System32/operadriver.exe");
        System.setProperty("webdriver.opera.driver", file.getAbsolutePath());
        file = new File("C:/Windows/System32/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
        file = new File("C:/Windows/System32/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
    }

    @BeforeMethod
    public void setup(){
        browser = System.getProperty("browser");
        switch (browser)
        {
            case "firefox" :
                driver = new FirefoxDriver();
                break;
            case "opera" :
                driver = new OperaDriver();
                break;
            case "chrome" :
                driver = new ChromeDriver();
                break;
            case "ie" :
                driver = new InternetExplorerDriver();
                break;
        }
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        try {
            driver.quit();
        }
        catch (UnreachableBrowserException e) {
        }
    }
}
