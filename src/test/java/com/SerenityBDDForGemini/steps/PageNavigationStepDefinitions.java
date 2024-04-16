package com.SerenityBDDForGemini.steps;

import com.SerenityBDDForGemini.execute.Perform;
import com.SerenityBDDForGemini.navigation.NavigateTo;
import com.SerenityBDDForGemini.support.PageObjectOperations;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.lang.reflect.InvocationTargetException;

public class PageNavigationStepDefinitions {
    @Steps
    Perform perform;

    @Steps
    PageObjectOperations pageObjectOperations;

    @When("I navigate to {string} page")
    public void iNavigateToPage(String page) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        NavigateTo.class.getDeclaredMethod("the" + pageObjectOperations.pageObjectName(page) + "Page")
                .invoke(new NavigateTo());
    }
}
