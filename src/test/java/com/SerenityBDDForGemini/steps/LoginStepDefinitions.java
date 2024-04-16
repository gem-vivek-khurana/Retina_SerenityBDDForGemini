package com.SerenityBDDForGemini.steps;

import com.SerenityBDDForGemini.navigation.NavigateTo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;

import java.lang.reflect.InvocationTargetException;

public class LoginStepDefinitions {
    public final LoggingEventBuilder LOGGER_INFO = LoggerFactory.getLogger(LoginStepDefinitions.class).atInfo();

    /**
     * Step definition for launching the browser and opening a login page.
     *
     * @param page The name of the login page. The name of the page object class for this step-definition should be
     *             "<Page>Login.java" and a method should be available in the {@link NavigateTo} class with the name
     *             "the<Page>LoginPage()" which should be navigating to the {@link net.thucydides.core.annotations.DefaultUrl}
     *             of Serenity library by using pageObject.open() method. Refer to {@linkplain NavigateTo#theRetinaLoginPage()}
     *             method and {@link com.SerenityBDDForGemini.pages.retina.RetinaLogin} page for reference.
     * @throws NoSuchMethodException    If a matching method is not found.
     * @throws InvocationTargetException If the invoked method throws an exception.
     * @throws IllegalAccessException    If there's an issue accessing the method.
     * <p>
     * Example usage:
     * <pre>{@code
     * Given I launch the browser and open the "Retina" login page
     * }</pre>
     * </p>
     */
    @Given("I launch the browser and open the {string} login page")
    public void launchBrowserAndOpenTheLogin(String page) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        NavigateTo.class.getMethod("the" + page + "LoginPage").invoke(new NavigateTo());
    }

    /**
     * Step definition for stopping the debugger.
     * For halting the flow of feature run put up this statement ion the feature steps and put the IDE debugger here.
     * This debugging methodology will only work with IDEs like Intellij IDEA or Eclipse.
     *<p>
     * Example usage:
     * <pre>{@code
     * When I stop the debugger here
     * }</pre>
     * </p>
     */
    @When("I stop the debugger here")
    public void iStopTheDebuggerHere() {
        System.out.println("Pause Run");
    }
}
