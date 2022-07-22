@Smoke1
@Smoke
Feature: Smoke Test Case BSBDTR1401

 Background:
    Given initialize smoke test data
    And environment details and data setup

@Smoke3
  Scenario: Verify user is able to create a new case BSBDTR1401
    Given login into Smart Desktop
    Then verify SD homepage is displayed
    When search phone number in smart desktop search page
    Then verify SD subscriber
    And close browser