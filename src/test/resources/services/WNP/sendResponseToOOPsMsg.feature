@Ignore
Feature: Send OOPs response

  Background: Configuration - Set up the authentication, Headers, and params
    #Configure the xml payload
    * def payloadFileName = 'OOPS_SMS_PAYLOAD'
    * def authFileName = 'Auth_Token'
    * def TN = karate.properties['karate.TN']
    * def authorizationToken = read(PATH_CONFIG+authFileName+'.json');
    * header Authorization = authorizationToken.OOPs_Authorization
    * param phoneNumber = TN
    * param destinationAddress = '18774847586'
    * param originatingApplicationId = 'Adobe_Genesys'
    

  Scenario: Retrieve SPID from the LSMS
    #Set endpoint url
    Given url ENDPOINT_OOPS
    #Request XML passed for the operation and printing the same for verification
    * def reqpayload = read(PATH_API_PAYLOAD + payloadFileName +'.json')
    And header Content-Type = 'application/json'
    And request reqpayload
    #		When REST operation post
    When method post
