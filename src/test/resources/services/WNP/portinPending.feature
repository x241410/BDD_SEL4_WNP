@Ignore
Feature: Create Portin Pending Request

  Background: Configuration - Set up the authentication, Headers, and params
    #Configure the xml payload
    * def xmlName = 'WLN_Portin_Pending_Payload'
    * def contentType = 'application/xml'
    * def tn = karate.properties['karate.tn']
    * def authFileName = 'Auth_Token'
    * def authorizationToken = read(PATH_CONFIG+authFileName+'.json');
    * header Authorization = authorizationToken.PortinPending_Authorization


  Scenario: Respond to 2FA SMS
    #Set endpoint url
    Given url ENDPOINT_PORTIN_PENDING
    #Request XML passed for the operation and printing the same for verification
    * def reqpayload = read(PATH_API_PAYLOAD + xmlName +'.xml')
    And header Content-Type = contentType
    And request reqpayload
    #When api action performed
    When method post
