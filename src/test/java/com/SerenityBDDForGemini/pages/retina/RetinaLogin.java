package com.SerenityBDDForGemini.pages.retina;

import com.SerenityBDDForGemini.support.PageObjectOperations;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("https://dev-retina.geminisolutions.in/#/login")
public class RetinaLogin extends PageObjectOperations {
 public static final By PAGE_LOADED = By.tagName("app-login-page");
 public static final By EMAIL = By.cssSelector("input[formcontrolname ='email']");
 public static final By PASSWORD = By.cssSelector("input[formcontrolname ='password']");
 public static final By LOGIN = By.className("logbutton");
}
