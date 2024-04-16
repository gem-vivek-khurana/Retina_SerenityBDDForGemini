package com.SerenityBDDForGemini.steps;

import com.SerenityBDDForGemini.support.DataObjectOperations;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggingEventBuilder;

public class SessionHandlingStepDefinitions {
    public final LoggingEventBuilder LOGGER_INFO = LoggerFactory.getLogger(SessionHandlingStepDefinitions.class).atInfo();

    @Steps
    DataObjectOperations dataObjectOperations;

    @When("I set the session variable {string} as {string}")
    public void iSetTheSessionVariableAs(String sessionVariable, String value) {
        value = value.contains("$") ? dataObjectOperations.transformDataValue(value) : value;
        Serenity.setSessionVariable(sessionVariable).to(value);
        LOGGER_INFO.log("Set Session Variable: " + sessionVariable + "to value: " + value);
    }

    @When("I add the session variable values {string} and {string} saved in {string}")
    public void iAddTheSessionVariableValuesAndSavedIn(String sessionVariable1, String sessionVariable2, String result) {
        int value = Integer.parseInt(Serenity
                .sessionVariableCalled(sessionVariable1)) + Integer.parseInt(Serenity
                .sessionVariableCalled(sessionVariable2));
        Serenity.setSessionVariable(result).to(value);
        LOGGER_INFO.log("Set Session Variable: " + result + "to value: " + value);

    }
}
