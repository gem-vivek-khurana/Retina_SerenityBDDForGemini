package com.SerenityBDDForGemini.steps;

import com.SerenityBDDForGemini.execute.Perform;
import com.SerenityBDDForGemini.support.DataObjectOperations;
import com.SerenityBDDForGemini.support.PageObjectOperations;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FieldInteractionsStepDefinitions {
    @Steps
    Perform perform;

    @Steps
    PageObjectOperations pageObjectOperations;

    @Steps
    DataObjectOperations dataObjectOperations;

    @When("I set the {string} text field/area as {string}")
    public void iSetTextFieldAs(String fieldName, String value) {
        String poe = pageObjectOperations.poeName(fieldName);
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> pageClass = pageObjectOperations.getPageClass(currentPage);
        Field textElement = pageObjectOperations.poeFieldClass(poe, currentPage);
        perform.settingFieldValue(textElement, pageClass, value);
    }

    @When("I set the {string} date field as {string}")
    public void iSetDateFieldAs(String fieldName, String value) {
        String poe = pageObjectOperations.poeName(fieldName);
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> pageClass = pageObjectOperations.getPageClass(currentPage);
        Field textElement = pageObjectOperations.poeFieldClass(poe, currentPage);
        // Check if the date input from feature is not in traditional format
        if (value.equals("t") || value.contains("t-") || value.contains("t+") || value.contains("t&dtf")) {
            // Check if the date requires special DateTimeFormatter
            if (value.contains("&dtf=")) value = dataObjectOperations.extractDtfAndTransformDateValue(value);
            else value = dataObjectOperations.transformUIDateValue(value);
        }
        perform.settingDateFieldValue(textElement, pageClass, value);
    }

    @When("I click the {string} button/link/icon/radio/field/tab")
    public void iClickTheElement(String clickableElementName) {
        String poe = pageObjectOperations.poeName(clickableElementName);
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> pageClass = pageObjectOperations.getPageClass(currentPage);
        Field clickableElement = pageObjectOperations.poeFieldClass(poe, currentPage);
        perform.clickOn(clickableElement, pageClass);
    }

    @When("I set the {string} dropdown as {string}")
    public void iSetDropdownAs(String fieldName, String value) {
        String poe = pageObjectOperations.poeName(fieldName);
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> pageClass = pageObjectOperations.getPageClass(currentPage);
        Field dropdownElement = pageObjectOperations.poeFieldClass(poe, currentPage);
        value = value.contains("$") ? dataObjectOperations.transformDataValue(value) : value;
        perform.settingDropdownValue(dropdownElement, pageClass, value);
    }

    @When("I set the {string} checkbox as {string}")
    public void iSetCheckboxAs(String fieldName, String value) {
        String poe = pageObjectOperations.poeName(fieldName);
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> pageClass = pageObjectOperations.getPageClass(currentPage);
        Field checkboxElement = pageObjectOperations.poeFieldClass(poe, currentPage);
        perform.settingCheckboxAs(checkboxElement, pageClass, value);
    }

    @When("I set these fields with following values:")
    public void iSetTheseFieldsWithFollowingValues(List<Map<String, String>> dataTable) {
        if (!dataTable.get(0).keySet().containsAll(Arrays.asList("field", "fieldType", "value")))
            throw new RuntimeException("The data table with this step is incorrect. Please make sure that the table" +
                    " the right headers: [field, fieldType, value]");
        if (dataTable.get(0).keySet().size() != 3)
            throw new RuntimeException("The data table with this step is incorrect. Please make sure that the table" +
                    " the right headers: [field, fieldType, value]");
        for (Map<String, String> tableRow : dataTable) {
            String valueToFill = "";
            if (tableRow.get("value").contains("`$`")) {
                valueToFill = tableRow.get("value");
            } else {
                valueToFill = tableRow.get("value").contains("$") ? dataObjectOperations
                        .transformDataValue(tableRow.get("value")) : tableRow.get("value");
            }
            switch (tableRow.get("fieldType").toLowerCase()) {
                case "text field", "textarea" -> iSetTextFieldAs(tableRow.get("field"), valueToFill);
                case "date field" -> iSetDateFieldAs(tableRow.get("field"), valueToFill);
                case "dropdown" -> iSetDropdownAs(tableRow.get("field"), valueToFill);
                case "checkbox" -> iSetCheckboxAs(tableRow.get("field"), valueToFill);
                default -> throw new IllegalArgumentException("Unknown field type: " + tableRow.get("fieldType"));
            }
        }
    }
}
