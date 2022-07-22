@LSMS
Feature: WLN - API for creating portin pending request

  @v1-0
  Scenario: API for creating portin pending request
    #Operation 1
    When def createPortinPendingReq = call read(PATH_API_OPS+'portinPending.feature')
    #Request
    * xml createPortinPendingRequest = createPortinPendingReq.reqpayload
    #Response
    * xml createPortinPendingResponse = createPortinPendingReq.response
    #Status
    * def createPortinPendingStatus = createPortinPendingReq.responseStatus
    #Validation
    Then match createPortinPendingStatus == 200
