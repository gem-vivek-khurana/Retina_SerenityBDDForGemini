Feature: Login to Retina as Super Admin and Verify UI

  Scenario: Verify Login for Retina as Super Admin and Check the Field presence
    Given I launch the browser and open the "Retina" login page
    Then I should be on the "Retina Login" page under the "Retina" workflow
    When I set the "Email" text field as "riversray243@gmail.com"
    And I set the "Password" text field as "Password@123"
    And I click the "Login" button
    Then I should be on the "Retina Home" page under the "Retina" workflow
    And I should see the following fields are "visible":
      | field  |
      | CSV    |
      | NEW    |
      | Search |