@Ignore
Feature: Send response to 2FA SMS

  Background: Configuration - Set up the authentication, Headers, and params
    #Configure the xml payload
    * def xmlName = '2FA_SMS_Payload'
    * def pacInternalReqNum = karate.properties['karate.pacInternalReqNum']
    * def TN = karate.properties['karate.TN']
    * def responseYN = karate.properties['karate.responseYN']
    * def dateTime = karate.properties['karate.dateTime']
    * def languageCode = karate.properties['karate.languageCode']
    * def BAN = karate.properties['karate.BAN']
    * def param_orderId = pacInternalReqNum
    * def param_eventType = 'SMS_2WAY_RES'
    * def contentType = 'application/xml'
    * def authFileName = 'Auth_Token'
    * def authorizationToken = read(PATH_CONFIG+authFileName+'.json');
    * header Authorization = authorizationToken.Authorization
    * param orderId = param_orderId
    * param eventTypeId = param_eventType

  Scenario: Respond to 2FA SMS
    #Set endpoint url
    Given url ENDPOINT_2FA
    #Request XML passed for the operation and printing the same for verification
    * def reqpayload = read(PATH_API_PAYLOAD + xmlName +'.xml')
    And header Content-Type = contentType
    And request reqpayload
    #When api action performed
    When method post
