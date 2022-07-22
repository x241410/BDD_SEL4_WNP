@LSMS
Feature: LSMS - Verify the SPID

  @v1-0
  Scenario: Verify SPID for the subscriber from LSMS
    #Operation 1
    When def spidAPIfeatureCall = call read(PATH_API_OPS+'getSPIDFromLSMS.feature')
    #Request
    * xml spidAPIfeatureCallRequest = spidAPIfeatureCall.reqpayload
    #Response
    * xml spidAPIfeatureCallResponse = spidAPIfeatureCall.response
    #Status
    * def spidAPIfeatureCallStatus = spidAPIfeatureCall.responseStatus
    #Validation
    Then match spidAPIfeatureCallStatus == 200
