@Ignore
Feature: Get SPID from LSMS

  Background: Configuration - Set up the authentication, Headers, and params
    #Configure the xml payload
    * def xmlName = 'LSMS_SPID_Payload'
    * def TN = karate.properties['karate.TN']

  Scenario: Retrieve SPID from the LSMS
    #Set endpoint url
    Given url ENDPOINT_LSMS
    #Request XML passed for the operation and printing the same for verification
    * def reqpayload = read(PATH_API_PAYLOAD + xmlName +'.xml')
    And header Content-Type = 'application/soap+xml'
    And request reqpayload
    #		When soap action operation
    When method post
