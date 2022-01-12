Feature: Sales Order Journey

  @sanity
  Scenario Outline: Validate that the user is able to create a sales order

    Given the user authenticates using ".json"
    And the user creates a json request body for "CreateSalesOrder" API with "Post" http method using below details <Iterations>
      | ReqHdr_ | ReqQpm_ | ReqPpm_ | ReqBdy_Lat | ReqBdy_Lng | ReqBdy_Accuracy | ReqBdy_Name      | ReqBdy_PhoneNumber | ReqBdy_Address                 | ReqBdy_Types   | ReqBdy_Website    | ReqBdy_Language |
      | N/A     | N/A     | N/A     | 38         | 40         | 50              | Veerankis Villah | 9911588999         | 1 The Queen, Windsor Castle,UK | Castle#,#House | http://google.com | English-UK      |
      | N/A     | N/A     | N/A     | 1          | 3          | 3               | Naveens Villah   | 1234567890         | 2 The Queen, Windsor Castle,UK | Castle#,#House | http://google.com | English-Brit    |

    When the user calls "CreateSalesOrder" API with "Post" http request

    Then the user should get the status code as 200
    And the response json of "CreateSalesOrder" API should match below details <Iterations>
      | ResHdr_Address | ResHdr_Id              | ResBdy_ExpScope | ResBdy_ExpStatus |
      | N/A            | Apache/2.4.18 (Ubuntu) | APP             | OK               |
      | N/A            | Apache/2.4.18 (Ubuntu) | APP             | OK               |

    Given the user creates the http "Get" request
    When the user calls "GetSalesOrder" API with "Get" http request
    Then the user should get the status code as 200
    And the response json of "GetSalesOrder" API should match below details <Iterations>
      | ReqBdy_Name      | ReqBdy_PhoneNumber | ReqBdy_Address                 |
      | Veerankis Villah | 9911588999         | 1 The Queen, Windsor Castle,UK |
      | Naveens Villah   | 1234567890         | 2 The Queen, Windsor Castle,UK |

    Examples:
      | Iterations |
      | 0          |
      | 1          |



















#
##Option 1
#
#  Scenario: Validate that the user is able to create a sales order
#    Given the user authenticates using ".json"
#    And the user creates a post request with below details
#    And with below header values
#      |       |       |
#      |       |       |
#    And with below query parameters values
#      |       |       |
#      |       |       |
#    And with below request body values
#      |       |       |
#      |       |       |
#
#    When the user hits below end point
#      |                                      |
#      |                                      |
#
#    Then the user should get the status code as "200"
#    And below header values as below
#      |       |       |
#      |       |       |
#    And below body values in the response
#      |       |       |
#      |       |       |
#
##Option 2
#  Scenario: Validate that the user is able to create a sales order
#    Given the user authenticates using ".json"
#    And the user creates a post request with below details
#      |   ReqHdr    |  ReqQpm     | ReqPpm |  ReqBdy |
#      |             |             |        |         |
#
#    When the user hits below end point
#      | Resource                 |
#      | /maps/api/place/add/json |
#
#    Then the user should get the status code as "200"
#    And below values in the response
#      |  ResHdrAddress     |  ResHdrId     | ResBdy   |
#      |                    |               |          |
#
##Option 3
#  Scenario: Validate that the user is able to create a sales order
#    Given the user authenticates using ".json"
#    And the user creates a post request using ".json"
#    When the user hits below end point "/maps/api/place/add/json"
#    Then the user should get the status code as "200"
#    And response should match ".json"
#
#
#
    #Option 4
#  @sanity
#  Scenario: Validate that the user is able to create a sales order
#    Given the user authenticates using ".json"
#    And the user creates a http post request using below details and ".json"
#      | ReqHdr_ | ReqQpm_ | ReqPpm_ | ReqBdy_Lat |  ReqBdy_Name       |
#      | N/A     | N/A     | N/A     | 38         |  Veeranki's Villah |
#    When the user calls "abc" API by hitting below end point
#      | EndPoint                 |
#      | /maps/api/2/abc/xyz |
#    Then the user should get the status code as "200"
#    And response "Json" should match below details
#      | ResHdr_Address | ResHdr_Id | ResBdy_ExpScope |
#      | N/A            | N/A       | APP             |


