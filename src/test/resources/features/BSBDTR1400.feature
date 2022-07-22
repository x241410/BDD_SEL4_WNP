@Smoke2
@Smoke
Feature: Smoke Test Case

 Background:
    Given initialize smoke test data
    And environment details and data setup

@Smoke4
  Scenario: Verify user is able to create a new case
    Given login into Smart Desktop
    Then verify SD homepage is displayed
    When search phone number in smart desktop search page
    Then verify SD subscriber
    And close browser
    
@Smoke5 
  Scenario: Verify user is able to search phone number
    Given login into Smart Desktop
    Then verify SD homepage is displayed
    When search phone number in smart desktop search page
    Then verify SD subscriber
    And close browser  
