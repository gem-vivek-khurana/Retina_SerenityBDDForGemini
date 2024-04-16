package com.SerenityBDDForGemini.pages.retina;

import com.SerenityBDDForGemini.support.PageObjectOperations;
import org.openqa.selenium.By;

public class RetinaHome extends PageObjectOperations {
    public static final By PAGE_LOADED = By.tagName("app-organization-management");
    public static final By SEARCH = By.cssSelector("input[formcontrolname='SearchText']");
    public static final By NEW = By.xpath("//span[contains(text(), 'NEW')]/parent::button");
    public static final By CSV = By.xpath("//span[contains(text(), 'CSV')]/parent::button");
}
