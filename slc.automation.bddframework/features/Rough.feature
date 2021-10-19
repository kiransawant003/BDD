Feature: Create Application 

Background: Copy the feature file 
Given feature file is Churn

  @createApplicationUsingACH
  Scenario Outline: Create and Originate Application Using BTL for Normal User
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And _In Review App Search by Email_Address
 #   And update paydate for Normal user
 #   And Change due date
   # And Originate Application
   And get the application number for Blue Trust

    Examples: 
      | FileName            | PaymentMode              |
      | BTL_QA_ClientData_1 | Electronic Fund Transfer |

  @createApplicationUsingACH
  Scenario Outline: Create and Originate Application Using MaxLend for Normal User
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with EFT
    Then user is on LMS homepage
    When user enters login details in LMS for MLL
    And _In Review App Search by Email_Address
#    And update paydate for Normal user
 #   And Change due date
 #   And Originate Application
 And get the application number for Max Lend

    Examples: 
      | FileName            | PaymentMode              |
      | MLL_QA_ClientData_1 | Electronic Fund Transfer |
      
      
      @createOriginateWithoutEFTN
  Scenario Outline: BTL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on BTL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Blue Trust
    And In Review App Search by Email_Address
    And get the application number for Blue trust

    Examples: 
      | FileName            | PaymentMode |
      | BTL_QA_ClientData_1 | Txt-A-Check |

  @createOriginateWithoutEFTN
  Scenario Outline: MLL_Create and Originate application
    Given user load configuration file "<FileName>"
    When user fetch the information
    Then user is on MLL homepage
    And Specify information on AboutYou Page
    And Specify information on Income Page for Employed with DirectDeposit
    And Specify information on Banking Page using "<PaymentMode>"
    Then Apply Contact Consent
    And Apply ESign with TextCheck
    Then user is on LMS homepage
    When user enters login details in LMS for Max Lend
    And In Review App Search by Email_Address
    And get the application number for Max Lend

    Examples: 
      | FileName            | PaymentMode |
      | MLL_QA_ClientData_1 | Txt-A-Check |