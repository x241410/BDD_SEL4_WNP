@LSMS
Feature: OOPS - Respond to OOPs Message

  @v1-0
  Scenario: Verify respone to OOPs message
    #Operation 1
    When def oopsAPIfeatureCall = call read(PATH_API_OPS+'sendResponseToOOPsMsg.feature')
    #Request
    * json oopsAPIfeatureCallRequest = oopsAPIfeatureCall.reqpayload
    #Response
    * json oopsAPIfeatureCallResponse = oopsAPIfeatureCall.response
    #Status
    * def oopsAPIfeatureCallStatus = oopsAPIfeatureCall.responseStatus
    #Validation
    Then match oopsAPIfeatureCallStatus == 200
