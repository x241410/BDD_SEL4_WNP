@LSMS
Feature: 2FA SMS Response

  @v1-0
  Scenario: Verify Customer Response to 2FA SMS
    #Operation 1
    When def sendResponseSMSAPIfeatureCall = call read(PATH_API_OPS+'sendResponseToSMS.feature')
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
