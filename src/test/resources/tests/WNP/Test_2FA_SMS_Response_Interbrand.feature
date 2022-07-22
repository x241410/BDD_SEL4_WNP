@LSMS
Feature: 2FA SMS Response for Interbrand

  @v1-0
  Scenario: Verify Customer Response to 2FA SMS Interbrand
    #Operation 1
    When def sendResponseSMSAPIfeatureCall = call read(PATH_API_OPS+'sendResponseToSMS_Interbrand.feature')
    #Request
    * xml sendResponseSMSAPIfeatureCallRequest = sendResponseSMSAPIfeatureCall.reqpayload
    #Response
    * json sendResponseSMSAPIfeatureCallResponse = sendResponseSMSAPIfeatureCall.response
    * def status = sendResponseSMSAPIfeatureCallResponse.status
    * def statusMessage = sendResponseSMSAPIfeatureCallResponse.statusMessage
    * def orderId = sendResponseSMSAPIfeatureCallResponse._routingkey
    #Status
    * def sendResponseSMSAPIfeatureCallStatus = sendResponseSMSAPIfeatureCall.responseStatus
    #Validation
    Then match sendResponseSMSAPIfeatureCallStatus == 200
