package com.epam.controls;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Element {

    private By locator;
    private WebElement element;
    private WebDriver driver;

    public Element(WebDriver driver, String text)
    {
        this.driver = driver;
        this.locator = By.xpath("//label[contains(., '" + text + "')]/input");
    }

    public WebElement getElement()
    {
        if(element == null)
            return element = driver.findElement(locator);
        return element;
    }

    public boolean isChecked()
    {
        return getElement().isSelected();
    }
}