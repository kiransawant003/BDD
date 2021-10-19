Feature: Change Status of VIP User

  @changeStatus
  Scenario Outline: Create new VIP User for merchant Blue Trust
    Given user load and fetch file "<FileName>"
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    Then if not paid off then pay it off
 

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |

  @changeStatus
  Scenario Outline: Create new VIP User for merchant Max Lend
     Given user load and fetch file "<FileName>"
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    Then verify login to TRANS LMS is successful
    And _In Review App Search for VIP User by SSN#
    Then if not paid off then pay it off

    # And Continue to apply loan and pay off untiill user becomes VIP for BTL "<PaymentMode>"
    Examples: 
      | FileName            | PaymentMode              |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |
