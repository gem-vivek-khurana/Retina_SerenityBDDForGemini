package com.SerenityBDDForGemini.steps;

import com.SerenityBDDForGemini.execute.Perform;
import com.SerenityBDDForGemini.support.DataObjectOperations;
import com.SerenityBDDForGemini.support.GridObjectOperations;
import com.SerenityBDDForGemini.support.PageObjectOperations;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GridObjectStepDefinitions {
    @Steps
    PageObjectOperations pageObjectOperations;

    @Steps
    GridObjectOperations gridObjectOperations;

    @Steps
    Perform perform;

    @Steps
    DataObjectOperations dataObjectOperations;

    @Then("I should see the following values in the grid:")
    public void iShouldSeeTheFollowingValuesInTheGrid(List<Map<String, String>> dataTable) {
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> pageClass = pageObjectOperations.getPageClass(currentPage);
        Field tableElement = pageObjectOperations.poeFieldClass("GRID_LOADED", currentPage);
        By table = perform.fieldToInteract(tableElement, pageClass);
        dataTable = dataObjectOperations.transformDataTable(dataTable);
        for (Map<String, String> tableRow : dataTable) {
            Assert.assertEquals(tableRow, gridObjectOperations.getValueForRowColumn(tableRow, table));
        }
    }

    @When("I click the {string} link/button/icon/field on row {int} of the grid")
    public void iClickTheButtonOnRowOfTheGrid(String fieldName, int rowNumber) {
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> gridClass = pageObjectOperations.getPageClass(currentPage);
        Field clickableElement = pageObjectOperations.poeFieldClass(fieldName, currentPage);
        WebElement rowToFocus = gridObjectOperations.getRowFocusedWebElement(rowNumber, currentPage);
        perform.clickOn(rowToFocus.findElement(perform.fieldToInteract(clickableElement, gridClass)));
    }

    @When("I set the {string} checkbox as {string} on row {int} of the grid")
    public void iSetTheCheckboxAsOnRowOfTheGrid(String fieldName, String value, int rowNumber) {
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> gridClass = pageObjectOperations.getPageClass(currentPage);
        Field checkboxElement = pageObjectOperations.poeFieldClass(fieldName, currentPage);

        WebElement rowToFocus = gridObjectOperations.getRowFocusedWebElement(rowNumber, currentPage);
        perform.settingCheckboxAs(rowToFocus.findElement(perform.fieldToInteract(checkboxElement, gridClass)), value);
    }

    @When("I set the {string} checkbox as {string} on row with {string} column as {string} in the grid")
    public void iSetTheCheckboxAsOnRowWithColumnAsInTheGrid(String fieldName, String value, String expectedField, String expectedValue) throws NoSuchFieldException {
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> gridClass = pageObjectOperations.getPageClass(currentPage);
        By table = perform.fieldToInteract(gridClass.getDeclaredField("GRID_LOADED"), gridClass);
        Field checkboxElement = pageObjectOperations.poeFieldClass(fieldName, currentPage);
        expectedValue = expectedField.toLowerCase().contains("date") ? dataObjectOperations.transformDateValue(
                expectedValue, DataObjectOperations.DateTimeFormatters.getDtf("ui_dtf")) : expectedValue;
        expectedValue = expectedValue.contains("$") ? dataObjectOperations
                .transformDataValue(expectedValue) : expectedValue;
        List<String> rowsData = gridObjectOperations.getRowsDataForColumns(Collections.singletonList(expectedField),
                table).get(expectedField);
        int rowIndex = rowsData.indexOf(expectedValue) + 1;
        if (rowIndex == 0) throw new RuntimeException("Unable to find row in the grid table.");
        WebElement rowToFocus = gridObjectOperations.getRowFocusedWebElement(rowIndex, currentPage);
        perform.settingCheckboxAs(rowToFocus.findElement(perform.fieldToInteract(checkboxElement, gridClass)),value);
    }

    @When("I click the {string} link/button/icon/field on row with {string} column as {string} in the grid")
    public void iClickTheLinkOnRowWithColumnAsInTheGrid(String fieldName, String expectedField, String expectedValue) throws NoSuchFieldException {
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> gridClass = pageObjectOperations.getPageClass(currentPage);
        By table = perform.fieldToInteract(gridClass.getDeclaredField("GRID_LOADED"), gridClass);
        Field clickableElement = pageObjectOperations.poeFieldClass(fieldName, currentPage);
        List<String> rowsData = gridObjectOperations.getRowsDataForColumns(Collections.singletonList(expectedField),
                table).get(expectedField);
        int rowIndex = rowsData.indexOf(expectedValue) + 1;
        if (rowIndex == 0) throw new RuntimeException("Unable to find row in the grid table.");
        WebElement rowToFocus = gridObjectOperations.getRowFocusedWebElement(rowIndex, currentPage);
        perform.clickOn(rowToFocus.findElement(perform.fieldToInteract(clickableElement, gridClass)));
    }

    @Then("I should see {int} row(s) in the grid")
    public void iShouldSeeRowsInTheGrid(int rowCount) throws NoSuchFieldException {
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> gridClass = pageObjectOperations.getPageClass(currentPage);
        Field gridLoadedElement = pageObjectOperations.poeFieldClass("GRID_LOADED", currentPage);
        By table = perform.fieldToInteract(gridLoadedElement, gridClass);
        int count = gridObjectOperations.getRowCount(table);
        Assert.assertEquals("Incorrect number of rows unexpectedly displayed", rowCount, count);
    }

    @Then("I should see the grid contains the following headers:")
    public void iShouldSeeTheGridContainsTheFollowingHeaders(List<Map<String, String>> dataTable) throws NoSuchFieldException {
        String currentPage = Serenity.sessionVariableCalled("Current Page");
        Class<?> gridClass = pageObjectOperations.getPageClass(currentPage);
        Field gridLoadedElement = pageObjectOperations.poeFieldClass("GRID_LOADED", currentPage);
        By table = perform.fieldToInteract(gridLoadedElement, gridClass);
        List<String> tableHeaders = gridObjectOperations.getTableHeaders(table);
        List<String> expectedHeaders = dataObjectOperations.transformListHashToStringList(dataTable);
        Assert.assertEquals("Unexpected display of table headers in the defined table."
                , expectedHeaders, tableHeaders);
    }
}
